
import processing.core.PApplet;

public class Cloud {
	public static final float STARTX = 1.9f;
	public static final float STARTY = .8f;
	
	public static final float SPEEDX = 0.001f;
	public static final float SPEEDY = 0.0005f;
	
	PApplet app;
	int alt = 1;
	float x = 3.0f, y = 2.0f;

	public Cloud(PApplet a) {
		app = a;
		x = STARTX;
		y = STARTY;
	}

	public void draw() {
		if(x < -2.3f) x = STARTX;
		
		x = x - SPEEDX;
		float changeY = (float) Math.sin(app.millis()/200f) * .006f ;
		y = y + changeY;
		app.stroke(255, 255, 255);
		app.fill(255, 255, 255);
		app.ellipse(x, y, .2f, .2f);
		app.ellipse(x-.3f, y, .4f, .4f);
		app.ellipse(x-.5f, y, .3f, .2f);
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}
