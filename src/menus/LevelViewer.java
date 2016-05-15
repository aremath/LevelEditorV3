package menus;

import processing.core.*;
import leveleditorv3.*;
import java.lang.Math;
import java.util.ArrayList;
import changes.*;

public class LevelViewer extends menuItem {
	
	//public Menu parent;
	
	public Level level;
	
	public boolean cameraControl = true;
	
	int cameraX;
	int cameraY;
	int cameraXmin;
	int cameraYmin;
	int cameraXmax;
	int cameraYmax;
	
	int selectedLayer;
	int selectedX;
	int selectedY;
	
	int lastClickX = 0;
	int lastClickY = 0;
	
	int shiftX = 0;
	int shiftY = 0;
	
	int shiftLayer;
	
	int[][] shiftTiles;
	
	public boolean shiftSelected;
	
	public int selectedTile = 0;
	
	int nLayers;
	boolean visibleLayers[];
	
	boolean controlC = false;
	int controlCArray[][];
	
	public static int maxChanges = 100;
	public ArrayList<Change> changes;
	
	
	public LevelViewer(float x, float y, float w, float h, Menu parent, LevelEditorV3 _p, Level l) {
		xpos = x + parent.xpos;
		ypos = y + parent.ypos;
		xSize = w;
		ySize = h;
		level = l;
		
		nLayers = level.map.length;
		visibleLayers = new boolean[nLayers];
		
		changes = new ArrayList<Change>();
		
		visibleLayers[2] = true;
		selectedLayer = 2;
		
		parent.items.add(this);
		//this.parent = p;
		
		cameraX = 0;
		cameraY = 0;
		
		cameraXmin = 0;
		cameraYmin = 0;
		
		cameraXmax = level.map[0][0].length * level.tileSize;
		cameraYmax = level.map[0].length * level.tileSize;
		
		p = _p;
	}
	
	public void drawM() {
		//TODO only partially draw tiles that are at the edge
		//TODO draw a border around the whole thing?
		
		for (int l = 0; l < nLayers; l++){
			if (visibleLayers[l]){
				int[][] tiles = getOnscreenTiles(level.map[l]);
				PImage tileImage;
				int xoffset = -(cameraX % level.tileSize);
				int yoffset = -(cameraY % level.tileSize);
				for (int y = 0; y < tiles.length; y++) {
					for (int x = 0; x < tiles[0].length; x++) {
						int currentTile = tiles[y][x];
						tileImage = level.tileset[currentTile];
						p.image(tileImage, xpos + level.tileSize*(x - 1) + xoffset, ypos + level.tileSize*(y - 1) + yoffset);
					}
				}
			}
		}
		
		drawMouseBox();
		
		if (changes.size() > maxChanges) {
			changes.remove(changes.get(0));
		}
		
	}
	
	public void drawMouseBox () {
		float[] pos = relMousePos();
		//if ((pos[0] >= 0) && (pos[1] >= 0)){
		if (mouseOver) {
			selectedX = level.tileCoord(pos[0] + cameraX);
			selectedY = level.tileCoord(pos[1] + cameraY);
			p.stroke(Colors.selectColor);
			p.noFill();
			if (p.keysDown[Keys.SHIFT]) {
				int xsize = Math.abs(lastClickX - selectedX) + 1;
				int ysize = Math.abs(lastClickY - selectedY) + 1;
				int xc = Math.min(lastClickX, selectedX);
				int yc = Math.min(lastClickY, selectedY);
				
				//TODO make sure this can't overflow the boundaries
				p.rect(xpos + xc * level.tileSize - cameraX, ypos + yc * level.tileSize - cameraY, level.tileSize * xsize, level.tileSize * ysize);
			}
			else {
				p.rect(xpos + level.snapToGrid(pos[0]), ypos + level.snapToGrid(pos[1]), level.tileSize, level.tileSize);
			}
			
			
		}
		
		if (this.shiftSelected) {
			
			int xsize = shiftTiles[0].length;
			int ysize = shiftTiles.length;
			int xc = shiftX;
			int yc = shiftY;
	
			p.stroke(Colors.selectColor);
			p.noFill();
			//TODO make sure this can't overflow the boundaries
			p.rect(xpos + xc * level.tileSize - cameraX, ypos + yc * level.tileSize - cameraY, level.tileSize * xsize, level.tileSize * ysize);
		}
	}
	
	public void onClick () {
		if (p.keysDown[Keys.SHIFT]) {
			shiftSelected = true;
			shiftLayer = selectedLayer;
			
			int xStart = 0;
			int yStart = 0;
			
			int xSize = lastClickX - selectedX;
			int ySize = lastClickY - selectedY;
			
			if (xSize < 0){
				xSize = Math.abs(xSize);
				xStart = lastClickX;
				shiftX = lastClickX;
			}
			else {
				xStart = selectedX;
				shiftX = selectedX;
			}
			
			if (ySize < 0){
				ySize = Math.abs(ySize);
				yStart = lastClickY;
				shiftY = lastClickY;
			}
			else {
				yStart = selectedY;
				shiftY = selectedY;
			}
			
			shiftTiles = new int[ySize + 1][xSize + 1];
			
			int xcoord, ycoord;
			
			for (int y = 0; y <= ySize; y++){
				for (int x = 0; x <= xSize; x++){
					xcoord = xStart + x;
					ycoord = yStart + y;
					if ((ycoord > 0) && (xcoord > 0) && (ycoord < level.map[0].length) && (xcoord < level.map[0][0].length)) {
						shiftTiles[y][x] = level.map[shiftLayer][ycoord][xcoord];
					}
				}
			}
			
			SelectChange s = new SelectChange(this);
			
		}
		else {
			shiftSelected = false;
		}
	}
	
