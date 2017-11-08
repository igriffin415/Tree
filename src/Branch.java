import java.awt.Color;
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
				
		//draw();
	}

	public void draw(){
		
		applet.noFill();
		applet.stroke(255, 255, 0);
		//applet.line(xPos, yPos, xEnd, yEnd);
		float midX = (xPos + xEnd) /2;
		float midY = (yPos + yEnd) /2;
		
		applet.beginShape();
		// midpoint

		//curve 
		applet.curveVertex(xPos, yPos);
		//applet.curveVertex(midX , midY);
		applet.curveVertex(xEnd, yEnd);
		applet.endShape(); 
		
	}	
}
