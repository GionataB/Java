package edu.gvsu.bonazzig.GUI1024;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import edu.gvsu.bonazzig.game1024.NumberGame;

/****************************************************************************************************
 * Creates a frame with three (there is no limit) tabbed panels that manage
 * different options:
 * - The first panel manages the size of the board, using a JSlider
 * - The second panel manages the winning value for the game, using an editable JComboBox.
 * - The third panel manages the time limit for the game, using JComboBox not editable.
 * 
 * Pressing the Ok button saves the changes made in all the tabbel windows, while
 * pressing close discharge all of them.
 * The default closing operation for this frame is "do nothing on close", meaning that
 * pressing the closing button default of the Operation System's window will not make any change
 * in the frame.
 * 
 * As a side note, it is possible to "cheat" by opening the option's window and the playing in background,
 * this allows the user to play with the timer stopped.
 * To prevent this, I tried using the setEditable() function to block the main Frame. However after getting some
 * unexpected behaviors from the KeyListener after unlocking the frame under certain conditions, I decided
 * that it is safer to let the user cheat than to give the user a way to crash the program.
 * 
 * @author  Gionata Bonazzi
 * @version 21 March 2017
 *****************************************************************************************************/
public class OptionFrame extends JFrame {
	
	/** JButton to apply all the changes **/
	private JButton applyButton;
	
	/** JButton to discard all the changes **/
	private JButton closeButton;
	
	/** ResizeSlider window for the option frame **/
	private ResizeSlider resizeBoard;
	
	/** WinningValueBox window for the option frame **/
	private WinningValueBox valueBox;
	
	/** DifficultyBox window for the option frame **/
	private DifficultyBox difficultyBox;
	
	/** Swing component to create tabbed windows in a single frame **/
	private JTabbedPane tp;
	
	/** the NumberGame object that handle the current game engine, to apply the changes in the options **/
	private NumberGame gameEngine;
	
	/** the GameBoard object that handle the current game board, to update the game board when there is a change in the options **/
	private GameBoard gameBoard;
	
	/*******************************************************************************************
	 * Constructor for a OptionFrame object that extends JFrame.
	 * Creates a frame with three (there is no limit) tabbed panels that manage
	 * different options:
	 * - The first panel manages the size of the board, using a JSlider
	 * - The second panel manages the winning value for the game, using an editable JComboBox.
	 * - The third panel manages the time limit for the game, using JComboBox not editable.
	 * @param gameEngine the current NumberGame object
	 * @param gameBoard  the current GameBoard object
	 *******************************************************************************************/
	public OptionFrame(NumberGame gameEngine, GameBoard gameBoard){
		this.gameEngine = gameEngine;
		this.gameBoard  = gameBoard;
		
		//Prevent the timer to count the time while the user is occupied with the options
		this.gameBoard.stopTimer();
		
		resizeBoard   = new ResizeSlider(gameEngine.getHeight(), gameEngine.getWidth());
		valueBox 	  = new WinningValueBox(gameEngine.getWinningValue());
		difficultyBox = new DifficultyBox();
			
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		createElements();//Creates the JTabbedPane's panels and the buttons
		setupFrame();//Sets up all the elements in the frame
		
		this.pack();
	    this.setVisible(true);
	    
	    this.setLocationRelativeTo(null);//Pops up the frame in the middle of the screen
	    this.setAlwaysOnTop(true);//A way to focus the user on the new frame and prevent the user from wanting to cheat.
	}
	
	/********************************************************************************
	 * The method sets up the JTabbedPane and add one panel in each tab.
	 * Furthermore, the apply button and the close button are being instantiated,
	 * with a tool tip and an action listener.
	 * The tool tip will show if the mouse cursor is placed on the button.
	 *********************************************************************************/
	private void createElements(){
		
		//Add tabbed panels
		tp = new JTabbedPane();
		tp.addTab("Size", resizeBoard);
		tp.addTab("Winning Value", valueBox);
		tp.addTab("Difficulty", difficultyBox);
		
		//Create the apply button
		applyButton = new JButton("Apply");
		applyButton.setToolTipText("Confirm the changes and return to the game");
		applyButton.addActionListener(new ButtonListener());
		
		//Create the cancel button
		closeButton = new JButton("Close");
		closeButton.setToolTipText("Discharge the changes and return to the game");
		closeButton.addActionListener(new ButtonListener());
	}
	
	/*****************************************************************************
	 * Add the elements instantiated in createElemnts() to the frame.
	 * A GridBagLayout is being used to "force" the tabbed panels to occupy as
	 * much width as two buttons, to give the frame a better look.
	 ****************************************************************************/
	private void setupFrame(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints position = new GridBagConstraints();
		
		//Sets up the position for the JTabbedPane
		position.insets = new Insets(5,5,5,5);
		position.gridy = 0;
		position.gridx = 0;
		position.gridwidth = 2;
		
		//Add the JTabbedPane
		this.getContentPane().add(tp, position);
		
		//Sets up the position for the JButtons and add them to the frame
		position.insets = new Insets(5, 50, 5, 5);
		position.gridwidth = 1;
		position.gridy++;
		this.getContentPane().add(applyButton, position);
		position.gridx++;
		this.getContentPane().add(closeButton, position);
	}
	
	/*******************************************************************************
	 * Called inside the ButtonListener class, but having to refer to "this" frame,
	 * a method inside the OptionFrame class is needed.
	 ******************************************************************************/
	private void createDialog(){
		JOptionPane.showMessageDialog(this, "The value is not a valid power of 2", "Error", JOptionPane.PLAIN_MESSAGE);
	}
	
	/*********************************************************************
	 * ActionListener implementations for the two option frame's buttons
	 * @author  Gionata Bonazzi
	 * @version 21 March 2017
	 ********************************************************************/
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			//Pressing the close button makes the program discharge all the changes.
			if(e.getSource() == closeButton){
				gameBoard.startTimer();
				dispose();
			}
			
			//Pressing the apply button makes the program apply the changes.
			if(e.getSource() == applyButton){
				try{
					gameEngine.resizeBoard(resizeBoard.getBoardHeight(), resizeBoard.getBoardWidth(), valueBox.getWinningValue());
					gameBoard.setTimeLimit(difficultyBox.getNewTime());
					gameBoard.resetGame();
					gameBoard.resizeFrame();
					GameStatistics.LOSSES++;
					GameStatistics.TOTAL++;
					GameStatistics.SLIDES = 0;
					dispose();
				}
				
				//Since the JComboBox for the winning value is editable, the user could insert something that is not valid, throwing an exception.
				//Even if the input is not a number (a string for example), the same exception will be thrown.
				catch(IllegalArgumentException exc){
					createDialog();
					valueBox.setDefaultItem();
					difficultyBox.defaultItem();
				}	
			}
		}	
	}
}
