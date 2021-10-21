package mazeGame;

import java.awt.Graphics2D;
import java.io.FileNotFoundException;

public class GameStateManager {
	
	private boolean paused;
	private PauseState pauseState;
	
	private GameState state;
	public int currentState;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int GAMEOVER = 2;
	
	public GameStateManager() {
		
		//music loop start here
		
		paused = false;
		pauseState = new PauseState(this);
		
		currentState = MENU;
		setState(currentState);	
		
	}
	
	public void setState(int i) {
		
		currentState = i;
		
		if (i == MENU) {
			state = new MenuState(this);			
		}
		
		state.init();
	}
	
	//overloaded for gameovers
	public void setState(Score score, int level, boolean gameWon) throws FileNotFoundException {
		state = new GameOverState(this, score, level, gameWon);
		state.init();
	}

	//overloaded for new playthroughs
	public void setState(boolean newGame) {
		state = new PlayState(this, newGame);	
		state.init();
	}
	

	public void update() throws FileNotFoundException {
		//if paused, update state based on paused variable		
		
		//else {update state}
		state.update();
		
	}
	
	public void draw(Graphics2D g) throws FileNotFoundException {
		
		//if paused, draw menu
		
		//System.out.println("gsm draw called");
		//else draw current game state
		state.draw(g);
		
	}
	
	public void setPaused(boolean b) {
		paused = b;
	}


}
