package mazeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Hud {

	int level;
	
	
	//display total score
	Score score;
	
	//show updating timer
	
	//collected items
	int collectedItems;
	
	//level num items
	int numItems;
	
	//"press escape to pause"
	
	

	//fonts
	Font headFont = new Font("SansSerif", Font.PLAIN, 40);
	Font subFont = new Font("SansSerif", Font.PLAIN, 30);
	Font font = new Font("SansSerif", Font.PLAIN, 25);;
	
	//background
	BufferedImage[][] bg = SpriteBank.hudbg;
	
	public Hud(Score score, int numItems, int level) {
		this.score = score;
		this.numItems = numItems;
		this.level = level;
	}

	public void incCollected() {
		//increases value by 1
		collectedItems++;
		
		//increments score
		score.incScore();
	}	
	
	public void paint(Graphics2D g) {
		
		g.drawImage(bg[0][0], 930, 0, null);
		
		//title of game at top
		g.setFont(headFont);		
		g.setColor(Color.white);
		g.drawString("Ruin Runner", 967, 100);
		
		//all catergory headers
		g.setFont(subFont);
		
		g.drawString("Level: " + level, 970, 150);
		
		g.drawString("Time remaining:", 970, 320);
		
		g.drawString("Score", 970, 430);
		
		g.drawString("Items Collected", 970, 540);
		
		g.drawString("Total Score", 970, 650);
		
		
		
		
		//corresponding values using smaller font
		g.setFont(font);
		
		//placeholder time value
		g.drawString("beeb beeb ", 970, 360);
		
		g.drawString(score.getLevelScoreString(), 970, 470);
		
		g.drawString(getCollectedStatus(), 970, 580);
		//playthrough total score
		g.drawString(score.getTotalScore() , 970, 690);
		
		g.drawString("Press 'ESC' to pause", 957, 850);
		
		
		
		
	}
	
	//getters and setters
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public int getCollectedItems() {
		return collectedItems;
	}

	public void setCollectedItems(int collectedItems) {
		this.collectedItems = collectedItems;
	}
	
	public String getCollectedStatus() {
		return String.valueOf(collectedItems) + " / " + String.valueOf(numItems); 
	}
	
	
}
