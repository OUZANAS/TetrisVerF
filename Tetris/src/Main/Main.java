package Main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {

		JFrame window = new JFrame("Simple Tetris");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		// add gamePanel to the window
		GamePanel qp = new GamePanel();
		window.add(qp);
		window.pack(); // the size of the GamePanel becomes the size of the window 
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		qp.launchGame();
}
}