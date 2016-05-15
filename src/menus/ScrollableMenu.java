package menus;

import java.util.ArrayList;
import leveleditorv3.*;
import processing.core.*;

public class ScrollableMenu extends Menu {
	//scrollBar yScroller;
	//scrollBar xScroller?
	float maxXSize;
	float maxYSize;
	
	float yOffset;
	float newYOffset;
	
	public ScrollableMenu(float x, float  y, float  w, float  h, LevelEditorV3 _p, float maxX, float maxY, boolean bg, int l) {
		super(x, y, w, h, _p, bg, l);
		maxXSize = maxX;
		maxYSize = maxY;
		//menuList.add(this); //TODO: no menuList, just a big parent menu that everything gets added to
		items = new ArrayList<menuItem>();
		//this.yScroller = new scrollBar(this);
		scrollable = true;
		yOffset = 0;
		newYOffset = 0;
	}
	
	public ScrollableMenu(float x, float  y, float  w, float  h, Menu parent, LevelEditorV3 _p, float maxX, float maxY, boolean bg, int l) {
		super(x, y, w, h, parent, _p, bg, l);
		maxXSize = maxX;
		maxYSize = maxY;
		
		items = new ArrayList<menuItem>();
		//this.yScroller = new scrollBar(this);
		scrollable = true;
		yOffset = 0;
		newYOffset = 0;
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
		
		menuItem currentItem;
		for (int i = 0; i < items.size(); i++) {
			//TODO: instead of actually changing the position, it would be more stable to be able to display them differently
			//TODO: better way to display the objects so that they don't disappear and leave empty spots
			currentItem = items.get(i);
			
			currentItem.ypos -= newYOffset;
			if (currentItem.ypos + currentItem.ySize > ySize) {
				
			}
			else if (currentItem.ypos < ypos) {
				
			}
			else {
				currentItem.drawM();
			}
		}
		yOffset += newYOffset;
		newYOffset = 0;
	}
	
	public void onScroll(float scrollAmount) {
		//TODO: as global int scrollspeed?
		newYOffset += 10 * scrollAmount;
		
		//TODO: or scroll velocity then have friction and stuff
		//TODO: this is awful
		//TODO: fix the thing where it shifts when you get to the bottom or the top.
		//TODO: offset per ITEM, then scroll on item per thing
		
		//if the scroll would put the scrollbar above the top	
		if (yOffset + newYOffset < 0) {
			newYOffset = yOffset;
			//System.out.println("can't scroll past the top");
		}
		//if it would put the scrollbar below the bottom
		else if (yOffset + newYOffset + ySize > maxYSize) {
			//this.newYOffset = this.maxYSize - this.ySize - this.yOffset;
			newYOffset = 0;
			//System.out.println("can't scroll past the bottom");
		}
		
		//System.out.println(this.newYOffset);
		//System.out.println(this.newYOffset);
	}
	
	/*
		public void continousClick() {
		for (int i = 0; i < this.items.size(); i++){
		if (this.items.get(i).mouseOver){
		this.items.get(i).continousClick();	
		}	
		}
		}
		
		/*
		public void keyPress(int keycode) {
		for (int i = this.items.size() - 1; i >= 0; i--){
		this.items.get(i).keyPress(keycode);
		//keycode should only propogate to ONE object
		break;
		}
		}
	*/
	
}
