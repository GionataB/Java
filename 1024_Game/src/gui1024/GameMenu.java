package gui1024;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import game1024.NumberGame;


/*************************************************************************
 * The game's menu that extends JMenuBar.
 * From the menu, it is possible to:
 *  - Create a new game, resetting all the statistics.
 *  - Restarting the current game, keeping the statistics.
 *  - Undo the last move, making the last slide like it never happened 
 *  	(no animation for the undoing).
 *  - Open the option's frame, to directly interact with some options from
 *  	the game engine.
 *  - Quit the game and close the frame.
 * @author  Gionata Bonazzi
 * @version 21 March 2017
 *
 ************************************************************************/
public class GameMenu extends JMenuBar{
	
	/** The menu object **/
	private JMenu menu;
	
	/** The menu's item for resetting the game **/
	private JMenuItem resetItem;
	
	/** The menu's item for quitting the game **/
	private JMenuItem quitItem;
	
	/** The menu's item for opening the option's frame **/
	private JMenuItem optionItem;
	
	/** The menu's item for starting a new game **/
	private JMenuItem newGameItem;
	
	/** The menu's item for undoing the last move **/
	private JMenuItem undoItem;
	
	/** The address to the NumberGame Object**/
	private NumberGame gameEngine;
	
	/** The address to the GameBoard Object**/
	private GameBoard gameBoard;
	
	/** The address to the main JFrame Component**/
	private JFrame frame;
	
	
	/*********************************************************************
	 * Creates the game's menu.
	 * @param game  the game's engine
	 * @param board the game's board
	 * @param frame the game's main frame
	 ********************************************************************/
	public GameMenu(NumberGame game, GameBoard board, JFrame frame){
		this.gameBoard  = board;
		this.gameEngine = game;
		this.frame 		= frame;
		
		this.menu 		 = new JMenu("Options...");
		this.resetItem 	 = new JMenuItem("Reset");
		this.quitItem  	 = new JMenuItem("Quit");
		this.optionItem  = new JMenuItem("Option...");
		this.newGameItem = new JMenuItem("New game");
		this.undoItem 	 = new JMenuItem("Undo");
		
		setItem(newGameItem, 'N');
		setItem(resetItem, 'R');
		setItem(undoItem, 'U');
		setItem(optionItem, 'O');
		setItem(quitItem, 'Q');
		
		this.add(menu);
		
		this.setVisible(true);
	}
	
	/*********************************************************************
	 * Set the desired menu item, with a mnemonic, an accelerator
	 * and the ActionListener. Last, adds the item the JMenu object.
	 * 
	 * Note:
	 *  - Mnemonic: for all users, when the menu is open, shortcut to the item
	 *  	by pressing the assigned keyboard's key.
	 *  - Accelerator: for power users, shortcut to the item even when the menu is close,
	 *  	used by pressing a combination of Alt + the assigned key (on macOS) or
	 *  	CTRL + the assigned key (on Windows).
	 *  In this project, the key for the mnemonic and the accelerator are the same.
	 * @param item the JMenuItem to set up
	 * @param key  the keyboard's key for the mnemonic and the accelerator.
	 ********************************************************************/
	private void setItem(JMenuItem item,  char key){
		item.setMnemonic(key);
		item.setAccelerator(KeyStroke.getKeyStroke(key, ActionEvent.ALT_MASK));
		item.addActionListener(new MenuListener());
		menu.add(item);
	}
	
/*************************************************************************
 * ActionListener implementation for the menu.
 * @author  Gionata Bonazzi
 * @version 21 March 2017
 ************************************************************************/
private class MenuListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		
		//Quit the game
		if(e.getSource() == quitItem){
			System.exit(1);
		}
		
		//New game, resets statistics
		if(e.getSource() == newGameItem){
			GameStatistics.UNDO = 0;
			GameStatistics.SLIDES = 0;
			GameStatistics.TOTAL = 1;
			GameStatistics.WINS = 0;
			GameStatistics.LOSSES = 0;
			
			gameBoard.resetGame();
			return;
		}
		
		//Reset game, adds a loss
		if(e.getSource() == resetItem){
			GameStatistics.UNDO = 0;
			GameStatistics.SLIDES = 0;
			GameStatistics.TOTAL++;
			GameStatistics.LOSSES++;
			
			gameBoard.resetGame();
			return;
		}
		
		//Open the option's frame
		if(e.getSource() == optionItem){
			OptionFrame window = new OptionFrame(gameEngine, gameBoard);
	        return;
		}
		
		//Undo last move
		if(e.getSource() == undoItem){
			try{
				gameEngine.undo();
				
				GameStatistics.SLIDES--;
				GameStatistics.UNDO++;
				
				gameBoard.renderBoard();
			}
			
			//exception if the undo is done on the first board's state (no slides have been done yet)
			catch(IllegalStateException exc){
				JOptionPane.showMessageDialog(frame, "You can't undo anymore.", "Warning", JOptionPane.PLAIN_MESSAGE);
			}		
		}
		
	}	
}
}
