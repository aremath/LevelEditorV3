package changes;

import menus.LevelViewer;

public class SelectChange extends Change {
	
	public SelectChange(LevelViewer p) {
		super(p);
	}
	
	public void undo() {
		parent.shiftSelected = false;
		super.undo();
	}
	
}
