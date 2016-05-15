package changes;

import menus.LevelViewer;

public class LevelChange extends Change {
	
	int prevTile;
	int changeTile;
	
	int tileL;
	int tileX;
	int tileY;
	
	public LevelChange(int pt, int ct, int l, int x, int y, LevelViewer p) {
		super(p);
		
		prevTile = pt;
		changeTile = ct;
		tileL = l;
		tileX = x;
		tileY = y;
		
		//super(p);
	}
	
	public void undo() {
		parent.level.map[tileL][tileY][tileX] = prevTile;
		super.undo();
	}
	
	
}
