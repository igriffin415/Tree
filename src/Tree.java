import processing.core.PApplet;

//ten branches, call draw for each branch
public class Tree {
	PApplet app;
	float start, bottom;
	Branch[] branches;

	public Tree(PApplet a, float s, float b) {
		app = a;
		start = s;
		bottom = b;
		branches = new Branch[10];
		fillBranches();
	}
	
	
	/* adds branch to branch array */
//	private void addBranch() {
//		float angle = (float)(Math.random() * 120) + 30;
//		Branch b = new Branch(start, bottom, angle, app);
//	}
	
	/* update branches in branch array */
	public void update() {
		for(Branch b : branches) {
			//b.update();
		}
	}
	
	/* draw branches in branch array */
	public void draw() {
		for(Branch b : branches) {
			b.draw();
		}
	}
	
	/* fill branch array */
	private void fillBranches() {
		for(int i=0; i < branches.length; i++) {
			float angle = (float)(Math.random() * 120) + 30;
			branches[i] = new Branch(start, bottom, angle, app);
		}
	}
}
