package menus;

public class IntegerInputBox extends menuItem {
	int value;
	int maxDigits;
	int base;
	
	//TODO: constructor
	
	public void drawM() {
		//draw the box
		
		//put the text
	}
	
	public void onClick() {
		isClicked = true;
	}
	
	public void continousClick() {
		
	}
	
	public void keyPress(int keycode) {
		if ((keycode > 47) && (keycode < 58)){
			if (isClicked) {
				int keyInt = keycode - 48;
				//TODO: if value * base < maxDigits ^ base
				if (value < maxDigits){
					value *= base;
					value += keyInt;
					
				}
			}
		}
	}
	
	public void onScroll(float e) {
		
	}
	
}
