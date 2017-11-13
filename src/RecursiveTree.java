import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

//ten branches, call draw for each branch
public class RecursiveTree {
	PApplet app;
	float start, bottom;
	
	/** Golden ratio makes the trunk length:branch length ratio aesthetically appealing **/
	public static final float GOLDEN_RATIO = 1.618f;
	/** Number of generation**/ 
	public static final int NUM_GENERATION = 5;
	/** Maximum branching angle of a branch from the trunk **/

	/** Number of branches **/
	public static final int NUM_BRANCHES = 3; 
	
	final static float TRUNK_LENGTH = 0.9f;
	
	final static float TRUNK_WEIGHT = 0.12f;

	float trunkLength = 0;
	
	float maxBranchAngle;
	
	boolean done = false;
	
	ArrayList<RecursiveBranch> branches = new ArrayList<RecursiveBranch>();
	ArrayList<Leaf> leaves = new ArrayList<Leaf>();

	
	public RecursiveTree(PApplet app, float start, float bottom) {
		this.app = app;
		this.start = start;
		this.bottom = bottom;
		
		maxBranchAngle = app.PI/2.5f;
		setupBranch(app, new PVector(start, bottom + TRUNK_LENGTH), TRUNK_LENGTH, app.PI/2.5f, NUM_GENERATION-1, TRUNK_WEIGHT/GOLDEN_RATIO);	  
		//fillBranches();
	}
	
	public void setupBranch(PApplet app, PVector startingPoint, float length, float angle, int generation, float weight){
		// Base case 
	    if( generation == 0 )
	    {  
	    	leaves.add(new Leaf(app, startingPoint.x, startingPoint.y));
	    	//leaves.add(new Leaf(app, startingPoint.x+app.random(0.06f), startingPoint.y+app.random(0.02f)));
	    	leaves.add(new Leaf(app, startingPoint.x-0.06f+app.random(0.06f), startingPoint.y-0.18f+app.random(0.2f)));
	    	leaves.add(new Leaf(app, startingPoint.x-0.06f+app.random(0.06f), startingPoint.y-0.18f+app.random(0.2f)));
	    	//leafPosition.add(new PVector(startingPoint.x, startingPoint.y));
	    }
	    
	    else if (generation > 0)
	    {  
//	    	if(generation == 1)
//		    	leaves.add(new Leaf(app, startingPoint.x+app.random(0.05f), startingPoint.y+app.random(0.05f)));
//		    	leaves.add(new Leaf(app, startingPoint.x-app.random(0.05f), startingPoint.y+app.random(0.05f)));
		    	
	    	for (int i = 0; i < NUM_BRANCHES; i++){
	    	  	// Determine an angle of a branch which will be painted.         
	    	  	float branchangle = (float)(angle-maxBranchAngle + (app.random(2*maxBranchAngle)));
	    	  	// Determine an end point of a branch 
	    	  	PVector branchendPoint = computeEndpoint(startingPoint, length/GOLDEN_RATIO, branchangle); 
	    	  	//Draw a branch
	    	  	branches.add(new RecursiveBranch(app, angle, startingPoint.x, startingPoint.y, branchendPoint.x, branchendPoint.y, weight));
	    	  	// Calculate and store the length of branch which was newly painted by the above line.
	    	  	float branchlength = startingPoint.dist(branchendPoint);
	    	  	//
	    	  	setupBranch(app, branchendPoint, branchlength, branchangle, generation-1, weight/GOLDEN_RATIO );
	      	}
	    }
	}
	
	
	/** 
	 * Compute the point that is length away from point p at the specified angle.
	 * Uses cosine to get the new x coordinate, sine to get the new y coordinate.
	 */
	public static PVector computeEndpoint( PVector p, double length, double angle )
	{
		return new PVector( (float)(p.x + length*Math.cos(angle)), (float)(p.y + length*Math.sin(angle))); // y is sin
	}
	
	/* adds branch to branch array */
//	private void addBranch() {
//		float angle = (float)(Math.random() * 120) + 30;
//		Branch b = new Branch(start, bottom, angle, app);
//	}
		
	/* draw branches in branch array */
	public void draw() {
//		for(Branch b : branches) {
//			b.draw();
//		}
		
		drawTrunk();
		
		// draw branches if drawing trunk is done
		if(done){
	    	RecursiveBranch former = branches.get(0);
	    	former.draw();
	    	for(int i = 1; i < branches.size(); i++){
	    		if(former.isDone())
	    			branches.get(i).draw();
	    		former = branches.get(i);
	    	}
	    }
		
		// draw leaves if drawing branches is done
		if(branches.get(branches.size()-1).isDone()){
			for(Leaf l : leaves){
				app.noStroke();
				l.draw(false);
			}
		}
		
		
		
	}
	
	
	public void drawLeaf(){
		
	}
	
	public void drawTrunk(){
		
		app.strokeWeight(TRUNK_WEIGHT);
		
		if(trunkLength < TRUNK_LENGTH)
			trunkLength = trunkLength + 0.025f;
		else
			done = true;
		app.stroke(130, 84, 59);
		app.line(start, bottom, start, bottom + trunkLength);
		
		//float trunkangle = (float)Math.atan2(bottom+TRUNK_LENGTH - bottom, 0);
		// Calculare and store the length of trunk
		
		// Paint branches using the recursive method        
	}
	
	
	/* fill branch array */
//	private void fillBranches() {
//		for(int i=0; i < branches.length; i++) {
//			float angle = (float)(Math.random() * 120) + 30;
//			branches[i] = new Branch(start, bottom, angle, app);
//		}
//	}
}