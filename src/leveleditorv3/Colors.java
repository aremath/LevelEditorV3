package leveleditorv3;

import processing.core.*;

public class Colors {
	//RGB
	
	static PApplet p;
	
	
	public static int alphaOnly; //= p.color(0,0,0,0);
	public static int backgroundColor; //= p.color(200, 200, 200, 255);
	public static int selectColor; //= p.color(255, 0, 0, 255);
	
	public static int menuBorderColor; //= p.color(29,79,131,255);
	public static int baseMenuColor; //= p.color(49, 99, 151, 255);
	public static int mouseMenuColor; //= p.color(66, 129, 194, 255);
	public static int selectMenuColor; //= p.color(127, 169, 213, 255);
	public static int selectMouseMenuColor; //= p.color(172, 199, 228, 255);
	
	public static int black; //= p.color(0,0,0,255); //TODO which of black and alphaonly is a=255?
	
	//TODO: is it really necessary to initialize colors? I wanted this type to be just a static declaration of globals...
	public Colors(PApplet _p){
		p = _p;
		
		alphaOnly = p.color(0,0,0,0);
		backgroundColor = p.color(200, 200, 200, 255);
		selectColor = p.color(255, 0, 0, 255);
		
		menuBorderColor = p.color(29,79,131,255);
		baseMenuColor = p.color(49, 99, 151, 255);
		mouseMenuColor = p.color(66, 129, 194, 255);
		selectMenuColor = p.color(127, 169, 213, 255);
		selectMouseMenuColor = p.color(172, 199, 228, 255);
		
		black = p.color(0,0,0,255);
	}
	
	public static int getMenuColor(boolean isClicked, boolean mouseOver) {
		if ((isClicked) && (mouseOver)) {
			return selectMouseMenuColor;
		}
		else if (mouseOver) {
			return mouseMenuColor;
		}
		else if (isClicked) {
			return selectMenuColor;
		}
		else {
			return baseMenuColor;

		}
	}
}
