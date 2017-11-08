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


	float xPos;
	float yPos;
	float xEnd;
	float yEnd;
	float angle;
	float random;
	
	private PApplet applet;
	boolean popped;


	public Branch(float x, float y, float angle, PApplet a){
		this.xPos = x;
		this.yPos = y;
		this.angle = angle;
		this.applet = a;
		
		// Calculate endpoint 
		xEnd = (float) (LENGTH*(Math.sin(angle)));
		yEnd = (float) (LENGTH*(Math.cos(angle)));
			
		//get random for curves
		Random rand = new Random();

	    random = rand.nextFloat() * (.05f - 0.03f) + 0.03f;
		
		//draw();
	}

	public void draw(){
		
		applet.noFill();
		applet.stroke(204, 102, 0);
		applet.strokeWeight(.05f);
		
		float midX = (xPos + xEnd) /2;
		float midY = (yPos + yEnd) /2;
		
		applet.beginShape();
		// midpoint

		//curve 
		applet.curveVertex(xPos, yPos);
		applet.curveVertex(xPos, yPos);
		//applet.curveVertex(.5f*midX + .5f*random , 1.5f*midY - random*.6f);
		applet.curveVertex(midX - 2.5f*random , midY + random*2.6f);
		applet.curveVertex(xEnd, yEnd);
		applet.curveVertex(xEnd, yEnd);
		applet.endShape(); 
		
	}	
}
