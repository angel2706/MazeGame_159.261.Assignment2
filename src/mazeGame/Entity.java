package mazeGame;

import java.awt.Image;
import java.awt.Rectangle;


public class Entity {
	
	int x;
	int y;
	
	Image sprite;
	
	Animation animation;
	int currentAnimation;
	
	public Entity() {
		animation = new Animation();
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, Settings.cellWidth, Settings.cellHeight);
		
	}
	
}
