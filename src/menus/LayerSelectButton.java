package menus;

import processing.core.*;
import leveleditorv3.*;

public class LayerSelectButton extends Button {
	int layer;
	LevelViewer viewer;
	
	public LayerSelectButton (float x, float y, float w, float h, Menu parent, LevelEditorV3 _p, int l, LevelViewer v){
		super(x, y, w, h, parent, _p);
		layer = l;
		text = Integer.toString(l);
		viewer = v;
	}
	
	public void drawM() {
		if (viewer.selectedLayer == layer) {
			isClicked = true;
			viewer.visibleLayers[layer] = true;
		}
		else {
			isClicked = false;
		}
		super.drawM();
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
			viewer.selectedLayer = layer;
		}
		
	}
	
}

