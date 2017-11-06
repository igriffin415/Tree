import processing.core.PApplet;

//ten branches, call draw for each branch
public class Tree {
	PApplet app;
	float start;
	Branch[] branches;

	public Tree(PApplet a) {
		app = a;
		branches = new Branch[10];
		fillBranches();
	}
	
	
	/* fills the branch array */
	private void fillBranches() {
		for(Branch branch : branches) {
			//create a branch 
			//put branch in array
		}
	}
	
	public void update() {
		for(Branch branch : branches) {
			//update each branch
		}
	}
	
	public void draw() {
		for(Branch branch : branches) {
			//draw each branch
		}
	}
}
