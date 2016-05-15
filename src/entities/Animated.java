package entities;

import leveleditorv3.Level;
import processing.core.*;

public class Animated extends Entity {
	int state;			//TODO use an enum
	int stateCounter;
	int[] numsArray;	//TODO use an enum
	float xvel;
	float yvel;
	boolean active;  //?? TODO probably turns off for cutscenes and such.
	
	public Animated(int x, int y, Level l, PApplet p) {
		super(x, y, l, p);
		xvel = 0;
		yvel = 0;
		isSolid = false;
		//TODO: what arraylists is Level going to have?
	}
	
	public void stateFunction () {
		
	}
	
}
