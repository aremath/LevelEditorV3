package menus;

import processing.core.*;
import leveleditorv3.*;

public abstract class menuItem {
	public float xpos;
	public float ypos;
	public float xSize;
	public float ySize;
	
	public int layer;
	
	public boolean isClicked;
	public boolean isSelected;
	public boolean mouseOver;
	
	LevelEditorV3 p;
	
	public abstract void drawM();
	
	public abstract void onClick();
	
	public abstract void continousClick();
	
	public abstract void keyPress(int keycode);
	
	public abstract void onScroll(float e);
	
	public float[] relMousePos() {
		float[] out = new float[2];
		if ((p.mouseX < this.xpos) || (p.mouseX > this.xpos + this.xSize)) {
			out[0] = -1;
			this.mouseOver = false;
		}
		else {
			out[0] = p.mouseX - this.xpos;
		}
		
		if ((p.mouseY < this.ypos) || (p.mouseY > this.ypos + this.ySize)) {
			out[1] = -1;
			this.mouseOver = false;
		}
		else {
			out[1] = p.mouseY - this.ypos;
		}
		
		if ((out[0] >= 0) && (out[1] >= 0)) {
			this.mouseOver = true;
		}
		
		return out;
	}
	
	
}
