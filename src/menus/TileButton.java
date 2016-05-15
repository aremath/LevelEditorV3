package menus;

import processing.core.*;
import leveleditorv3.*;

public class TileButton extends Button {
	
	int tileType;
	int imageSep; //TODO: defaults 10?
	LevelViewer viewer;
	
	public TileButton(float x, float y, float w, float h, Menu parent, LevelEditorV3 _p, int t, LevelViewer v, int is){
		super(x, y, w, h, parent, _p);
		tileType = t;
		viewer = v;
		imageSep = is;
	}
	
	public void drawM() {
		
		if (viewer.selectedTile == tileType){
			isClicked = true;
		}
		else {
			isClicked = false;
		}
		
		float pos[] = relMousePos();
		int c = Colors.getMenuColor(isClicked, mouseOver);
		p.stroke(Colors.menuBorderColor);
		p.fill(c);
		p.rect(xpos, ypos, xSize, ySize, 5, 5, 0, 0); //the last 4 numbers are curved corner radii
		
		p.image(viewer.level.tileset[tileType], xpos + (xSize - viewer.level.tileSize) / 2, ypos + (ySize - imageSep - viewer.level.tileSize) / 2);
		
	}
	
	public void onClick() {
		//this.isClicked = true;
		viewer.selectedTile = tileType;
		
	}
	
	public void continousClick() {
		
	}
	
	public void keyPress(int keycode) {
		
		
	}
}