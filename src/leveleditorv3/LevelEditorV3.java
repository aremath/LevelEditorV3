package leveleditorv3;

import processing.core.PApplet;
import processing.event.MouseEvent;
import menus.*;

/*
//GENERAL TODOS:
//TODO: make sure classes that don't need processing.core don't import it
*/


public class LevelEditorV3 extends PApplet {
	
	public Colors c;
	
	public boolean[] keysDown;
	public Level currentLevel;
	public LevelViewer lvl;
	public Menu all;
	
	String levelname = "0.txt";
	String thisPath;
	
	String absolutePath;
	
	public void settings() {
		size(640, 320);
	}
	
	public void setup() {
		keysDown = new boolean[Defaults.MAX_KEYS];
		
		c = new Colors(this);
		//thisPath = sketchPath("");
		thisPath = System.getProperty("user.dir");
		System.out.println(thisPath);
		Images.loadImages(thisPath, this);
		
		frameRate(60);
		
		colorMode(HSB); //TODO: apparently does nothing T_T
		
		all = new Menu(0,0,640,320, this, false, 0);
		
		currentLevel = new Level(thisPath, "0.txt", this);
		
		currentLevel.readLevel();
		
		//menu constructors are called as:
		//x,y
		//w,h
		//Menu, PApplet, other arguments
		
		Menu tileButtons = new ScrollableMenu(Defaults.DEFAULT_SEP, Defaults.DEFAULT_SEP,
											Defaults.TILE_MENU_WIDTH, Defaults.TILE_MENU_HEIGHT * 6,
											all, this, 
											Defaults.TILE_MENU_WIDTH, (Defaults.TILE_MENU_HEIGHT - Defaults.DEFAULT_SEP) * Defaults.NTILES, false, 0);
		
		//System.out.println(TILE_MENU_HEIGHT * NTILES);
		
		Menu tiles = new Menu(tileButtons.xpos + tileButtons.xSize + Defaults.DEFAULT_SEP, Defaults.DEFAULT_SEP,
								currentLevel.tileSize * Defaults.TILE_VIEWER_WIDTH, currentLevel.tileSize * Defaults.TILE_VIEWER_HEIGHT,
								all, this, false, 0);
		
		lvl = new LevelViewer(Defaults.DEFAULT_SEP, Defaults.DEFAULT_SEP,
							tiles.xSize - Defaults.DEFAULT_SEP, tiles.ySize - Defaults.DEFAULT_SEP,
							tiles, this, currentLevel);
		
		//make a tile button for each tile
		for (int tile = 0; tile < Defaults.NTILES; tile ++){
			
			TileButton t = new TileButton(tileButtons.xpos, tileButtons.ypos + tile * (Defaults.TILE_MENU_HEIGHT - Defaults.DEFAULT_SEP),
										Defaults.TILE_MENU_WIDTH, Defaults.TILE_MENU_HEIGHT,
										tileButtons, this, tile, lvl, Defaults.DEFAULT_SEP);
		}
		
		Menu layerButtons = new Menu(tiles.xpos + tiles.xSize + Defaults.DEFAULT_SEP, Defaults.DEFAULT_SEP,
									2 * Defaults.LAYER_BUTTON_SIZE, currentLevel.map.length * Defaults.LAYER_BUTTON_SIZE,
									all, this, true, 0);
		
		for (int layer = 0; layer < currentLevel.map.length; layer ++) {
			
			LayerSelectButton ls = new LayerSelectButton(layerButtons.xpos, layerButtons.ypos + Defaults.LAYER_BUTTON_SIZE * layer,
														Defaults.LAYER_BUTTON_SIZE, Defaults.LAYER_BUTTON_SIZE,
														layerButtons, this, layer, lvl);
			
			LayerViewButton lv = new LayerViewButton(layerButtons.xpos + Defaults.LAYER_BUTTON_SIZE, layerButtons.ypos + Defaults.LAYER_BUTTON_SIZE * layer,
													Defaults.LAYER_BUTTON_SIZE, Defaults.LAYER_BUTTON_SIZE,
													layerButtons, this, layer, lvl);
		}
	}

	public void draw() {
		background(Colors.backgroundColor);
		
		//TODO: move this to within the LevelViewer drawM function?
		lvl.cameraControl = lvl.mouseOver;
		
		all.drawM();
		
		//TODO: this might not work so well
		if ((all.mouseOver) && (mousePressed)) {
				all.continousClick();
		}
	}
	
	public void keyPressed() {
		//TODO only propogate key presses to the current active layer
		all.keyPress(keyCode);
		keysDown[keyCode] = true;
	}

	public void keyReleased() {
		keysDown[keyCode] = false;
		
	}


	//TODO: during draw if mousePressed or something so you can click and drag (?) does the continuousClick function solve this?
	public void mousePressed() {
		//TODO: a click should have a single menuItem as the endpoint
		if (all.mouseOver){
			all.onClick();
		}
	}

	//TODO: scroll should also only have a single endpoint
	public void mouseWheel(MouseEvent event) {
		float e = event.getCount();
		all.onScroll(e);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { leveleditorv3.LevelEditorV3.class.getName() });
	}
}
