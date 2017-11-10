import java.io.IOException;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;

public class LeafTest extends PApplet {
	
	KinectBodyDataProvider kinectReader;
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
	HashMap<Integer, Leaf> leaves = new HashMap<Integer, Leaf>();
	
	boolean falling = false;

	//handRight variables for getIntensity method
	float HRprevY = 0;
	float HRVelocity = 0;
	boolean HRgoingUp = false;
	float HRcurrY = 0;
	int HRintensity = -1;
	
	//handLeft variables for getIntensity method
	float HLprevY = 0;
	float HLVelocity = 0;
	boolean HLgoingUp = false;
	float HLcurrY = 0;
	int HLintensity = -1;

	public void createWindow(boolean useP2D, boolean isFullscreen, float windowsScale) {
		if (useP2D) {
			if(isFullscreen) {
				fullScreen(P2D);  			
			} else {
				size((int)(1920 * windowsScale), (int)(1080 * windowsScale), P2D);
			}
		} else {
			if(isFullscreen) {
				fullScreen();  			
			} else {
				size((int)(1920 * windowsScale), (int)(1080 * windowsScale));
			}
		}		
	}
	
	// use lower numbers to zoom out (show more of the world)
	// zoom of 1 means that the window is 2 meters wide and appox 1 meter tall.
	public void setScale(float zoom) {
		scale(zoom* width/2.0f, zoom * -width/2.0f);
		translate(1f/zoom , -PROJECTOR_RATIO/zoom );		
	}

	public void settings() {
		createWindow(false, false, .5f);
	}

	public void setup(){
		
		try {
			kinectReader = new KinectBodyDataProvider("test2.kinect", 10);
		} catch (IOException e) {
			System.out.println("Unable to creat e kinect producer");
		}
		//kinectReader = new KinectBodyDataProvider(8008);
		kinectReader.start();
		
		for(int i = 0; i < 10; i++){
			leaves.put(i, new Leaf(this, this.random(-1f, 1f),0) );
		}		
	}
	
	public void draw(){
		setScale(.5f);
		
		noStroke();
		// light grey background
		background(255,255,255);
		
		
		for(Leaf l : leaves.values()){
			l.draw();
		}
		
		if(falling){
			for(Leaf l : leaves.values()){
				l.falling();
			}	
		}
		
		KinectBodyData bodyData = kinectReader.getData();
		Body person = bodyData.getPerson(0);
		if(person != null){
			PVector head = person.getJoint(Body.HEAD);
			PVector spine = person.getJoint(Body.SPINE_SHOULDER);
			PVector spineBase = person.getJoint(Body.SPINE_BASE);
			PVector shoulderLeft = person.getJoint(Body.SHOULDER_LEFT);
			PVector shoulderRight = person.getJoint(Body.SHOULDER_RIGHT);
			PVector footLeft = person.getJoint(Body.FOOT_LEFT);
			PVector footRight = person.getJoint(Body.FOOT_RIGHT);
			PVector handLeft = person.getJoint(Body.HAND_LEFT);
			PVector handRight = person.getJoint(Body.HAND_RIGHT);
			
			fill(255,255,255);
			noStroke();

			fill(255,255,255);
			noStroke();
			drawIfValid(head);
			drawIfValid(spine);
			drawIfValid(spineBase);
			drawIfValid(shoulderLeft);
			drawIfValid(shoulderRight);
			drawIfValid(footLeft);
			drawIfValid(footRight);
			drawIfValid(handLeft);
			drawIfValid(handRight);

			if( 
					(footRight != null) &&
					(footLeft != null) &&
					(handLeft != null) &&
					(handRight != null) 
					) {
				stroke(255,0,0, 100);
				noFill();
				strokeWeight(.05f); // because of scale weight needs to be much thinner
				curve(
						footLeft.x, footLeft.y, 
						handLeft.x, handLeft.y, 
						handRight.x, handRight.y,
						footRight.x, footRight.y
						);
			}
			
			System.out.println(getIntensity(handRight, handLeft));
			
			// start falling
			if( getIntensity(handRight, handLeft) == 4 )
				falling = true;
			else if( getIntensity(handRight, handLeft) == 3 ){
				for(Leaf l : leaves.values()){
					l.turnRed();
				}		
				}
			else if( getIntensity(handRight, handLeft) == 2 ){	
				for(Leaf l : leaves.values()){
					l.turnRed();
				}
			}
			else if( getIntensity(handRight, handLeft) == 1 ){
				for(Leaf l : leaves.values()){
					l.turnRed();
				}
			}
		}
	}
	
