import java.awt.Color;
import processing.core.PApplet;

public class Branch {
	// starting point (x,y)
	// drawing a curve
	// every (TIME) change random angle

	private static final float WIDTH = .2f;
	private static final float DECREASE = .005f;

	float xPos;
	float yPos;
	float angle;
	
	private PApplet applet;
	boolean popped;


	public Branch(float x, float y, float angle, PApplet a){
		this.xPos = x;
		this.yPos = y;
		this.angle = angle;
		this.applet = a;
				
		draw();
	}

	public void draw(){
		applet.noFill();
		applet.stroke(255, 255, 0);
		
		applet.beginShape();

		applet.line(85, 20, 10, 10);
		applet.line(90, 90, 15, 80);
		applet.stroke(0, 0, 0);
		applet.bezier(85, 20, 10, 10, 90, 90, 15, 80);
		
		applet.endShape();
		
	}	
}
