package mazeGame;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


public class Enemy extends Entity {

	boolean dead = false;
	
	int x;
	int y;

	//movement
	int yChange = 0;
	int xChange = 0;

	int stepsToTake = 0;
	ArrayList<Cell> path;

	//size of level - used as correct bound
	int levelSize;

	//sprite make buffered image
	Image sprite;

	private BufferedImage[] downSprites;
	private BufferedImage[] leftSprites;
	private BufferedImage[] rightSprites;
	private BufferedImage[] upSprites;
	
	private final int DOWN = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	
	/*
	 * This value is used to restrict the enemy from moving 
	 * ensures an enemy is only allowed to move every 10th 'tick'
	 * if 10 -> move and decrease moving, if >10 -> don't move and decrease, if 0 -> do nothing and reset to 10
	 * Terribly sloppy implementation but quick :P (n pretty clever (rip a neater implementation tho))
	 * I will change this if I get time but did just want to implement something to slow em down
	 * If you are reading this then I ran outta time lol
	 */
	int moving = 9;

	public Enemy(int x, int y, int levelSize) throws IOException {
		this.levelSize = levelSize;

		//sprite = loadSprite();
		
		downSprites = SpriteBank.enemy[0];
		leftSprites = SpriteBank.enemy[1];
		rightSprites = SpriteBank.enemy[2];
		upSprites = SpriteBank.enemy[3];

		this.x = x;
		this.y = y;		

		animation.setFrames(upSprites);
		animation.setDelay(10);
	}	

	
	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(d);
	}
	
	
	//movement
	public void move(ArrayList<ArrayList<Cell>> grid) {

		if (stepsToTake <= 0 || path.size() <= 0) {

			path = getDirection(grid);
			moving = 9;

			getSteps(grid);
			
		} else {

			moveEnemy(grid);
		}
	}

	
	//sets the steps to take to a random number within the size of path
	private void getSteps(ArrayList<ArrayList<Cell>> grid) {
		int limit = path.size();
		//System.out.println("Path size: " + path.size());

		//if path size somehow zero
		if (limit <= 0) {
			
			//does not move, changes direction
			path = getDirection(grid);
			moving = 9;
			System.out.println("ERROR");

		}
		else if (limit == 1) {
			this.x = path.get(0).xPos;
			this.y = path.get(0).yPos;

			path = getDirection(grid);
			moving = 9;
			//System.out.println("Limit is 1");
		}
		else if (stepsToTake <= 0) {

			Random rand = new Random();
			stepsToTake = rand.nextInt(limit);
		}
	}

	private void moveEnemy(ArrayList<ArrayList<Cell>> grid) {
		//uses the movement variable to decide if enemy should move

		//move when 10
		if (moving == 10) {
			//dec stepstotake
			stepsToTake -= 1;
			//move 1 cell
			this.x = path.get(0).xPos;
			this.y = path.get(0).yPos;

			//System.out.println("Moved to: x-" + xPos/30 + " y-"+ yPos/30);

			//remove cell
			path.remove(0);
			//dec moving var
			moving--;
		}
		//resets moving var so that var loops
		else if (moving == 0) {
			moving = 10;
		}
		//if not at min val, dec moving var
		else if (moving < 10) {
			moving--;
		}

	}

	private ArrayList<Cell> getDirection(ArrayList<ArrayList<Cell>> grid) {
		//System.out.println("Get Direction Called");

		ArrayList<Cell> path = new ArrayList<>();
		ArrayList<Cell> validNeighbours = new ArrayList<>();

		Cell right = null;
		Cell left = null;
		Cell up = null;
		Cell down = null;

		Cell dir = null;
		String direction = "none";

		Random rand = new Random();

		//get e pos in grid
		int col = this.x / Settings.cellHeight;
		int row = this.y / Settings.cellWidth;

		//System.out.println("TESTING col: " + col + " row: "+ row);

		//get e neighbours				
		if (col <= levelSize-2) {
			right = grid.get(col+1).get(row);
			if (right.isBlocked() == false) {
				//System.out.println("right blocked");
				validNeighbours.add(right);
			}
		}

		if (col >= 2) {	
			left = grid.get(col-1).get(row);
			if (left.isBlocked() == false) {
				validNeighbours.add(left);
				//System.out.println("left blocked");
			}
		}

		if (row >= 2) {
			up = grid.get(col).get(row-1);
			if (up.isBlocked() == false) {
				validNeighbours.add(up);
				//System.out.println("up blocked");
			}
		}

		if (row <= levelSize-2) {
			down = grid.get(col).get(row+1);
			if (down.isBlocked() == false && down.isEntry() == false) {
				validNeighbours.add(down);
				//System.out.println("down blocked");
			}
		}


		if (validNeighbours.size() > 0) {
			//choose one randomly
			dir = validNeighbours.get(rand.nextInt(validNeighbours.size()));

		}
		//System.out.println("TESTING size of validNeighbours arraylist: " + validNeighbours.size());
		//System.out.println("TESTING current position: x " + xPos/30 + " y "+ yPos/30);

		int newCol = dir.getxPos()/30;
		int newRow = dir.getyPos()/30;

		//calculate direction
		//set direction
		//same col 
		if (newCol == col) {
			//moving down
			if (newRow > row) {

				this.xChange = 0;				
				this.yChange = 1;
				direction = "down";
			}
			//movign up
			if (newRow < row) {
				this.xChange = 0;				
				this.yChange = -1;
				direction = "up";
			}
			if (dir.yPos == row) {
				System.out.println("Error in direction");
			}
		} 
		//same row
		else if (newRow == row) {
			//moving right
			if (newCol > col) {

				this.xChange = 1;				
				this.yChange = 0;
				direction = "right";
			}
			//moving left
			if (newCol < col) {

				this.xChange = -1;				
				this.yChange = 0;
				direction = "left";
			}
			if (dir.xPos == col) {
				System.out.println("Error in direction");
			}
		}

		//for that direction, get all unblocked cells within the straight pathway			
		//put all path way cells into a list / stop at first blocked cell
		boolean notBlocked = true;
		int gridCol = this.x / 30;
		int gridRow = this.y / 30;

		//System.out.println("Dir: col-" + dir.yPos + " row-" + dir.xPos);

		//System.out.println("Direction to go: " + direction);
		//System.out.println("Current pos: col-" + gridCol + " row-" + gridRow);

		if (gridRow > 0 && gridCol > 0 && gridRow < levelSize && gridCol < levelSize) {

			while(notBlocked) {
				//update grid nav pos based on direction

				if (direction == "down") {
					if (gridRow + 1 < levelSize) {
						gridRow += 1;
					}
					else {
						notBlocked = false;
					}
					
					setAnimation(DOWN, downSprites, 10);
				} 
				else if (direction == "up") {
					if (gridRow - 1 > 0) {
						gridRow -= 1;
					}
					else {
						notBlocked = false;
					}
					
					setAnimation(UP, upSprites, 10);
				}
				else if (direction == "left") {
					if (gridCol - 1 > 0) {
						gridCol -= 1;
					}
					else {
						notBlocked = false;
					}
					
					setAnimation(LEFT, leftSprites, 10);
				}
				else if (direction == "right") {
					if (gridCol + 1 < levelSize) {
						gridCol += 1;
					}
					else {
						notBlocked = false;
					}
					
					setAnimation(RIGHT, rightSprites, 10);
				}

				//System.out.println("Next cell pos: " + gridCol + " " + gridRow);

				Cell nextCell = grid.get(gridCol).get(gridRow);

				if (nextCell.isBlocked()) {
					notBlocked = false;
					//System.out.println("Direction finished - blocked found");					
				}
				else {
					path.add(nextCell);
				}
			}		
		}

		//System.out.println("Final path length: " + path.size());
		//Return cell list
		return path;

	}
	
	//bounds
	public Rectangle getRectangle() {
		return new Rectangle(x, y, Settings.cellWidth, Settings.cellHeight);
	}


	//placholder image
	//loads in sprite of image
	private Image loadSprite() throws IOException {

		//load body segment
		Image enemyImage = ImageIO.read(new File("resources//sprites//glow.png"));
		//scales to fit defined dimensions
		Image scaledenemyImage = enemyImage.getScaledInstance(Settings.cellWidth, Settings.cellHeight, Image.SCALE_DEFAULT);

		return scaledenemyImage;			
	}
}
