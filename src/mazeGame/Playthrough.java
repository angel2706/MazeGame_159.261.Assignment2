package mazeGame;

import java.io.FileNotFoundException;

public class Playthrough {
	
	//default values
	private int currentLevel;
	boolean finished = false;
	
	Level level;
	RandomMaze maze;
	
	public Playthrough(int currentLevel) {
		
		//gets progress
		this.currentLevel = currentLevel;
		if (currentLevel == 10) {
			currentLevel = 1;
		}
		updateLevel();		
	}
	
	//moves playthrough onto next level
	public void nextLevel() {
		incLevel();
		updateLevel();
	}
	
	//Increases current level
	private void incLevel() {
		currentLevel++;
	}
	
	//update playthrough - loads new level if current has updated	
	private void updateLevel() {
		
		//end of game check
		if (currentLevel == 11) {
			this.finished = true;
		}
		else {
			try {
				//creates level based on currentlevel value
				this.level = new Level(currentLevel);
				this.maze = this.level.maze;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//setters and getters

	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public String getCurrentLevelString() {
		return String.valueOf(currentLevel);
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public RandomMaze getMaze() {
		return maze;
	}

	public void setMaze(RandomMaze maze) {
		this.maze = maze;
	}
}
