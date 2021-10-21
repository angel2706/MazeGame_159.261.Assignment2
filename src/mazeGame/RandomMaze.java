package mazeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class RandomMaze {
	
	//maze dimensions
	//minimum functional maze size is 7
	int size;
	
	//grid that holds all passage and wall cells
	ArrayList<ArrayList<Cell>> grid = new ArrayList<>();
	
	public RandomMaze(int size) {
		
		//dealing with size??
		this.size = size;
		
		//initalizes make entirely as walls first
		for (int curX = 0; curX < this.size; curX++) {
			
			
			ArrayList<Cell> column = new ArrayList<>();
			
			for (int curY = 0; curY < this.size; curY++) {
				
				Cell cell = new Cell(Settings.cellWidth, Settings.cellHeight, (curX*Settings.cellWidth), (curY*Settings.cellHeight), true);
				column.add(cell);
			}
			
			grid.add(column);			
		}
		
		/* 
		 * Used psuedo code from
		 * https://stackoverflow.com/questions/29739751/implementing-a-randomly-generated-maze-using-prims-algorithm
		 */	
		generateMaze();
		generateEntry();
		generateExit();
		
	}
	
	private void generateMaze() {
		//Generate maze
		Random random = new Random();
		
		//pick a random cell excluding the mazes outer walls
		//Initial x and Y position must alway be an odd number
		//set to 3 by default, min maze size 5 
						
		//set its state to unblocked
		grid.get(3).get(3).setBlocked(false);
		
		ArrayList<Cell> initial = getFrontiers(3, 3);
		
		nextFrontier(initial, 3, 3);


	}
	
	
	private ArrayList<Cell> getFrontiers(int curX, int curY) {
				
		//compute its 'frontier' cells
		ArrayList<Cell> frontiers = new ArrayList<>();
		
		
		//frontier = neighbour with distance == 2 && neighbour state == blocked && within grid
		if ((curY - 2) > 0) {
			
			Cell north = grid.get(curX).get(curY - 2);
			
			if (north.isBlocked()) {
				north.setPathNeighbourX(curX);
				north.setPathNeighbourY(curY);
				frontiers.add(north);
			}
		}

		if ((curY + 2) < (this.size-1)) {
			
			Cell south = grid.get(curX).get(curY + 2);
			
			if (south.isBlocked()) {
				south.setPathNeighbourX(curX);
				south.setPathNeighbourY(curY);
				frontiers.add(south);
			}
		}

		if ((curX + 2) < (this.size-1)) {
			
			Cell east = grid.get(curX + 2).get(curY);
			
			if (east.isBlocked()) {
				east.setPathNeighbourX(curX);
				east.setPathNeighbourY(curY);
				frontiers.add(east);
			}
		}

		if ((curX - 2) > 0) {
			
			Cell west = grid.get(curX - 2).get(curY);
			
			if (west.isBlocked()) {
				west.setPathNeighbourX(curX);
				west.setPathNeighbourY(curY);
				frontiers.add(west);
			}
		}

		return frontiers;
	}
	
	
	
	private void nextFrontier(ArrayList<Cell> frontiers, int curX, int curY) {
		
		Random random = new Random();
		
		while (!frontiers.isEmpty()) {
			
			int next = random.nextInt(frontiers.size());

			Cell toChange = frontiers.get(next);

			int x = toChange.getxPos()/Settings.cellWidth;
			int y = toChange.getyPos()/Settings.cellHeight;
			
			grid.get(x).get(y).setBlocked(false);

			int passageX = toChange.getPathNeighbourX()*Settings.cellWidth;
			int passageY = toChange.getPathNeighbourY()*Settings.cellHeight;
			

			//connect frontier to pass
			//is col same
			if (toChange.getxPos() == passageX) {

				//is row greater or less
				if (toChange.getyPos() > passageY) {
					//path is north, y - 1
					grid.get(x).get(y-1).setBlocked(false);
				}
				else if (toChange.getyPos() < passageY) {
					//path is south, y + 1 
					grid.get(x).get(y+1).setBlocked(false);
				}
			}
			//is row same
			else if (toChange.getyPos() == passageY) {

				//is col greater or less
				if (toChange.getxPos() > passageX) {
					//path is west, x - 1
					grid.get(x-1).get(y).setBlocked(false);
				}
				else if (toChange.getxPos() < passageX) {
					//path is east, x + 1
					grid.get(x+1).get(y).setBlocked(false);
				}
			}	
			
			int index = frontiers.indexOf(toChange);
			frontiers.remove(index);

			frontiers.addAll(getFrontiers(x,y));
		}
	}
	
	private void generateEntry() {
		//entry is always generated on the 'bottom' row
		//within the center range
		//must be connected to a passage
		
		int min = (this.size / 2) - 1 ;
	
		
		boolean entryMade = false;
		
		//should at most loop 5 times - untested
		while (!entryMade) {
			
			if (grid.get(min).get(this.size-2).isBlocked() == false) {
				grid.get(min).get(this.size-1).setBlocked(false);
				grid.get(min).get(this.size-1).setEntry(true);
				
				entryMade = true;
				
				System.out.println(min);
			}
			
			min += 1;
		}
	}
	
	private void generateExit() {
		//exit must be connected to a passage
		//must not be where the entry is nor directly beside it
		//must not be in the corners
		
		//filter grid choices
		//random algorithm?
		
		Random random = new Random();
		
		ArrayList<Cell> validOptions = new ArrayList<>();
		
		boolean validExit = false;	
		
		//get first and last column
		//then get first and last cell of all other columns
		//filter out corners and entrance range
		
		ArrayList<Cell> left = new ArrayList<>(this.grid.get(0).size());
		left.addAll(this.grid.get(0));
		for (Cell lc : left) {
			if (lc.yPos != 0 && lc.yPos !=this.grid.get(0).size()/Settings.cellHeight) {
				validOptions.add(lc);
			}
		}
		
		
		ArrayList<Cell> right = new ArrayList<>();
		right.addAll(this.grid.get(this.size-1));
		for (Cell rc : right) {
			if (rc.yPos != 0 && rc.yPos != this.grid.get(0).size()/Settings.cellHeight) {
				validOptions.add(rc);
			}
		}
		
		
		for (int i = 1; i < this.size-1; i++) {
			validOptions.add(this.grid.get(i).get(0));
			
			if (this.grid.get(i).get(this.size-1).isBlocked() == true) {
				validOptions.add(this.grid.get(i).get(this.size-1));
			}
		}
		
		
		//must be adjacent to passage
		while (!validExit) {
			int exitIndex = random.nextInt(validOptions.size());
			
			Cell exitCell = validOptions.get(exitIndex);
			
			int exitX = exitCell.getxPos()/Settings.cellWidth;
			int exitY = exitCell.getyPos()/Settings.cellHeight;
						
			
			//check for passages
			if (
					(exitCell.getxPos() - 1 > 0 && grid.get(exitX-1).get(exitY).isBlocked() == false && exitCell.getyPos() != this.size-1)
					
				) {
				
				this.grid.get(exitX).get(exitY).setExit(true);
				validExit = true;
				
				System.out.println(this.grid.get(exitX).get(exitY).isExit);
				
				System.out.println("final exit x " + exitX);
				System.out.println("final exit y " + exitY);
			}
			
			else if (
					
					(exitCell.getxPos() + 1 < this.size-1 && grid.get(exitX+1).get(exitY).isBlocked() == false && exitCell.getyPos() != this.size-1)
					
				) {
				
				this.grid.get(exitX).get(exitY).setExit(true);
				validExit = true;
				
				System.out.println(this.grid.get(exitX).get(exitY).isExit);
				
				System.out.println("final exit x " + exitX);
				System.out.println("final exit y " + exitY);
			}
			
			else if (
					
					(exitCell.getyPos() - 1 > 0 && grid.get(exitX).get(exitY-1).isBlocked() == false) 
					
				) {
				
				this.grid.get(exitX).get(exitY).setExit(true);
				validExit = true;
				
				System.out.println(this.grid.get(exitX).get(exitY).isExit);
				
				System.out.println("final exit x " + exitX);
				System.out.println("final exit y " + exitY);
			}
			
			else if (
					
					(exitCell.getyPos() + 1 < this.size-1 && grid.get(exitX).get(exitY+1).isBlocked() == false) 
					
				) {
				
				this.grid.get(exitX).get(exitY).setExit(true);
				validExit = true;
				
				System.out.println(this.grid.get(exitX).get(exitY).isExit);
				
				System.out.println("final exit x " + exitX);
				System.out.println("final exit y " + exitY);
			}
			
		}
	}
	
	
	public Cell returnEntry() {
		
		Cell entry = null;
		for (ArrayList<Cell> a : this.grid) {
			for (Cell c : a) {				
				if (c.isEntry) {
					entry = c;
				}
			}
		}
		
		return entry;
	}
	
	
	//create items and place all tiles
	public void paint(Graphics g) {
			
		//nested depth of 2 - okay?
		for (ArrayList<Cell> a : this.grid) {
			
			for (Cell c : a) {		
				
				if (c.isEntry()) {
					g.drawImage(SpriteBank.floor[1][13], c.xPos, c.yPos, c.width, c.height, null);
				}
				else if (c.isExit) {
					g.drawImage(SpriteBank.floor[1][5], c.xPos, c.yPos, c.width, c.height, null);
				}
				else if (c.isBlocked()) {
					g.drawImage(SpriteBank.floor[1][3], c.xPos, c.yPos, c.width, c.height, null);
				}				
				else {
					g.drawImage(SpriteBank.floor[2][1], c.xPos, c.yPos, c.width, c.height, null);
				}
				
				
			}
		}
			
	}

}
