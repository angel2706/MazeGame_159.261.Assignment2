package mazeGame;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemies {

	int numEnemies = 0;
	
	//holds positions for enemy spawning
	ArrayList<ArrayList<Cell>> grid;
	ArrayList<Cell> valid;
	
	ArrayList<Enemy> enemies = new ArrayList<>();
	
	public Enemies(int numEnemies, ArrayList<Cell> valid, ArrayList<ArrayList<Cell>> grid) {
		this.numEnemies = numEnemies;
		
		this.valid = valid;
		this.grid = grid;
		
		//create appropriate enemies
		createEnemies();		
	}	
	

	//creates x amount of enemies using the valid positions
	private void createEnemies() {
		
		Random rand = new Random(); 
		
		for (int i = 0; i < numEnemies; i++) {
			
			try {
				
				Cell pos = valid.get(rand.nextInt(valid.size()));
				
				Enemy e = new Enemy(pos.xPos, pos.yPos, grid.size());
				enemies.add(e);
				
				valid.remove(pos);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	public void paint(Graphics g) {
		
		//draws each enemy
		for (int i = 0; i < this.enemies.size(); i++) {
			
			Enemy e = enemies.get(i);
		
			g.drawImage(e.animation.getImage(), e.x, e.y, null);
		}
		
	}
	
	public void moveEnemies() {
		
		//draws each enemy
		for (int i = 0; i < this.enemies.size(); i++) {
			
			Enemy e = enemies.get(i);
		
			e.move(this.grid);
		}		
	}
	
	
	//getters and setters
	public int getNumEnemies() {
		return numEnemies;
	}

	public void setNumEnemies(int numEnemies) {
		this.numEnemies = numEnemies;
	}
}
