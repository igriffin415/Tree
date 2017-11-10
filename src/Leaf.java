import processing.core.PApplet;

//static, blossom or fall
public class Leaf {
	
	PApplet app;
	float x, y;
	float size;
	// falling speed
	float speed;
	// random greenish color
	int[] color = new int[3];
	int transparency = 170;
	int x_direction = (int)(Math.random()*2);
	int changeInGreen = 0;
	static final int MAX_RED = 220; 
	static final int MAX_GREEN = (int)(Math.random()*255); 
	
	public Leaf(PApplet a, float x, float y){
		this.app = a;
		this.x = x;
		this.y = y;
		// random falling speed
		speed = (float)Math.random()*0.003f + 0.002f;
		// random size of leaf
		this.size = (float)Math.random()*0.08f + 0.13f;
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
		else
			fadeaway();
	}
	
	// leaf fade away
	public void fadeaway(){
		if(transparency > 0)
			transparency= transparency-5;
	}	
	
	// return true if the leaf touched the ground
	public boolean touched(){
		return y <= -1f;
	}
	
	public void turnRed(){
		if(color[0] < MAX_RED )
			color[0] = color[0] + 1;

		if(changeInGreen < 60){
			color[1] = color[1] - 1;
			changeInGreen++;
		}
		
		if(color[2] > 0)
			color[2] = color[2] - 1;
	}
	
	public void draw(){
		app.fill(color[0], color[1], color[2], transparency);
		app.ellipse(x, y, size, size);
	}
}
