package mazeGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Player extends Entity {
	
	ArrayList<ArrayList<Cell>> grid;
	ArrayList<Enemy> enemies;
	
	
	//x and y pos
	int xPos;
	int yPos;
	
	//movement
	int yChange;
	int xChange;
	
		
	//sprites
	Image sprite;
	private BufferedImage[] downSprites;
	private BufferedImage[] leftSprites;
	private BufferedImage[] rightSprites;
	private BufferedImage[] upSprites;
	
	// animation
	int facing = 3;
	private final int DOWN = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
		
	//inside exit
	boolean foundExit = false;
		
	//Projectiles
	ArrayList<Projectile> projectiles = new ArrayList<>();
	int numProjectilesAllowed = 2;
	
	
	public Player(int startX, int startY, ArrayList<ArrayList<Cell>> grid, ArrayList<Enemy> enemies) {
		
		//get sprites
		downSprites = SpriteBank.player[0];
		leftSprites = SpriteBank.player[1];
		rightSprites = SpriteBank.player[2];
		upSprites = SpriteBank.player[3];
		
		this.xPos = startX;
		this.yPos = startY;
		this.grid = grid;
		this.enemies = enemies;
		
		animation.setFrames(upSprites);
		animation.setDelay(10);
		
//		try {
//			
//			this.sprite = loadPlayerImage();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(d);
	}
	
	//load player image
	//this is a stand in image / stand in function
	private Image loadPlayerImage() throws IOException {

		//load body segment
		Image playerImage = ImageIO.read(new File("bloob.png"));
		//scales to fit defined dimensions
		Image scaledplayerImage = playerImage.getScaledInstance(Settings.cellWidth, Settings.cellHeight, Image.SCALE_DEFAULT);

		return scaledplayerImage;			
	}
	
	
	public void paint(Graphics g) throws IOException { 
		
		g.drawImage(this.animation.getImage(), this.xPos, this.yPos, null);
		
		if (projectiles.size() > 0) {
			for (Projectile p : projectiles) {
				p.paint((Graphics2D) g);
			}
		}
	}
	
	
	public void update() {
		
		int remove = 0;
		
		if (projectiles.size() > 0) {
			
			//will at max only have 2 projs to loop through
			for (int i = 0 ; i < projectiles.size(); i++) {
				
				Projectile p = projectiles.get(i);
				
				for (Enemy e : enemies) {
					if (p.intersects(e, grid)) {
						e.dead = true;
					}
				}
				
				
				if (p.crashed) {
					remove += 1 + i;
				} 
				else {
					p.move();
				}				
			}
		}
		
		if (remove == 3) {
			projectiles.clear();
		}
		else if (remove == 2) {
			projectiles.remove(1);
		}
		else if (remove == 1) {
			projectiles.remove(0);
		}
		
		
		move();
		
		
	}
	
	//move
	private void move() {
		//checks if can move		
		//checks for blocked
		//if can, update pos
		
		//REFACTOR MOVEMENT TO INCLUDE FLUID ANIMATIONS
		if (this.xChange == -1 && (checkNewCell())) {
			this.xPos -= 30;
			this.xChange = 0;
		}
		//change to maze size - one block
		else if (this.xChange == 1 && (checkNewCell())) {
			
			this.xPos += 30;
			this.xChange = 0;
		}
		
		if (this.yChange == -1 && (checkNewCell())) {
			this.yPos -= 30;
			this.yChange = 0;
		}
		//change to maze size - one block
		else if (this.yChange == 1 && (checkNewCell())) {
			this.yPos += 30;
			this.yChange = 0;
		}
	}
	
	private boolean checkNewCell() {
		
		//get play pos in grid		
		//using direction,
		//get new cell to move into
		int col = (xPos/30);
		int row = (yPos/30);
		
		//System.out.println("Player pos: " + xPos/30 + " " + yPos/30);
		//System.out.println("xChange: " + xChange);
		//System.out.println("yChange: " + yChange);
		//position check - not trying to walk into maze edge walls
			
		//moving down
		if (yChange == 1) {
			row += 1;			
		}
		//moving up
		else if (yChange == -1) {
			row -= 1;
		}
		//moving right
		else if (xChange == 1) {
			col += 1;
		}
		//moving left
		else if (xChange == -1) {
			col -= 1;
		}
		
		if (col >= grid.size() || row >= grid.size() || col < 0 || row < 0) {
			return false;
		}
		
		Cell newCell = grid.get(col).get(row);
		
		//check if new cell is exit
		//		found exit true
		//		return true unblocked
		if (newCell.isExit) {
			foundExit = true;
			System.out.println("EXIT FOUND");
			return true;
		}

		//return false if new cell is blocked	
		else if (newCell.isBlocked()) {
			return false;
		}
		//else return true
		return true;
	}
	
	
	//intersects - only for player since thats all we need
	public boolean intersects(Entity o) {
		return getRectangle().intersects(o.getRectangle());
	}
	
	//bounds
	public Rectangle getRectangle() {
		return new Rectangle(xPos, yPos, Settings.cellWidth, Settings.cellHeight);
	}
	
	
	public void keyPressed(KeyEvent e) {
		
		//System.out.println("Keypressed recieved");
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.xChange = -1;
			this.yChange = 0;
			facing = LEFT;
			setAnimation(LEFT, leftSprites, 10);
			}
			
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.xChange = 1;
			this.yChange = 0;
			facing = RIGHT;
			setAnimation(RIGHT, rightSprites, 10);
		}
				
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.xChange = 0;
			this.yChange = -1;
			facing = UP;
			setAnimation(UP, upSprites, 10);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.xChange = 0;
			this.yChange = 1;
			facing = DOWN;
			setAnimation(DOWN, downSprites, 10);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			//shoot
			//shoot();
			
			//System.out.println("Shoot called");
		}		
	}
	
	
	private void shoot() {
		
		//check player hasn't created max num proj
		if (checkProjectileAllowed()) {
			//System.out.println("Shoot accepted");
			Projectile proj = new Projectile(xPos, yPos, facing);
			projectiles.add(proj);
			
			System.out.println("Bullet movement: " + xChange + " " + yChange);
		}
		
	}
	
	private boolean checkProjectileAllowed() {
		
		if (projectiles.size() >= numProjectilesAllowed) {return false;}
		
		return true;
	}
	
	
}
