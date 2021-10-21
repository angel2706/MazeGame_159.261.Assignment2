package mazeGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.JPanel;



public class GamePanel extends JPanel implements Runnable, KeyListener {

	//Panel Dimensions

	//Game state manager
	GameStateManager gsm;
	Thread thread;

	BufferedImage image;

	// game loop stuff
	private final int FPS = 30;
	private final int TARGET_TIME = 1000 / FPS;	

	//while running
	private boolean running;
	private Graphics2D g;

	public GamePanel() {
		setPreferredSize(new Dimension(31 * 30 + 290, 31 * 30));
		setFocusable(true);
		requestFocus();
	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		//needs to call or pass on key events to appropriate methods/classes
		KeyEvents.setKeyEvent(e);
	}
	public void keyReleased(KeyEvent e) {}


	public void addNotify() {
		super.addNotify();

		if(thread == null) {

			addKeyListener(this);
			thread = new Thread(this);
			thread.start();
		}

	}
	

	public void run() {
		init();

		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0; // 60 Ticks per second
		double ns = 1_000_000_000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();


		while(running) {	

			long now = System.nanoTime();

			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1)
			{
				try {
					update();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				draw();
				drawToScreen();

				updates++;
				delta--;
			}

			

			frames++;

			if(System.currentTimeMillis() - timer > 1000){ // Waits
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}

	}


public void init() {
	running = true;
	image = new BufferedImage(31 * 30 + 420, 31 * 30, 1);
	g = (Graphics2D) image.getGraphics();
	gsm = new GameStateManager();

}

private void drawToScreen() {
	Graphics g2 = getGraphics();
	g2.drawImage(image, 0, 0, 31 * 30 + 420, 31 * 30, null);
	g2.dispose();
}

public void update() throws FileNotFoundException {
	gsm.update();
}

public void draw() {
	try {
		gsm.draw(g);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
