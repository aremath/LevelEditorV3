package menus;

import leveleditorv3.*;
import processing.core.*;

public class LayerViewButton extends Button {
	
	int layer;
	LevelViewer viewer;
	
	public LayerViewButton (float x, float y, float w, float h, Menu parent, LevelEditorV3 _p, int l, LevelViewer v){
		super(x, y, w, h, parent, _p);
		layer = l;
		text = Integer.toString(l);
		viewer = v;
	}
	
	public void drawM() {
		if (viewer.visibleLayers[layer]) {
			isClicked = true;
		}
		else {
			isClicked = false;
		}
		super.drawM();
		if (isClicked) {
			p.image(Images.openeye, xpos + ((xSize - Images.closedeye.width) / 2), ypos + ((ySize - Images.closedeye.height) / 2));
		}
		else {
			p.image(Images.closedeye, xpos + ((xSize - Images.closedeye.width) / 2), ypos + ((ySize - Images.closedeye.height) / 2));
		}
	}
	
	public void onClick() {
		if (isClicked) {
			isClicked = false;
		}
		else {
			isClicked = true;
		}
		
		if (layer < viewer.nLayers){
			viewer.visibleLayers[layer] = isClicked;	
		}
		
	}
	
}
