
import processing.core.PApplet;

public class Cloud {
	public static final float STARTX = 2.5f;
	public static final float STARTY = .8f;
	
	public static final float SPEEDX = 0.001f;
	public static final float SPEEDY = 0.0005f;
	float randomYStart;
	
	PApplet app;
	boolean rightLeft = false;
	float x = 3.0f, y = 2.0f;

	public Cloud(PApplet a, boolean leftDirection) {
		app = a;
		randomYStart = .2f + (float)(Math.random() * .8f); 
		rightLeft = leftDirection;
		if(rightLeft) x = STARTX;
		else x = -STARTX;
		y = randomYStart;
	}

	public void draw() {
		//right to left movement
		if(!rightLeft) {
			if(x < -STARTX) x = STARTX;
			
			x = x - SPEEDX;
			
			float changeY = (float) Math.sin(app.millis()/500f) * 0.006f ;
			y = y + changeY;

			app.noStroke();
			app.fill(255, 255, 255);
			app.ellipse(x, y, .3f, .2f);
			app.ellipse(x-.2f, y, .45f, .4f);
			app.ellipse(x-.45f, y, .35f, .25f);
		} else {
			if(x > STARTX) x = -STARTX;
			
			x = x + SPEEDX;
			float changeY = (float) Math.sin(app.millis()/500f) * .006f ;
			y = y + changeY;

			app.noStroke();
			app.fill(255, 255, 255);
			app.ellipse(x, y, .3f, .2f);
			app.ellipse(x-.2f, y, .45f, .4f);
			app.ellipse(x-.45f, y, .35f, .25f);
		}
		
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}
