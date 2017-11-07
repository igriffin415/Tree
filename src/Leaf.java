import processing.core.PApplet;

//static, blossom or fall
public class Leaf {
	
	PApplet app;
	float x, y;
	
	// falling speed
	float speed;
	// random greenish color
	int[] color = new int[3];
	int x_direction = (int)(Math.random()*2);
	int changeInGreen = 0;
	static final int MAX_RED = 220; 
	static final int MAX_GREEN = (int)(Math.random()*255); 
	
	public Leaf(PApplet a, float x, float y){
		app = a;
		this.x = x;
		this.y = y;
		speed = .004f;
		// generate random greenish color
		color[0] = (int)(Math.random()*80);
		color[1] = (int)(Math.random()*90)+140;
		color[2] = (int)(Math.random()*40);
	}
	
	public void falling(){
		if( !touched() ){
			y = y - speed;

			if(x_direction == 0)
				x = x - 0.002f;
			else
				x = x + 0.002f;
		}
	}
	
	// return true if the leaf touched the ground
	public boolean touched(){
		return y <= -1f;
	}
	
	public void blossom(){
		
	}
	
	public void turnRed(){
		if(color[0] < MAX_RED )
			color[0] = color[0] + 1;
		if(changeInGreen < 50){
			color[1] = color[1] - 1;
			changeInGreen++;
		}
		color[2] = color[2] - 1;
	}
	
	public void draw(){
		app.fill(color[0], color[1], color[2], 170);
		app.ellipse(x, y, .2f, .2f);
	}
	
}