	public void drawIfValid(PVector vec) {
		if(vec != null) {
			fill(0);
			ellipse(vec.x, vec.y, .1f,.1f);
		}

	}

	//calculate the y difference between current location and the last location
	public float diffVel(float oldVel, float lastY,  float curY){
		
			float diffY = curY - lastY;
			
			if (diffY != 0){

				return (float) ((oldVel*0.8) + (diffY*0.2));
			} else {
			 return  oldVel;
			}
	}
	
	//set the state goingUp true if left hand is moving up
	public void diffHL(PVector handLeft){
		
		if (handLeft != null){
			HLVelocity = diffVel(HLVelocity, HLprevY, handLeft.y);
			
			if (HLVelocity < 0.004){
				HLgoingUp = false;
			} else{	
				HLgoingUp = true;
			}
			HLprevY =  handLeft.y;
		}

	}	
	
	//set the state goingUp true if right hand is moving up
	public void diffHR(PVector handRight){
		
		if (handRight != null){
			HRVelocity = diffVel(HRVelocity, HRprevY, handRight.y);
			
			if (HRVelocity < 0.004){
				HRgoingUp = false;
			} else{	
				HRgoingUp = true;
			}
			HRprevY =  handRight.y;
		}

	}
	
	// return the average intensity of right and left hand
	public int getIntensity(PVector right, PVector left){
		if(Math.min(getIntensityHR(right), getIntensityHL(left)) == -1)
			return (getIntensityHR(right) + getIntensityHL(left))/2 - 1;
		
		return (getIntensityHR(right) + getIntensityHL(left))/2;
	}

	/* TAKES IN COORDINATES OF HAND RIGHT AND RETURNS INTEGER REPRESENTING INTENSITY OF CHANGES IN Y-AXIS
	 * No change --> Return -1
	 * Minimal change --> Return 1
	 * Slight change --> Return 2
	 * Change --> Return 3
	 * More Change --> Return 4
	 * Big change --> Return 5
	 */
	// add flowers to represent head
	public int getIntensityHR(PVector p1){
		
		int intensity = -1;
		
		diffHR(p1);
		
		//HRgoingUp is the previous value
		if (p1!=null){	
			
			if (HRgoingUp){
			
				if (HRVelocity == 0)	{
					return intensity;
				}
				else if (HRVelocity < 0.01){
					 intensity = 1;
				}
				else if (HRVelocity < 0.02){
					 intensity = 2;
				}
				else if (HRVelocity < 0.03 ){
					 intensity = 3;
				}
				else if (HRVelocity <  0.06 ){
					 intensity = 4;
				}
				else if (HRVelocity > 0.06) {
					 intensity = 5;
				}
				
				}
			
			}
		
		return intensity;
	}
	
	/* TAKES IN COORDINATES OF HAND LEFT AND RETURNS INTEGER REPRESENTING INTENSITY OF CHANGES IN Y-AXIS
	 * No change --> Return -1
	 * Minimal change --> Return 1
	 * Slight change --> Return 2
	 * Change --> Return 3
	 * More Change --> Return 4
	 * Big change --> Return 5
	 */
	public int getIntensityHL(PVector p1){
		
		int intensity = -1;
		
		if (p1!=null){	

			diffHL(p1);
			
			if (HLgoingUp){
			
				if (HLVelocity == 0)	{
					return intensity;
				}
				else if (HLVelocity < 0.01){
					 intensity = 1;
				}
				else if (HLVelocity < 0.02){
					 intensity = 2;
				}
				else if (HLVelocity < 0.03 ){
					 intensity = 3;
				}
				else if (HLVelocity <  0.06 ){
					 intensity = 4;
				}
				else if (HLVelocity > 0.06) {
					 intensity = 5;
				}
				
			}
			
		}
		
		return intensity;
	}
	
	public static void main(String[] args) {
		PApplet.main(LeafTest.class.getName());
	}

}
