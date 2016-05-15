package menus;

import processing.core.*;
import leveleditorv3.*;

public class Button extends menuItem {
	
	String text;
	
	public Button(float x, float y, float w, float h, Menu parent, LevelEditorV3 _p){
		xpos = x;
		ypos = y;
		xSize = w;
		ySize = h;
		parent.items.add(this);
		p = _p;
	}
	
	public Button(float x, float y, float w, float h, Menu parent, LevelEditorV3 _p, String s){
		xpos = x;
		ypos = y;
		xSize = w;
		ySize = h;
		parent.items.add(this);
		
		text = s;
		
		p = _p;
	}
	
	public void onClick() {
		isClicked = true;
	}
	
	public void drawM() {
		float[] pos = relMousePos();
		int c = Colors.getMenuColor(isClicked, mouseOver);
		p.stroke(Colors.menuBorderColor);
		p.fill(c);
		p.rect(xpos, ypos, xSize, ySize);
		p.fill(Colors.black);
		p.text(text, xpos + 1, ypos, xSize, ySize);
	}
	
	public void continousClick() {
		
	}
	
	public void keyPress(int keycode) {
		
	}
	
	public void onScroll(float e) {
		
	}
}
