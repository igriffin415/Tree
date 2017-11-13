import java.awt.Color;
import java.util.Random;

import processing.core.PApplet;

public class RecursiveBranch {
	// starting point (x,y)
	// drawing a curve
	// every (TIME) change random angle

	private static final float WIDTH = .2f;
	private static final float DECREASE = .005f;

	private static final int LENGTH = 1;
	private static final float FINAL_SIZE = 1.0f;
	private static final float SPEED = 0.001f;
	
	float xStart;
	float yStart;
	float xEnd;
	float yEnd;
	float xMid;
	float yMid;
	float curEndX;
	float curEndY;
	float angle;
	float random;
	float weight;
	long startTime;
	int growTime = 5000;
	float interpolate;
//	float branchLength;
	float currentLength = 0;
	float size;
	boolean done = false;
	private PApplet applet;
	boolean popped;


	public RecursiveBranch(PApplet app, float angle, float startX, float startY, float endX, float endY, float weight){
		//get random for curves
		Random rand = new Random();
		startTime = System.currentTimeMillis();
		
		size = startY;

	    random = rand.nextFloat() * (.05f - 0.03f) + 0.03f;
	    
		this.xStart = startX;
		this.yStart = startY;
		
		this.angle = angle;
		this.applet = app;
		this.weight = weight;
		// Calculate endpoint 
		this.xEnd = endX;
		this.yEnd = endY;
		
//		branchLength = app.dist(xStart, yStart, xEnd, yEnd);
	}
	
	public void draw(){
		applet.strokeWeight(weight);
		if(currentLength < 1f){
			currentLength = currentLength + 0.5f;
		}
		else
			done = true;

		float x = applet.lerp(xStart, xEnd, currentLength);
		float y = applet.lerp(yStart, yEnd, currentLength);
		applet.line(xStart, yStart, x, y);
	}

	public boolean isDone(){
		return done;
	}
	
	
//	public void draw(){
//		interpolate = (float)(System.currentTimeMillis()-startTime)/growTime;
//		
//		
//		curEndX = (curEndX >= xEnd) ? xEnd : ((1-interpolate)*xStart + interpolate*xEnd);
//		curEndY = (curEndY >= yEnd) ? yEnd : ((1-interpolate)*yStart + interpolate*yEnd);
//		
//		
//		
//		//calculate midpoint
//		this.xMid = ((xStart + curEndX) /2.0f) - 2.5f * random;
//		this.yMid = ((yStart + curEndY) /2.0f) + random * 2.6f;
//
//		applet.noFill();
//		applet.stroke(204, 102, 0);
//		applet.strokeWeight(weight);
//		
//		applet.beginShape();
//
//		//curve 
//		applet.curveVertex(xStart, yStart);
//		applet.curveVertex(xStart, yStart);
//		//applet.curveVertex(.5f*midX + .5f*random , 1.5f*midY - random*.6f);
//		applet.curveVertex(xMid, yMid);
//		applet.curveVertex(curEndX, curEndY);
//		applet.curveVertex(curEndX, curEndY);
//		applet.endShape(); 
//		
//	}	
}
