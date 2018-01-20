package edu.gvsu.bonazzig.GUI1024;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import edu.gvsu.bonazzig.game1024.NumberGame;

/*************************************************************************************************************************************
 * Class that contains the main method for a 1024 game. The main set ups the
 * game engine, the game's frame, and the three principal elements of the GUI:
 * 	-	The game's board, basically the "game" it-self, made of tiles that can be swiped in the four cardinal directions.
 * 	-	The statistics area, where statistics such as time elapsed, number of slides, number of games won, lost and the total played.
 * 	-	The menu bar, that contains an option element in which the user can start a new game (reset all the stats for the session), 
 * 			reset the current game, undo the last move, change some options in the board (resizing) and quit the game.
 * 
 * @author  Gionata Bonazzi
 * @version 17 March 2017
 *
 ************************************************************************************************************************************/
public class TenTwentyFourMain {
	
	public static void main(String[] args) {
		
		//Sets up the frame
		JFrame frame = new JFrame("Gionata's game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());		
		
		//Sets up the menu and the panels
		NumberGame gameEngine = new NumberGame();
		
		//Sets up the game engine, statistics area, and menu bar.
		GameStatistics stats = new GameStatistics();
		GameBoard board = new GameBoard(gameEngine, stats, frame);
		GameMenu menu = new GameMenu(gameEngine, board, frame);
		
		//adds the elements
		frame.getContentPane().add(menu, BorderLayout.NORTH);
        frame.getContentPane().add(stats, BorderLayout.WEST);
        frame.getContentPane().add(board, BorderLayout.CENTER);
        
        //Packs the elements in the frame and set it visible
        frame.pack();
        frame.setVisible(true);
        
        //Sets position of this frame at the center of the screen
        frame.setLocationRelativeTo(null);

	}
}
