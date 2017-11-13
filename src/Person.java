import processing.core.PApplet;
import processing.core.PVector;

public class Person {
	Body body;
	PApplet app;
	
	public Person(PApplet app, float size) {
		this.app = app;		
	}

	public void update(Body body) {
		this.body = body;
	}	
	
	public PVector getRightHand() {
		PVector handRight = body.getJoint(Body.HAND_RIGHT);
		if(handRight != null)
			return handRight;
		return null;
	}
	
	public PVector getLeftHand() {
		PVector handLeft = body.getJoint(Body.HAND_LEFT);
		if(handLeft != null)
			return handLeft;
		return null;
	}
	
	public PVector getHead() {
		PVector head = body.getJoint(Body.HEAD);
		if(head != null)
			return head;
		return null;
	}
}