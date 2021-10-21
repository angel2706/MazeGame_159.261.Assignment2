package mazeGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PlayState extends GameState {

	Player player;
	Enemies enemies;
	Items items;

	//backgrounds
	BufferedImage[][] fillbg;


	//on screen helper menu?
	Hud hud;
	Score score = new Score();	

	boolean newGame;
	
	Playthrough playthrough;
	
	


	public PlayState(GameStateManager gsm, boolean newGame) {
		super(gsm);
		
		this.newGame = newGame;
		
		if (newGame) {
			playthrough = new Playthrough(1);
			score.setGameTotal(0);
			saveProgress();
		}
		else {
			playthrough = new Playthrough(readProgress());
		}
	}

	public void init() {

		fillbg = SpriteBank.fillbg;

		
		enemies = new Enemies(playthrough.level.getNumEnemies(), playthrough.level.unblockedCells(), playthrough.level.maze.grid);
		player = new Player(playthrough.maze.returnEntry().xPos, playthrough.maze.returnEntry().yPos, playthrough.level.maze.grid, enemies.enemies);
		items = new Items(playthrough.level.getNumItems(), playthrough.level.unblockedCells());

		hud = new Hud(score, playthrough.level.getNumItems(), playthrough.getCurrentLevel());
	}


	public void update() throws FileNotFoundException {

		//game over checks
		if (gameOver()) {
			gsm.setState(score, playthrough.getCurrentLevel(), false);
		}
		//level up checks
		if (!checkLevelUp()) {

			handleInput();

			//updates player x and y pos if can move
			player.update();
			enemies.moveEnemies();
			

			for (int i = 0; i < items.getNumItems(); i++) {
				Item item = items.items.get(i);
				if (player.intersects(item)) {

					item.setCollected(true);

				}
			}

			hud.setCollectedItems(items.getNumCollected());
			score.setLevelTotal(items.getNumCollected() * 20);
			hud.setScore(score);

		}
		else {levelUp();}

	}


	public void draw(Graphics2D g) {

		try {

			g.drawImage(fillbg[0][0], 0, 0, null);

			playthrough.level.getMaze().paint(g);
			enemies.paint(g);
			player.paint(g);
			items.paint(g);
			hud.paint(g);

		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	public void handleInput() {
		//if event key pressed
		//what key pressed
		if(KeyEvents.isKeyEvent()) {
			player.keyPressed(KeyEvents.getKeyEvent());
		}
	}


	public boolean gameOver() {

		//if enemy collision
		for (int j = 0; j < enemies.enemies.size(); j++) {
			Enemy e = enemies.enemies.get(j);
			if (player.intersects(e)) {

				return true;

			}
			
		}

		//if timer runs out 
		
		//else
		return false;
	}

	public boolean checkLevelUp() {

		if (player.foundExit) {return true;}

		return false;
	}

	public void levelUp() {

		
		//change playthrough level
		playthrough.nextLevel();
		
		if (playthrough.isFinished()) {
			try {
				
				gsm.setState(score, playthrough.getCurrentLevel(), true);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else {
		
			//update hud
			hud.setLevel(playthrough.getCurrentLevel());
			hud.setNumItems(playthrough.getLevel().getNumItems());

			score.setGameTotal(score.getGameTotal() + score.getLevelTotal());

			//saves progress
			saveProgress();

			//reset enemies and player
			init();
		}
	}

	//saves the current level to a file
	public void saveProgress() {
		try {

			//create buffered writer
			BufferedWriter output = new BufferedWriter(new FileWriter("resources\\cLevel.txt"));

			//write new cLevel
			output.write(playthrough.getCurrentLevelString());

			output.newLine();
			//write score
			output.write(score.getGameScoreString());

			//Close the output
			output.close();
		}
		catch(IOException e) {
			//Catch exception
			System.out.println("Save failed");
			e.printStackTrace();
		}
	}


	//reads last level from file
	public int readProgress() {
		//vars
		ArrayList<String> details = new ArrayList<>();
		int cLevel = 0;

		try {
			// create buffered reader to read file
			String file = "resources\\cLevel.txt";
			Scanner reader = new Scanner(new FileReader(file));

			while(reader.hasNextLine()) {
				details.add(reader.nextLine());
			}

			//read cLevel
			//converting strings into ints
			cLevel = Integer.parseInt(details.get(0));

			score.setGameTotal(Integer.parseInt(details.get(1)));

			reader.close();

		}
		catch(IOException e) {
			e.printStackTrace();
		}

		System.out.println("CURRENT LEVEL" + cLevel);
		return cLevel;
	}





}
