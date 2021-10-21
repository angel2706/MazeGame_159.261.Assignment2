package mazeGame;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;



public class SpriteBank {
	
	public static BufferedImage[][] floor = load("resources\\textures\\tex.png", 32, 32);
	public static BufferedImage[][] wall;
	public static BufferedImage[][] exit;
	
	//public static BufferedImage[][] entities = load("resources\\sprites\\entities.png", 16, 16) ;

	public static BufferedImage[][] enemy = load("resources\\sprites\\en.png", 32, 32);
	public static BufferedImage[][] player = load("resources\\sprites\\player.png", 32, 32);
	
	
	//UNNECESSARY ARRAYS - REFACTOR INTO BUFFEREDIMAGES
	public static BufferedImage[][] items = load("resources\\sprites\\gems.png", 32, 32);
	
	public static BufferedImage[][] selector = loadSpriteImage("resources\\sprites\\selector.png", 30, 30);
	
	public static BufferedImage[][] bg = loadSpriteImage("resources\\textures\\titlebg.png", 1220, 930);
	public static BufferedImage[][] fillbg = loadSpriteImage("resources\\textures\\fillbg.png", 930, 930);
	public static BufferedImage[][] hudbg = loadSpriteImage("resources\\textures\\bg.png", 930, 930);
	
	public static BufferedImage[][] loadSpriteImage(String path, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(new File(path));
			
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			
			ret = new BufferedImage[height][width];
			
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		
		return null;
	}	
	
	
	public static BufferedImage[][] load(String path, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(new File(path));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
}
