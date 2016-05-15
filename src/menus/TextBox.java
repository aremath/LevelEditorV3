package menus;

import processing.core.*;
import leveleditorv3.*;

public class TextBox extends menuItem {
	
	String text;
	int size;
	
	public TextBox(float x, float y, float w, float h, String s, int tsize, Menu parent, LevelEditorV3 _p){
		xpos = x;
		ypos = y;
		xSize = w;
		ySize = h;
		
		size = tsize;
		text = s;
		parent.items.add(this);
		p = _p;
	}
	
	public void drawM() {
		p.fill(Colors.black);
		p.textSize(size);
		p.text(text, xpos, ypos, xSize, ySize);
	}
	
	public void onClick() {
		isClicked = true;
	}
	
	public void continousClick() {
		
	}
	
	public void keyPress(int keycode) {
		
	}
	
	public void onScroll(float e) {
		
	}
	
}
