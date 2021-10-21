package mazeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GameOverState extends GameState {

	BufferedImage[][] selector;
	BufferedImage[][] bg;

	int currentOption = 0;
	boolean gameWon;

	String[] options = {
			"PLAY AGAIN",
			"QUIT"
	};

	//fonts for display
	Font font = new Font("SansSerif", Font.PLAIN, 40);
	Font titleFont = new Font("SansSerif", Font.PLAIN, 80);

	//values to be displayed
	int level;	
	Score score;

	//leaderboard
	ArrayList<Integer> leaderboard = new ArrayList<>();

	public GameOverState(GameStateManager gsm, Score score, int level, boolean gameWon) throws FileNotFoundException {
		super(gsm);

		this.score = score;
		this.level = level;
		this.gameWon = gameWon;
		
		compareLeaderBoard();
		
	}

	@Override
	public void init() {
		selector = SpriteBank.selector;
		bg = SpriteBank.bg;


	}

	@Override
	public void update() {
		handleInput();

	}

	@Override
	public void draw(Graphics2D g) throws FileNotFoundException {
		g.drawImage(bg[0][0], 0, 0, null);

		g.setColor(Color.black);
		g.fillOval(50, 50, 1120, 830);

		g.setColor(Color.white);
		g.setFont(titleFont);

		if (!gameWon) {	
			g.drawString("Game over.", 406, 500);

			g.setFont(font);

			g.drawString("Highest Level: " + String.valueOf(level), 470, 560);
			g.drawString("Final Score: " + score.getGameScoreString(), 470, 600);


		}
		else if (gameWon) {

			g.drawString("Success!", 426, 200);
			g.setFont(font);
			displayLeaderBoard(g);
			g.drawString("Final Score: " + score.getGameScoreString(), 470, 280);
			
			
		}

		//replay or quit options
		g.drawString(options[0], 520, 700);
		g.drawString(options[1], 520, 770);

		if(currentOption == 0) {g.drawImage(selector[0][0], 470, 670, null);}
		else if (currentOption == 1) {g.drawImage(selector[0][0], 470, 740, null);}

	}

	@Override
	public void handleInput() {
		if (KeyEvents.isKeyEvent()) {

			KeyEvent e = KeyEvents.getKeyEvent();

			//System.out.println("e: " + e==null);

			if (e.getKeyCode() == KeyEvent.VK_DOWN && currentOption == 0) {
				currentOption ++;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && currentOption == 1) {
				currentOption --;
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				selectOption();
			}
		}

	}

	private void selectOption() {
		if (currentOption == 0) {
			gsm.setState(true);
		}
		if (currentOption == 1) {
			System.exit(0);
		}
	}
	
	
	private void displayLeaderBoard(Graphics2D g) {
		g.drawString("LEADER BOARD", 450, 350);
		int rank = 0;
		
		for (int i = 0 ; i < leaderboard.size(); i++) {
			rank = i + 1;
			g.drawString(rank+". " + String.valueOf(leaderboard.get(i)), 470, 420 + (i * 40));
		}
	}


	private void readLeaderBoard() throws FileNotFoundException {

		ArrayList<String> placeholder = new ArrayList<>();

		String lbFile = "resources\\leaderboard.txt";

		//loads maze level information based on given level
		Scanner reader = new Scanner(new FileReader(lbFile));

		while(reader.hasNextLine()) {
			placeholder.add(reader.nextLine());
		}	

		if (placeholder.size() > 0) {
			for (String s : placeholder) {
				this.leaderboard.add(Integer.parseInt(s));
			}
		}
	}


	private void compareLeaderBoard() throws FileNotFoundException {

		readLeaderBoard();

		
		//if leaderboard empty, add score automatically
		if (leaderboard.size() < 5) {
			leaderboard.add(score.getGameTotal());
		}
		else if (leaderboard.size() > 0) {

			//smallest to largest
			Collections.sort(leaderboard);
			
			//will not bother to compare score if score is smaller than the
			//smallest highscore saved
			if (score.getGameTotal() > leaderboard.get(0)) {
				
				//-1 as a non number measure
				int pos = -1;
				
				//loops through
				for (int i = 0 ; i < leaderboard.size(); i++) {

					if (score.getGameTotal() > leaderboard.get(i)) {
						pos = i;
					}					
				}
				
				if (pos != -1) {
					addNewScore();
				}				
			}
				
		} 
		
		//sort biggest to smallest
		Collections.sort(leaderboard, Collections.reverseOrder());

		//save
		saveLeaderBoard();
	}
	
	
	private void addNewScore() {
		
		if (leaderboard.size() > 5) {leaderboard.remove(0);}		
		
		leaderboard.add(score.getGameTotal());
	}


	private void saveLeaderBoard() {
		try {

			String lbFile = "resources\\leaderboard.txt";

			//create buffered writer
			BufferedWriter output = new BufferedWriter(new FileWriter(lbFile));
			
			int sizeMeasure = leaderboard.size();
			
			while (sizeMeasure > 5) {
				//System.out.println("LEADERBOARD SIZE " + leaderboard.size());
				leaderboard.remove(leaderboard.size() - 1);
				sizeMeasure--;
			}

			//System.out.println("LEADERBOARD SIZE " + leaderboard.size());
			//writes top 5 highscores to leaderboard
			for (int i = 0 ; i < leaderboard.size(); i++) {

				output.write(String.valueOf(leaderboard.get(i)));

				output.newLine();
			}			

			//Close the output
			output.close();
		}
		catch(IOException e) {
			//Catch exception
			System.out.println("Save failed");
			e.printStackTrace();
		}
	}




}
