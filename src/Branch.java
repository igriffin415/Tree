import java.awt.Color;
import processing.core.PApplet;

public class Branch {
	// starting point (x,y)
	// drawing a curve
	// every (TIME) change random angle

	private static final float WIDTH = .2f;
	private static final float DECREASE = .005f;
	private static int LENGTH = 10;

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
				
		draw();
	}

	public void draw(){
		
		applet.noFill();
		applet.stroke(255, 255, 0);
		applet.line(xPos, yPos, xEnd, yEnd);
		
		
		/*applet.beginShape();
		// midpoint
		float midX = (xPos + xEnd) /2;
		float midY = (yPos + yEnd) /2;
		//curve 1
		applet.curve(xPos, yPos, 3.5f*(xPos + midX), .5f*(xPos + midX), 5.5f*(xPos + midX), 10.5f*(xPos + midX), midX, midY);
		//curve 2
		applet.curve(midX, midY, 3.5f*(xPos + midX), 3.5f*(xPos + midX), 50.0f, 20.0f, xEnd, yEnd);
		applet.endShape(); */
		
	}	
}
