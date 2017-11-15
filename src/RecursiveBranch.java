import java.awt.Color;
import java.util.Random;

import processing.core.PApplet;

public class RecursiveBranch {
	// starting point (x,y)
	// drawing a curve
	// every (TIME) change random angle

//	private static final float WIDTH = .2f;
//
//	private static final int LENGTH = 1;
//	private static final float FINAL_SIZE = 1.0f;
//	private static final float SPEED = 0.003f;
	
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
	long startTime;
	int growTime = 6000;
	float interpolate;
	float currWeight;
	float maxWeight;
	float currentLength = 0;
	float size;
	boolean done = false;
	private PApplet applet;
	boolean popped;
	boolean doneX = false, doneY = false;

	float growthSpeed = 0;
	
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

		this.currWeight = weight - 0.01f;
		maxWeight = weight + 0.001f;

		this.xEnd = endX;
		this.yEnd = endY;
		
	}
	
	public void draw(){
		if(currWeight < maxWeight) currWeight = currWeight + 0.0001f;
		else currWeight = maxWeight;
	
		applet.strokeWeight(currWeight);
		
		if(currentLength < 1f) currentLength = currentLength + 0.5f;
		else done = true;

		float x = applet.lerp(xStart, xEnd, currentLength);
		float y = applet.lerp(yStart, yEnd, currentLength);
		applet.line(xStart, yStart, x, y);
	}

	public boolean isDone() {
		return done;
	}

	public void setGrowSpeed(float distance){
		if( distance > 2 )
			growthSpeed = 0.005f;			
		else if( distance > 1.1 )
			growthSpeed = 0.1f;
		else if( distance > 0.8 )
			growthSpeed = 0.2f;
		else if( distance > 0.5 )
			growthSpeed = 0.5f;
	}
}

