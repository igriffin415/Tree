import processing.core.PApplet;

public class Cloud {
	PApplet app;
	float x= 0f; 
	float y = 0f;
	int color;
	
	public Cloud(PApplet a) {
		this.app = a;
		y = 0.8f;
		color = app.color(255, 255, 255);
	}

	public void draw(float x) {
		this.x = x;
		app.noStroke();
		app.fill(color);
		app.ellipse(x, y, .3f, .2f);
		app.ellipse(x-.2f, y, .45f, .4f);
		app.ellipse(x-.45f, y, .35f, .25f);
	}
	
	public void dark(float dist){
		if(dist == 0)
			color = app.color(255, 255, 255);
		else if(dist > 0 && dist < 3f){
			int c = 255-(int)(50/dist);
			color = app.color(c, c, c);
		}
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}