	public void continousClick() {
		if (p.keysDown[Keys.SHIFT]) {
			
			
		}
		
		else {
			if (p.mouseButton == PConstants.LEFT) {
				if (level.map[selectedLayer][selectedY][selectedX] != selectedTile){
					LevelChange c = new LevelChange(level.map[selectedLayer][selectedY][selectedX], selectedTile, selectedLayer, selectedX, selectedY, this);
					level.map[selectedLayer][selectedY][selectedX] = selectedTile;
				}
				//TODO if you click outside a shift selected area, it sets shiftSelected to false
				
				if (!shiftSelected) {
					lastClickX = selectedX;
					lastClickY = selectedY;
				}
				
				
			}
			else if (p.mouseButton == PConstants.RIGHT) {
				selectedTile = level.map[selectedLayer][selectedY][selectedX];
				shiftSelected = false;
			}
			
		}
	}
	
	public void keyPress(int keycode) {
		
		//arrow keys
		if (cameraControl) {
			if (keycode == Keys.LEFT) {
				if (cameraX > cameraXmin) {
					cameraX -= level.tileSize;
				}
			}
			if (keycode == Keys.UP) {
				if (cameraY > cameraYmin) {
					cameraY -= level.tileSize;
				}
			}
			if (keycode == Keys.RIGHT) {
				if (cameraX < cameraXmax) {
					cameraX += level.tileSize;
				}
			}
			if (keycode == Keys.DOWN) {
				if (cameraX < cameraYmax) {
					cameraY += level.tileSize;
				}
			}
		}
		//TODO: (int)'z'
		// control + z
		if (keycode == 90) {
			if ((p.keysDown[Keys.CONTROL]) && (changes.size() > 0)){
				//System.out.println("ctrl + z pressed");
				changes.get(changes.size() - 1).undo();
			}
		}
		//control + c
		if (keycode == 67) {
			if ((p.keysDown[Keys.CONTROL]) && (shiftSelected)) {
				//System.out.println("ctrl + c pressed");
				controlCArray = shiftTiles.clone();
				controlC = true;
			}
		}
		//control + x
		if (keycode == 88) {
			if ((p.keysDown[Keys.CONTROL]) && (shiftSelected)) {
				//System.out.println("ctrl + x pressed");
				controlCArray = shiftTiles.clone();
				//TODO: shiftX and shiftY are not correct
				this.setArea(shiftLayer, shiftX, shiftY, shiftTiles[0].length, shiftTiles.length, 0);
				controlC = true;
				
			}
		}
		//control + v
		if (keycode == 86) {
			if ((p.keysDown[Keys.CONTROL]) && (controlC)) {
				//System.out.println("ctrl + v pressed");
				this.areaCopy(controlCArray);
			}
		}
		//control + s
		if (keycode == 83) {
			if (p.keysDown[Keys.CONTROL]) {
				//System.out.println("ctrl + s pressed");
				//TODO "are you sure?" dialog box
				//TODO: define writeLevel and Level objects should have a name
				level.writeLevel();
			}
		}
	}
	
	public void onScroll(float e) {
		
	}
	
	public void areaCopy(int[][] cpy) {
		
		AreaChange a = new AreaChange(cpy[0].length, cpy.length, selectedLayer, selectedX, selectedY, this);
		
		for (int y = 0; y < cpy.length; y++) {
			for (int x = 0; x < cpy[0].length; x++) {
				
				if ((selectedX + x < level.map[0][0].length) && (selectedY + y < level.map[0].length)) {
					a.prevTiles[y][x] = level.map[selectedLayer][selectedY + y][selectedX + x];
					//System.out.println(a.prevTiles[y][x]);
					level.map[selectedLayer][selectedY + y][selectedX + x] = cpy[y][x];
					//System.out.println(a.prevTiles[y][x]);
					a.changeTiles[y][x] = cpy[y][x];
				}
				
			}
		}
		
	}
	
	//TODO
	public void setArea(int l, int xstart, int ystart, int w, int h, int value) {
		//System.out.println("setArea Called\n");
		//System.out.printf("starting from %d,%d, w/h %d,%d\n", xstart, ystart, w, h);
		//System.out.printf("%d, %d\n", selectedX, selectedY);
		AreaChange a = new AreaChange(w, h, l, xstart, ystart, this);
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if ((x + xstart < level.map[0][0].length) && (y + ystart < level.map[0].length))  {
					//System.out.println("changed a tile");
					a.prevTiles[y][x] = level.map[l][y + ystart][x + xstart];
					level.map[l][y + ystart][x + xstart] = value;
					a.changeTiles[y][x] = value;
					
				}
			}
		}
	}
	
	//returns the portion of the layer that is "on screen" based on where the camera is and the size of the viewer
	public int[][] getOnscreenTiles (int[][] layer) {
		
		//TODO: cast xSize and ySize to int before division?
		int outWidth = (int)(xSize/level.tileSize + 1);
		int outHeight = (int)(ySize/level.tileSize + 1);
		
		int camTileX = (int)(cameraX/level.tileSize) - 1;
		int camTileY = (int)(cameraY/level.tileSize) - 1;
		
		int[][] out = new int[outHeight][outWidth];
		
		/*
			System.out.println(cameraTileX);
			System.out.println(cameraTileY);
			
			System.out.println(outHeight);
			System.out.println(outWidth);
		*/
		
		for (int x = camTileX; x < camTileX + outWidth; x++) {
			for (int y = camTileY; y < camTileY + outHeight; y++) {
				if ((x >= 0) && (y >= 0) && (x < layer[0].length) && (y < layer.length)) {
					out[y - camTileY][x - camTileX] = layer[y][x];
					
				}
				else {
					out[y - camTileY][x - camTileX] = 0;
				}
				
			}
		}
		return out;
	}
	
}