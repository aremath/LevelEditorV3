package entities;

import leveleditorv3.Level;
import processing.core.*;

public class Entity {
	String name;
	float xpos;
	float ypos;
	PImage currentFrame; 	//some may display multiple frames in multiple places instead of creating new entities??
	int layer;     		//the graphics layer that the object is in. Things in higher layers get drawn later, on top of the rest. player is in layer #TODO decide 'main' layer number
	String[] images;
	PImage[] frames;		//may eventually use a single image, using extract() to find the right parts of it to animate. i.e.
	// this.currentFrame = get(frame, ....) and have a single frame instead of a list #TODO (maybe)
	PApplet p;
	Level level;
	
	//TODO: if it's solid, I need a collision box to check collision with
	boolean isSolid; //?
	boolean active;
	
	//activator method?
	
	public Entity(int x, int y, Level _l, PApplet _p) {
		xpos = x;
		ypos = y;
		_l.entityList.add(this);
		level = _l;
		p = _p;
	}
	
	public void activate () {
		
	}
}