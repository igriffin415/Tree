
import processing.core.PApplet;

public class Cloud {
	public static final float STARTX = 2.5f;
	public static final float SPEEDX = 0.001f;
	public static final float SPEEDY = 0.0005f;
	
	float ran;
	PApplet app;
	boolean rightLeft = false;
	float x = 3.0f, y = 2.0f;

	public Cloud(PApplet a, float x, float y, boolean leftDirection) {
		app = a;
		
		ran = 1.0f + (float)(Math.random() * 1000.0f); 
		rightLeft = leftDirection;
		if(rightLeft) this.x = x;
		else this.x = -STARTX;
		this.y = y;
	}

	public void draw() {
		//right to left movement
		if(!rightLeft) {
			if(x < -STARTX) rightLeft = true; //x = STARTX;
			
			x = x - SPEEDX;
			
			float changeY = (float) Math.sin((app.millis()+ran)/300f) * 0.002f ;
			y = y + changeY;

			app.noStroke();
			app.fill(255, 255, 255);
			app.ellipse(x, y, .3f, .2f);
			app.ellipse(x-.2f, y, .45f, .4f);
			app.ellipse(x-.45f, y, .35f, .25f);
		} else {
			if(x > STARTX) rightLeft = false;//x = -STARTX;
			
			x = x + SPEEDX;
			float changeY = (float) Math.sin(app.millis()/300f) * .002f ;
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
