import processing.core.PApplet;

public class Rain {
	PApplet app;
	float x, y;
	float speed;
	int color;

	public Rain(PApplet a, float x, float y) {
		this.app = a;
		this.x = x;
		this.y = y;
		speed = app.random(.006f, .010f);
		color = app.color(71, 170, 224);
	}
	
	public void update() {
		this.y = this.y - speed;
	}
	
	public void draw() {	
		app.noStroke();
		app.fill(color, 100);
		
		for (int i = 2; i < 8; i++ ) {
			app.ellipse(x, y - i*0.006f, i*.0045f, i*.0045f);
		}
		update();
	}
	
	public float getX() {
		return this.x;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public float getY() {
		return this.y;
	}

}
