package mazeGame;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		
		
		JFrame frame = new JFrame("Maze Game");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel gp = new GamePanel();
		frame.add(gp);
		frame.pack();
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);  
		
		
	}
}
