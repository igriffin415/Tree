import java.io.IOException;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;

public class RecursiveTreeApp extends PApplet {
	private static long DEFAULT_STAGGER = 5000; //ms
	
	String recordingFile = "test.kinect";
	HashMap<Long, Person> tracks = new HashMap<Long, Person>();
	HashMap<Long, Person> twoPeople = new HashMap<Long, Person>();
	Person pers1, pers2;
	Cloud cloud1;
	Cloud cloud2;
	RecursiveTree tree;
	
	final static int NUM_DROPS = 60;
	
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
	
	long curStagger = DEFAULT_STAGGER;
	long prevLeaf = 0;
	
	float bottom;
	
	Rain[] rain = new Rain[NUM_DROPS];
	
	KinectBodyDataProvider kinectReader;
	PersonTracker tracker = new PersonTracker();

	public static float PROJECTOR_RATIO = 1080f/1920.0f;

	public void settings() {
		createWindow(false, true, .5f);
	}

	public void setup(){

		/*
		 * use this code to run your PApplet from data recorded by UPDRecorder 
		 */
		
		if(recordingFile != null)
			try {
				kinectReader = new KinectBodyDataProvider("test.kinect", 10);
			} catch (IOException e) {
				System.out.println("Unable to create kinect producer");
			}
		else {
			kinectReader = new KinectBodyDataProvider(8008);
		}
		
		tree = null;
		cloud1 = new Cloud(this);
		cloud2 = new Cloud(this);
		kinectReader.start();

	}
	public void draw(){
		setScale(.5f);
		
		
		KinectBodyData bodyData = kinectReader.getMostRecentData();

		tracker.update(bodyData);
		background(204, 230, 255);
		
		//cloud3.draw();
		for(Long id : tracker.getEnters()) {
			tracks.put(id,  new Person(this, .1f));
			if(twoPeople.size() < 2)
			{
				twoPeople.put(id,  new Person(this, .1f));
			}
		}
		
		for(Long id: tracker.getExits()) {
			tracks.remove(id);
			if(twoPeople.containsKey(id))
				twoPeople.remove(id);
		}

		
		for(Body b : tracker.getPeople().values()) {
			if(twoPeople.containsKey(b.getId()))
			{
				Person p = twoPeople.get(b.getId());
				p.update(b);
			}
		}

		//if there are two people set each person to
		//pers1 and pers2 for use
		if(twoPeople.size() >= 2)
		{
			int count = 1;
			//set people
			for(Long id : twoPeople.keySet()) {
				if(count == 1)
					pers1 = twoPeople.get(id);
				else 
					pers2 = twoPeople.get(id);
				count++;
			}
			
			if(checkIntersect(pers1.getRightHand(), pers2.getLeftHand()) &&
					rain[0] == null) {
				for(int i = 0; i < NUM_DROPS; i++){
					rain[i] = new Rain(this, pers1.getRightHand().x-0.3f + random(0.6f), 0.8f);
				}
				rain[0].setSpeed(0.006f);
			}
			
			if(rain[0] != null && rain[0].getY() <= bottom && tree == null) {
				tree = new RecursiveTree(this, rain[0].getX(), bottom);
			}
			
			if(tree != null)
				tree.setGrowSpeed(getDistance(pers1.getHead(), pers2.getHead()));
			
			//if there is a tree and the tree is done drawing, start drawing leaves
			if(tree != null && tree.canDrawLeaves()){
				//if two people are close draw leaves slower
				if( getDistance(pers1.getHead(), pers2.getHead()) < 0.5f && getDistance(pers1.getHead(), pers2.getHead()) > 0 ){
					curStagger += 1000;
				} //if farther draw leaves faster
				else if( getDistance(pers1.getHead(), pers2.getHead()) >= 0.5f && getDistance(pers1.getHead(), pers2.getHead()) < 1f ){
					curStagger -= 1000;
				}
				//if stagger goes too low bring to zero
				if(curStagger <= 0) {
					curStagger = DEFAULT_STAGGER;
				}
				//draw leaves at stagger time
				if((System.currentTimeMillis() - prevLeaf) > curStagger) {
					prevLeaf = System.currentTimeMillis();
					tree.drawLeaf();
				}
				
				//depending on two people raising arms, leaves get yellow and then eventually fall off
				if(getIntensityHR(pers1.getRightHand()) == getIntensityHR(pers2.getRightHand()) && getIntensityHL(pers1.getLeftHand()) == getIntensityHL(pers2.getLeftHand())){
					if(getIntensityHR(pers1.getRightHand()) > 4 || getIntensityHL(pers1.getLeftHand()) > 4){
						tree.turnYellow();
						tree.turnYellow();
						tree.turnYellow();
						tree.turnYellow();
					}
					else if(getIntensityHR(pers1.getRightHand()) > 3 || getIntensityHL(pers1.getLeftHand()) > 3){
						tree.turnYellow();	
						tree.turnYellow();
						tree.turnYellow();
					}
					else if(getIntensityHR(pers1.getRightHand()) > 2 || getIntensityHL(pers1.getLeftHand()) > 2){
						tree.turnYellow();	
						tree.turnYellow();
					}
					else if(getIntensityHR(pers1.getRightHand()) > 0 || getIntensityHL(pers1.getLeftHand()) > 0){
						tree.turnYellow();	
					}
				}
			}
			
			//draw clouds at hand's horizontal position
			if(pers1.getRightHand() != null && pers2.getLeftHand() != null){
				cloud1.draw(pers1.getRightHand().x); 
				cloud2.draw(pers2.getLeftHand().x);
			}
			//rain 
			if(rain[0] == null){
				cloud1.dark(getDistance(pers1.getRightHand(), pers2.getLeftHand()));
				cloud2.dark(getDistance(pers1.getRightHand(), pers2.getLeftHand()));
			}
			else{
				cloud1.dark(0);
				cloud2.dark(0);
			}
		}
		else if(twoPeople.size() == 0) {
			tree = null;
		}
		//if there is a tree, update & draw otherwise check if there's a seed to update and draw. 
		//don't want a tree and seed 
		if(tree != null) {
			tree.draw();
		}
		else if( rain[0] != null) {
			for(Rain r : rain){
				r.draw();
			}
		}
		//ground
		noStroke();
		this.fill(99, 206, 120);
		this.rect(-2.5f, -1.2f, 5f, .3f);
	}


	public static void main(String[] args) {

		PApplet.main(RecursiveTreeApp.class.getName());
	}
	
	/**
	 * Determine whether or not the right and left hand are currently together.
	 * @return true if the hands are together
	 */
	public boolean checkIntersect(PVector hand1, PVector hand2) {	
		float diam = .30f;
		float distance = getDistance(hand1, hand2);
		if(distance > 0 && distance <= diam)
			return true;
		return false;
	}
	
	public float getDistance(PVector pt1, PVector pt2) {
		float distance = 0;
		if(pt1 != null && pt2 != null) {
			distance = dist(pt1.x, pt1.y, pt2.x, pt2.y);
		}
		else {
			distance = -1;
		}
		return distance;
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
		bottom = -PROJECTOR_RATIO/zoom;
	}
}
