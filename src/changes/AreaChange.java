package changes;

import menus.LevelViewer;

public class AreaChange extends Change {
	public int[][] prevTiles;
	public int[][] changeTiles;
	
	int tileL;
	int tileX;
	int tileY;
	
	
	
	
	public AreaChange(int xsize, int ysize, int l, int x, int y, LevelViewer p) {
		super(p);
		
		//TODO: take the arrays as arguments?
		prevTiles = new int[ysize][xsize];
		changeTiles = new int[ysize][xsize];
		
		tileL = l;
		tileX = x;
		tileY = y;
		
	}
	
	public void undo() {
		
		//System.out.println(this.prevTiles.length);
		
		for (int y = 0; y < prevTiles.length; y++) {
			for (int x = 0; x < prevTiles[0].length; x++) {
				
				//System.out.println(this.parent.length);
				
				if ((tileX + x < parent.level.map[0][0].length) && (tileY + y < parent.level.map[0].length)) {
					//System.out.println(prevTiles[y][x]);
					parent.level.map[tileL][tileY + y][tileX + x] = prevTiles[y][x];
				}
				
			}
		}
		
		super.undo();
		
	}
	
}