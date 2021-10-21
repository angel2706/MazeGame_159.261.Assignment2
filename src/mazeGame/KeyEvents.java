package mazeGame;

import java.awt.event.KeyEvent;

public class KeyEvents {

	public static KeyEvent toComplete = null;
	private static KeyEvent completed = null;
	
	public static void setKeyEvent(KeyEvent e) {
		
		toComplete = e;
	}

	public static boolean isKeyEvent() {

		return toComplete != null;
	}
	
	public static KeyEvent getKeyEvent() {
		
		completed = toComplete;
		toComplete = null;
		
		//System.out.println("completed: " + completed==null);
		
		return completed;
	}
}
