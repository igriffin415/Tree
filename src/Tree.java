import processing.core.PApplet;

//ten branches, call draw for each branch
public class Tree {
	PApplet app;
	float start;
	Branch[] branches;

	public Tree(PApplet a, float s) {
		app = a;
		start = s;
		branches = new Branch[10];
	}
	
	
	/* adds branch to branch array */
	public void addBranch() {
		//Branch b = new Branch(); //either i send in a random number or empty constructor
	}
	
	/* update branches in branch array */
	public void update() {
		for(Branch branch : branches) {
			//update each branch
		}
	}
	
	/* draw branches in branch array */
	public void draw() {
		for(Branch branch : branches) {
			//branch.draw();
			//draw each branch
		}
	}
}
