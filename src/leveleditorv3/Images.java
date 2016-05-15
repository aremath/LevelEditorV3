package leveleditorv3;

import processing.core.*;

public class Images {

	public static PImage openeye;
	public static PImage closedeye;
	
	public static PImage characterImage;
	
	public static PImage tilesetImage;
	
	static PApplet p;
	
	/*
	public Images (PApplet _p){
		p = _p;
	}
	*/
	
	//unsure if we need to actually load the image into a specific applet
	public static void loadImages(String path, PApplet _p){
		p = _p;
		
		openeye = p.loadImage(path + "\\src\\data\\images\\ui\\openeye.png");
		
		closedeye = p.loadImage(path + "\\src\\data\\images\\ui\\closedeye.png");
		
		characterImage = p.loadImage(path + "\\src\\data\\images\\text\\3x5Text.png");
		
		tilesetImage = p.loadImage(path + "\\src\\data\\images\\tilesets\\tileset1.png");
	}
	
	//Extract:
	// takes a sizey by sizex chunk of a PImage and returns it as a new PImage
	public static PImage extract (PImage source, int startx, int starty, int sizex, int sizey, PApplet _p) {
		boolean isEmpty = true;
		PImage out = _p.createImage(sizex, sizey, PConstants.ARGB);
		source.loadPixels();
		out.loadPixels();
		for (int x = 0; x < sizex; x++) {
			for (int y = 0; y < sizey; y++) {
				int loc = starty*source.width + startx + y*source.width + x;
				float h = _p.hue(source.pixels[loc]);
				float s = _p.saturation(source.pixels[loc]);
				float b = _p.brightness(source.pixels[loc]);
				float a = _p.alpha(source.pixels[loc]);
				
				//if there's a white pixel, treat it as an empty pixel
				if (b == 255) {
					a = 0;
				}
				//if there's a nonwhite pixel, the tile isn't empty
				else {
					isEmpty = false;
					
				}
				
				int outLoc = y*sizex + x;
				
				out.pixels[outLoc] = _p.color(h, s, b, a);
			}
		}
		if (!isEmpty){
			return out;
		}
		else {
			return null;
		}
	}
	
}
