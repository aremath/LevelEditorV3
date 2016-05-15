package menus;

import java.util.ArrayList;
import leveleditorv3.*;
import processing.core.*;

public class Menu extends menuItem {
	
	//public Menu parent;
	
	boolean visibleBG;
	boolean scrollable;
	
	ArrayList<menuItem> items;
	
	public Menu(float x, float  y, float  w, float  h, LevelEditorV3 _p, boolean bg, int l) {
		xpos = x;
		ypos = y;
		xSize = w;
		ySize = h;
		//menuList.add(this); //TODO: Menu all; which is the parent for all menus?
		items = new ArrayList<menuItem>();
		visibleBG = bg;
		scrollable = false;
		layer = l;
		p = _p;
	}
	
	public Menu(float x, float  y, float  w, float  h, Menu parent, LevelEditorV3 _p, boolean bg, int l) {
		xpos = x;
		ypos = y;
		xSize = w;
		ySize = h;
		parent.items.add(this);
		
		items = new ArrayList<menuItem>();
		visibleBG = bg;
		scrollable = false;
		layer = l;
		p = _p;
	}
	
	public void drawM() {
		
		float[] q = relMousePos();
		
		int c = Colors.getMenuColor(isClicked, mouseOver);
		p.stroke(c);
		
		if (visibleBG){
			p.fill(c);
		}
		else {
			p.noFill();
		}
		
		p.rect(xpos, ypos, xSize, ySize);
		
		
		for (int i = 0; i < items.size(); i++) {
			items.get(i).drawM();	
		}
	}
	
	public void onClick() {
		for (int i = 0; i < items.size(); i++){
			//this.items.get(i).isSelected = true;
			//others.isSelected = false;
			
			if (items.get(i).mouseOver){
				items.get(i).onClick();
				
			}
			
		}
		
	}
	
	public void continousClick() {
		for (int i = 0; i < items.size(); i++){
			if (items.get(i).mouseOver){
				items.get(i).continousClick();
			}	
		}
	}
	
	public void keyPress(int keycode) {
		for (int i = items.size() - 1; i >= 0; i--){
			items.get(i).keyPress(keycode);
			//key press only propogates to ONE object
			//TODO: key press propogates to ALL active objects (?)
			//break;
		}
	}
	
	public void onScroll(float e) {
		for (int i = items.size() - 1; i >= 0; i--){
			items.get(i).onScroll(e);
			//TODO: propogate the scroll to all menus associated with this menu?
		}
	}
	
}