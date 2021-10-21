package mazeGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Projectile {

	//direction to move in
	int direction;

	//pos
	int x;
	int y;

	//these values are used to calculate pos in grid
	//since this will only move in one direction
	int rowColVal;
	int rowCol;

	//speed
	int speed = 6;

	//crashed
	boolean crashed = false;

	public Projectile(int x, int y, int direction) {
		this.x = x;
		this.y = y;

		this.direction = direction;
	}

	//bounds
	public Rectangle getRectangle() {
		return new Rectangle(x, y, Settings.cellWidth, Settings.cellHeight);
	}

	//enemy collision
	public boolean intersects(Enemy e, ArrayList<ArrayList<Cell>> grid) {
		ArrayList<Cell> lineOfTravel = new ArrayList<>();
		//if rowCol == 0, we use rowColVal to get the projs row
		//direction moving along row - row stays the same
		if (rowCol == 0) {

			//			 int col = Math.round(x/Settings.cellWidth) * Settings.cellWidth;			 

			for (ArrayList<Cell> column : grid) {
				lineOfTravel.add(column.get(rowColVal/Settings.cellWidth));
			}	
		}
		//else it means column
		else if (rowCol == 1) {
			lineOfTravel.addAll(grid.get(rowColVal/Settings.cellWidth));
		}

		for (Cell c : lineOfTravel) {

			if (c.isBlocked()) {
				if(getRectangle().intersects(c.getRectangle())) {
					crashed = true;
				}
			}
		}

		if (getRectangle().intersects(e.getRectangle())) {
			crashed = true;
			return true;
		}

		return false;
	}

	public void move() {
		if (!crashed) {
			//left
			if (x < 0 || x > 1250 || y < 0 || y > 940) {
				crashed = true;
			}
			else {
				if (direction == 1) {
					rowColVal = x;
					rowCol = 0;
					x -= speed;

				}
				//right
				else if (direction == 2) {
					rowColVal = x;
					rowCol = 0;
					x += speed;
				}
				//up
				else if (direction == 3) {
					rowColVal = y;
					rowCol = 1;
					y -= speed;

					System.out.println("x: " + x + " vs " + rowCol );

				}
				//down
				else if (direction == 0) {
					rowColVal = y;
					rowCol = 1;
					y += speed;
				}

			}
		}
	}


	public void paint(Graphics2D g) {
		if (!crashed) {
			g.setColor(Color.gray);
			g.fillRect(x + ( Settings.cellWidth/2), y,  Settings.cellWidth/3, Settings.cellHeight/3);
		}

	}

}
