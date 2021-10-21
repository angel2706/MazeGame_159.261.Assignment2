package mazeGame;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Cell {
	int width;
	int height;
	int xPos;
	int yPos;
	
	boolean blocked;
	
	boolean isExit = false;
	boolean isEntry = false;
	int pathNeighbourX;
	int pathNeighbourY;
		

	//Colour - set based on blocked value
	Color cellCol;
	

	public Cell(int width, int height, int xPos, int yPos, boolean blocked) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.blocked = blocked;
	}
	
	
	public Rectangle getRectangle() {
		return new Rectangle(xPos, yPos, Settings.cellWidth, Settings.cellHeight);
	}
	
	//Just Getters and Setters
	
	public int getPathNeighbourX() {
		return pathNeighbourX;
	}


	public void setPathNeighbourX(int pathNeighbourX) {
		this.pathNeighbourX = pathNeighbourX;
	}


	public int getPathNeighbourY() {
		return pathNeighbourY;
	}


	public void setPathNeighbourY(int pathNeighbourY) {
		this.pathNeighbourY = pathNeighbourY;
	}

	public int getxPos() {
		return xPos;
	}


	public void setxPos(int xPos) {
		this.xPos = xPos;
	}


	public int getyPos() {
		return yPos;
	}


	public void setyPos(int yPos) {
		this.yPos = yPos;
	}


	public boolean isBlocked() {
		return blocked;
	}


	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}


	public Color getCellCol() {
		return cellCol;
	}


	public void setCellCol(Color cellCol) {
		this.cellCol = cellCol;
	}

	public boolean isExit() {
		return isExit;
	}


	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}

	public boolean isEntry() {
		return isEntry;
	}

	public void setEntry(boolean isEntry) {
		this.isEntry = isEntry;
	}
	
	
}
