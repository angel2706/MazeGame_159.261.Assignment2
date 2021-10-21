package mazeGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {
	
	Settings gs = new Settings();
	
	//defines rules for maze
	int level;
	int size;
	
	//number of enemies - would like to have different types
	private int numEnemies;	
	
	//number of items
	private int numItems;

	//holder for level details
	ArrayList<String> details = new ArrayList<>();
	
	//holds generated maze for level
	RandomMaze maze;

	public Level(int level) throws FileNotFoundException  {
		this.level = level;
		
		loadMaze();
		createMaze();		
	}	
	
	//loads maze
	private void loadMaze() throws FileNotFoundException {
		/*
		 * All level text files have a value for each required field even if its 0
		 * */		
		
		String levelFile = "resources\\levels\\lv" + getLevel() + ".txt";
		
		//loads maze level information based on given level
		Scanner reader = new Scanner(new FileReader(levelFile));

		while(reader.hasNextLine()) {
			this.details.add(reader.nextLine());
		}

		//dimensions of maze provided in file
		this.size = Integer.parseInt(details.get(0));
		
		//num enemies
		this.numEnemies = Integer.parseInt(details.get(1));
		
		//num items
		this.numItems = Integer.parseInt(details.get(2));
		
		reader.close();
		
	}
	
	
	//creates maze
	private void createMaze() {
		
		//size must be odd
		RandomMaze rmaze = new RandomMaze(getSize());
		
		setMaze(rmaze);
	}
	
	//returns an arraylist of all pathway cells
	public ArrayList<Cell> unblockedCells() {
		ArrayList<Cell> valid = new ArrayList<>();
		
		for (ArrayList<Cell> col : this.maze.grid) {
			
			for (Cell c : col) {
				
				if (c.isBlocked() == false && c.isEntry == false && c.isExit == false) {
					valid.add(c);
				}
			}
		}
		
		return valid;
	}

	

	//getters and setters
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public RandomMaze getMaze() {
		return maze;
	}

	public void setMaze(RandomMaze maze) {
		this.maze = maze;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getNumEnemies() {
		return numEnemies;
	}


	public void setNumEnemies(int numEnemies) {
		this.numEnemies = numEnemies;
	}
	
	public ArrayList<ArrayList<Cell>> getGrid() {
		
		return this.maze.grid;
	}
	
	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

}
