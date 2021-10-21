package mazeGame;

public class Score {

	//default score is 0
	int gameTotal = 0;
	int levelTotal = 0;

	public Score() {		
	}
	
	
	//increases score by default 20 points
	public void incScore() {
		levelTotal += 20;
	}
	
	//overriding the toString method
	public String getLevelScoreString() {
		return String.valueOf(levelTotal);
	}
	
	public String getGameScoreString() {
		return String.valueOf(gameTotal);
	}
	
	public String getTotalScore() {
		return String.valueOf(gameTotal + levelTotal);
	}


	public int getGameTotal() {
		return gameTotal;
	}


	public void setGameTotal(int gameTotal) {
		this.gameTotal = gameTotal;
	}


	public int getLevelTotal() {
		return levelTotal;
	}


	public void setLevelTotal(int levelTotal) {
		this.levelTotal = levelTotal;
	}
	
	

}
