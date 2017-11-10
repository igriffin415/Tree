import processing.core.PApplet;

public class Seed {
	PApplet app;
	float x, y;
	float speed;
	int color;

	public Seed(PApplet a, float x, float y) {
		app = a;
		this.x = x;
		this.y = y;
		speed = .005f;
		color = app.color(185, 156, 107);
	}
	
	public void update() {
		this.y = this.y - speed;
	}
	
	public void draw() {
		app.stroke(color);
		app.fill(color);
		app.ellipse(x, y, .01f, .03f);
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}
