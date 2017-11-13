import java.awt.Color;
import java.util.Random;

import processing.core.PApplet;

public class Branch {
	// starting point (x,y)
	// drawing a curve
	// every (TIME) change random angle

	private static final float WIDTH = .2f;
	private static final float DECREASE = .005f;

	private static final int LENGTH = 1;
	private static final float FINAL_SIZE = 1.0f;
	private static final float SPEED = 0.001f;
	private static final float MAX_STROKE = 0.06f;
	
	float xStart;
	float yStart;
	float xEnd;
	float yEnd;
	float xMid;
	float yMid;
	float curEndX;
	float curEndY;
	float angle;
	float currStroke = 0.003f;
	float random;
	long startTime;
	int growTime = 5000;
	float interpolate = 0;
	
	float size;
	
	private PApplet applet;
	boolean popped;


	public Branch(float x, float y, float angle, PApplet a){
		//get random for curves
		Random rand = new Random();
		startTime = System.currentTimeMillis();
		
		size = y;

	    random = rand.nextFloat() * (.05f - 0.03f) + 0.03f;
				
		this.xStart = x;
		this.yStart = y;
		
		this.angle = angle;
		this.applet = a;
		
		// Calculate endpoint 
		this.xEnd = (float) (LENGTH*(Math.sin(angle)));
		this.yEnd = (float) (LENGTH*(Math.cos(angle)));
		
		this.curEndX = xStart;
		this.curEndY = yStart;
	}
	

	public void draw(){
		interpolate = (float)(System.currentTimeMillis()-startTime)/growTime;
		
		curEndX = (curEndX >= xEnd) ? xEnd : ((1-interpolate)*xStart + interpolate*xEnd);
		curEndY = (curEndY >= yEnd) ? yEnd : ((1-interpolate)*yStart + interpolate*yEnd);
		
		//calculate midpoint
		this.xMid = ((xStart + curEndX) /2.0f) - 2.5f * random;
		this.yMid = ((yStart + curEndY) /2.0f) + random * 2.6f;

		applet.noFill();
		applet.stroke(204, 102, 0);
		if(currStroke < MAX_STROKE) currStroke = currStroke + 0.0001f;
		else currStroke = MAX_STROKE;
	
		applet.strokeWeight(currStroke);
		
		applet.beginShape();
		applet.curveVertex(xStart, yStart);
		applet.curveVertex(xStart, yStart);
		applet.curveVertex(xMid, yMid);
		applet.curveVertex(curEndX, curEndY);
		applet.curveVertex(curEndX, curEndY);
		applet.endShape(); 
		
	}	
}
