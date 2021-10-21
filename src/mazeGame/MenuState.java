package mazeGame;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class MenuState extends GameState {

	BufferedImage[][] selector;
	BufferedImage[][] bg;

	int currentOption = 0;

	String[] options = {
			"CONTINUE",
			"NEW GAME",
			"QUIT"
	};

	Font font = new Font("SansSerif", Font.PLAIN, 40);
	Font titleFont = new Font("SansSerif", Font.PLAIN, 120);

	public MenuState(GameStateManager gsm) {
		super(gsm);
	}


	public void init() {
		selector = SpriteBank.selector;
		bg = SpriteBank.bg;
	}


	public void update() {
		handleInput();
	}


	public void draw(Graphics2D g) {
		
		g.drawImage(bg[0][0], 0, 0, null);

		g.setFont(titleFont);		
		g.drawString("Ruin Runner", 280, 300);

		g.setFont(font);
		g.drawString(options[0], 545, 600);
		g.drawString(options[1], 545, 670);
		g.drawString(options[2], 545, 740);

		if(currentOption == 0) {g.drawImage(selector[0][0], 505, 570, null);}
		else if (currentOption == 1) {g.drawImage(selector[0][0], 505, 640, null);}
		else if (currentOption == 2) {g.drawImage(selector[0][0], 505, 710, null);}

	}


	public void handleInput() {
		//down check, up check, enter check
		if (KeyEvents.isKeyEvent()) {
			
			KeyEvent e = KeyEvents.getKeyEvent();
			
			//System.out.println("e: " + e==null);
			
			if (e.getKeyCode() == KeyEvent.VK_DOWN && (currentOption == 0 || currentOption == 1)) {
				currentOption ++;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && (currentOption == 1 || currentOption == 2)) {
				currentOption --;
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				selectOption();
			}
		}
		
		//System.out.println("Selection: " + currentOption);
	}
	
	private void selectOption() {
		if (currentOption == 0) {
			//continue playthrough
			gsm.setState(false);
		}
		if(currentOption == 1) {
			//new playthrough
			gsm.setState(true);
		}
		if (currentOption == 2) {
			System.exit(0);
		}
	}




}
