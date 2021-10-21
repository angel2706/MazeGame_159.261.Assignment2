package mazeGame;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public abstract class GameState {
	
	GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void update() throws FileNotFoundException;
	public abstract void draw(Graphics2D g) throws FileNotFoundException;
	public abstract void handleInput();
	
	
}
