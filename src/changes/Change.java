package changes;

import menus.LevelViewer;

public class Change {
	
	LevelViewer parent;
	
	public Change(LevelViewer p){
		parent = p;
		parent.changes.add(this);
	}
	
	public void undo(){
		parent.changes.remove(this);
	}
	
}