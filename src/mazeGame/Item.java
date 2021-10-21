package mazeGame;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item extends Entity {
	
	//xpos and ypos
	int x;
	int y;
	
	//collected state
	private boolean collected = false;
	
	//sprite
	Image sprite;
	
	BufferedImage[][] gem;
	
	public Item(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		
		sprite = loadSprite();
		gem = SpriteBank.items;
	}
	
	//placholder image / placeholder function
	//loads in sprite of image
	private Image loadSprite() throws IOException {

		//load body segment
		Image ItemImage = ImageIO.read(new File("resources//sprites//bol.png"));
		//scales to fit defined dimensions
		Image scaledItemImage = ItemImage.getScaledInstance(Settings.cellWidth, Settings.cellHeight, Image.SCALE_DEFAULT);

		return scaledItemImage;			
	}
	
	//bounds
		public Rectangle getRectangle() {
			return new Rectangle(x, y, Settings.cellWidth, Settings.cellHeight);
		}


	//getters and setters
	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}
	
	
}
