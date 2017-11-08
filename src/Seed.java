import processing.core.PApplet;

public class Seed {
	PApplet app;
	float x, y;
	float speed;

	public Seed(PApplet a, float x, float y) {
		app = a;
		this.x = x;
		this.y = y;
		speed = .005f;
	}
	
	public void update() {
		this.y = this.y - speed;
	}
	
	public void draw() {
		app.stroke(50);
		app.fill(50);
		app.ellipse(x, y, .1f, .1f);
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}
