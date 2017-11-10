import java.io.IOException;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;

public class TreeApp extends PApplet {
	
	public static enum COLOR_STATE { RED, GREEN, BLUE };
	public COLOR_STATE colorState = COLOR_STATE.RED;

	String recordingFile = "test.kinect";
	HashMap<Long, Person> tracks = new HashMap<Long, Person>();
	HashMap<Long, Person> twoPeople = new HashMap<Long, Person>();
	Person pers1, pers2;
	Seed seed;
	Tree tree;
	
	float bottom;
	
	KinectBodyDataProvider kinectReader;
	PersonTracker tracker = new PersonTracker();

	public static float PROJECTOR_RATIO = 1080f/1920.0f;

	public void settings() {
		createWindow(true, false, .5f);
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
		
		seed = null;
		tree = null;
		kinectReader.start();

	}
	public void draw(){
		setScale(.5f);

		KinectBodyData bodyData = kinectReader.getMostRecentData();

		tracker.update(bodyData);
		background(229, 255, 204);

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
				drawIfValid(p.getLeftHand());
				drawIfValid(p.getRightHand());
			}
		}
		
		//if there is a tree, update & draw otherwise check if there's a seed to update and draw. 
		//don't want a tree and seed 
		if(tree != null) {
			this.strokeWeight(.01f);
			tree.update();
			tree.draw();
		}
		else if(seed != null) {
			seed.update();
			seed.draw();
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
			
			//if the hands intersect and a seed is not falling
			//need two seperate in order to get correct position for falling
			if(checkIntersect(pers1.getLeftHand(), pers2.getRightHand()) && 
			   seed == null) {
				seed = new Seed(this, pers1.getLeftHand().x, pers1.getLeftHand().y);
			}
			else if(checkIntersect(pers1.getRightHand(), pers2.getLeftHand()) &&
					   seed == null) {
				seed = new Seed(this, pers2.getLeftHand().x, pers2.getLeftHand().y);
			}
			
			if(seed != null && seed.getY() <= bottom && tree == null) {
				tree = new Tree(this, seed.getX(), bottom);
			}
		}
		else if(twoPeople.size() == 0) {
			tree = null;
		}
	}


	public static void main(String[] args) {

		PApplet.main(TreeApp.class.getName());
	}
	
	/**
	 * Determine whether or not the right and left hand are currently together.
	 * @return true if the hands are together
	 */
	public boolean checkIntersect(PVector hand1, PVector hand2) {	
		float diam = .65f;
		if (hand1!=null && hand2!=null)	{
			//calculate the distance between the hands
			float distance = dist(hand1.x, hand1.y, hand2.x, hand2.y);
			if(distance <= diam) {
				return true;
			}
		}
		return false;
	}
	
	public void drawIfValid(PVector vec) {
		if(vec!= null)
		{
			fill(0);
			stroke(0);
			strokeWeight(.1f);
			ellipse(vec.x, vec.y, .1f, .1f);
		}
		
		
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
