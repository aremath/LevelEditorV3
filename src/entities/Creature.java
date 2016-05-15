package entities;

import leveleditorv3.Level;
import processing.core.*;

public class Creature extends Animated{
	int hitpoints;
	int team;
	//int mass;
	int[] hitbox; //[x,y,w,h] RELATIVE TO THE CREATURE'S XPOS AND YPOS
	int[] critbox; //[x,y,w,h]	//TODO hitboxes as their own datatype?
	int[] cooldownReference;
	int[] cooldowns;
	boolean isStun;
	boolean isInvuln;
	boolean collides;
	boolean isGravity;
	boolean isGrounded;
	boolean knocked;
	//TODO USE AN ENUM
	// many of the states have to be consistent across all creatures so that an attack (which ostensibly doesn't know anything about the state of the entity it's hitting
	//can send it into the correct state so here are the states:
	/* #NOTE:
		states:
		0 - idle, the entity is doing nothing. most ai-driven things will have idle send them right into some other state.
		1 - damaged, attacks will send things into this state if they have >50% hp
		2 - heavily damaged, "" <50% hp
		3 - dying, things get sent here when they have 0 hp. at the end of this state, the creature gets taken out of all of the relevant lists so it doesn't get iterated through. it might spawn a corpse entity which just sits there.
		4 - in air / falling, when things get thrown into the air by whatever, they'll get sent here. probably, they can initialize attacks while falling, so most things won't send the enemy here.
		5 - walking right
		6 - walking left
		
	*/
	static int OFFSET = 1; //TODO: this is not the right place for this
	
	
	public Creature(int x, int y, Level l, PApplet p) {
		super(x, y, l, p);
		hitpoints = 0;
		team = 0;
		hitbox = new int[4];
		critbox = new int[4];
		isGrounded = false;
		isGravity = true;
		knocked = false;
		//creatureList.add(this);
	}
	
	//TODO make this actually, you know, work
	public void fixxVel () {
		int nextTile;
		int top;
		int bottom;
		int type;
		top = level.tileCoord(ypos + hitbox[1]);
		bottom = level.tileCoord(ypos + hitbox[1] + hitbox[3]);
		if (xvel < 0) {
			nextTile = level.tileCoord(xpos + hitbox[0] + xvel);
			if (nextTile < 0) {
				xvel = 0;
				xpos = level.tileSize * (nextTile) - hitbox[0] + OFFSET;
			}
			else {
				for (int y = top; y <= bottom; y++) {
					type = level.tileType(level.map[2][nextTile][y]);
					if (type == 0) {
						xvel = 0;
						xpos = level.tileSize * (nextTile + 1) - hitbox[0] + OFFSET;
					}
				}
			}
		}
		else if (xvel > 0) {
			nextTile = level.tileCoord(xpos + hitbox[0] + hitbox[2] + xvel);
			if (nextTile >= level.map.length) {
				xvel = 0;
				xpos = level.tileSize * nextTile - hitbox[0] - hitbox[2] - OFFSET;
			}
			else {
				for (int y = top; y <= bottom; y++) {
					//TODO currentLevel[2] is arbitrary
					type = level.tileType(level.map[2][nextTile][y]);
					if (type == 0) {
						xvel = 0;
						xpos = level.tileSize * nextTile - hitbox[0] - hitbox[2] - OFFSET;
					}
				}
			}
		}  
	}
	
	public void fixyVel () {
		int nextTile;
		int left;
		int right;
		int type;
		left = level.tileCoord(xpos + hitbox[0]);
		right = level.tileCoord(xpos + hitbox[0] + hitbox[2]);
		if (yvel < 0) {
			nextTile = level.tileCoord(ypos + hitbox[1] + yvel);
			for (int x = left; x <= right; x++) {
				type = level.tileType(level.map[2][x][nextTile]);
				if (type == 0) {
					yvel = 0;
					ypos = level.tileSize * nextTile - this.hitbox[1] + OFFSET;
				}
			}
		}
		else if (yvel > 0) {
			nextTile = level.tileCoord(ypos + hitbox[1] + hitbox[3] + yvel);
			for (int x = left; x <= right; x++) {
				type = level.tileType(level.map[2][x][nextTile]);
				if (type == 0) {
					yvel = 0;
					isGrounded = true;  //#TODO: check grounded somewhere else
					ypos = level.tileSize * nextTile - this.hitbox[1] - this.hitbox[3] - OFFSET;
				}
			}
		}  
	}
	
	public void fixVel () {
		//TODO if this.colllides?
		this.fixxVel();
		this.fixyVel();
	}
}
