package edu.gvsu.bonazzig.GUI1024;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import edu.gvsu.bonazzig.game1024.*;

/*********************************************************************************************
 * The class creates an Object that extends JPanel and is the graphical representation
 * of the game engine. In short, this class is the "core" of the GUI, the main component
 * that handle the other classes or the component the other classes refer to.
 * 
 * Vital for the class are the BoardTile tiles, the NumberGame engine and the SlideListener,
 * without those three, the game won't work. 
 * 
 * The rest are extra requirements like fading in/out effects,
 * time limit for the game, statistics for the session and transition effects.
 * @author  Gionata Bonazzi
 * @version 21 March 2017
 *
 ********************************************************************************************/
public class GameBoard extends JPanel{
	
	/** The initial time limit **/
	private final int DEFAULT_TIME_LIMIT = 120;
	
	/** the time that pass between each update of the statistics' stop-watch.**/
	private final int STOPWATCH_DELAY = 1000;//in milliseconds (seconds= milliseconds / 1000)
	
	/** The time that the transition takes to complete **/
	private final int TRANSITION_DELAY = 700;//in milliseconds (seconds = milliseconds / 1000)
	
	/** Constant for the delay of the fading's timer: the total time that (fading out + fading in) takes is (255*2/DELAY), where 255 is the maximum value for the RGB scale **/
	private final int FADING_DELAY = 1;//in milliseconds (seconds = milliseconds / 1000)
	
	/** Array of BoardTile objects, it contain the java swing components used for rendering the board **/
	private BoardTile[][] tiles;
	
	/** The game's engine **/
	private NumberGame gameEngine;
	
	/** The address to the game's statistics area **/
	private GameStatistics gameStats;
	
	/** The JFrame container for the GUI**/
	private JFrame frame;
	
	/** The time limit to complete a single game, in seconds. Default value is 60 **/
	private int timeLimit;
	
	/** Not related to the system clock,  it is used to measure the total number of ActionEvent sent from the gameTimer in a single game. The update time for the variable is equivalent to the value of the STOPWATCH_DELAY constant **/
	private int timePassed;
	
	/** Timer object for the game **/
	private Timer gameTimer;
	
	/** Timer object to aid the transition **/
	private Timer transitionTimer;
	
	/** Timer object used to make the fading effect slow enough to be visible */
	private Timer fadingTimer;
	
	/** When in transition, prevent the SlideListener's KeyPressed() method to be executed. However, the keyboard buff won't be flushed, meaning that if directional keys get pressed while the board is transitioning, at the end all the keys will be processed in a fast pace, resulting in multiple slides instead of one. **/
	private boolean transition;
	
	/** Boolean that dictates what kind of transition has to be done (in/out) **/
	private boolean fadingOut;
	
	/** Boolean that is used to make the fading/shading effects work properly **/
	private boolean resetShade;
	
	
	/*********************************************************************
	 * Constructor for a GameBoard object, it sets up the layout, 
	 * the KeyListener, initializes variables, sets the timers, 
	 * and the size for the frame.
	 * Lastly, set the borders for the panel. 
	 * @param game
	 * @param stats
	 * @param frame The frame component that contains this element
	 ********************************************************************/
	public GameBoard(NumberGame game, GameStatistics stats, JFrame frame){
		this.gameEngine = game;
		this.gameStats 	= stats;
		this.frame 		= frame;
		this.transition = false;
		this.fadingOut 	= true;
		this.resetShade = true;
		
		this.setLayout(new GridBagLayout());
		this.addKeyListener(new SlideListener());
		
		initializeTiles();
		renderBoard();
		setTimer();
		
		frame.setPreferredSize(new Dimension(450, 300));
		frame.setMinimumSize(new Dimension(450, 300));
		
		
		
		this.setFocusable(true); //make the panel focusable
		this.setBorder(BorderFactory.createLoweredBevelBorder()); //gives the feeling that the position of the board is slightly deeper than the rest of the frame.
	}
	
	/*********************************************************************
	 * Restart the game, resetting the GUI (board + stats) and 
	 * the game engine.
	 ********************************************************************/
	public void resetGame(){
		transition = false;
		gameEngine.reset();
		resetBoard();
		resetTimer();
	}
	
	
	/*********************************************************************
	 * Changes the size of the main frame, depending on the size of the 
	 * board.
	 * Since the resizing also call for the setMinimumSize(), to avoid 
	 * problems do not use a game board larger than nine rows or column, 
	 * or both, on a screen with a resolution lower than 1000x800.
	 ********************************************************************/
	public void resizeFrame(){
		
		//Rows or columns wider than 9
		if(tiles.length > 9 || tiles[0].length > 9){
			frame.setSize(1000, 800);
			frame.setMinimumSize(new Dimension(1000, 800));
		}
		
		//Rows or columns wider than 6
		else if(tiles.length > 6 || tiles[0].length > 6){
			frame.setSize(750, 600);
			frame.setMinimumSize(new Dimension(750, 600));
		}
		
		//Rows or columns wider than 4
		else if(tiles.length > 4 || tiles[0].length > 4){
			frame.setSize(550, 400);
			frame.setMinimumSize(new Dimension(550, 400));
		}
		
		frame.setLocationRelativeTo(null);//After resizing, reposition the frame at the screen's center
		frame.repaint();
	}
	
	/*********************************************************************
	 * Update the board on the screen.
	 ********************************************************************/
	public void renderBoard(){
		placeDots();
		placeTiles();
		
		//Uses GridBagLayout for an easier positioning of each element
		GridBagConstraints position = new GridBagConstraints();
		position.gridy = 1;
		for(int i = 0; i < tiles.length; i++){
			position.gridx = 1;
			for(int j = 0; j < tiles[i].length;j++){
				position.gridx++;
				add(tiles[i][j], position);
			}
			position.gridy++;
		}
		
		//Update stats
		gameStats.update();
		this.repaint();
	}
	
	/*********************************************************************
	 * Change the game's time limit.
	 * @param time the new time limit.
	 ********************************************************************/
	public void setTimeLimit(int time){
		this.timeLimit = time;
	}
	
	/*********************************************************************
	 * Stop the timer for the game, this could be useful if the user wants 
	 * to access the options without the timer still going.
	 ********************************************************************/
	public void stopTimer(){
		gameTimer.stop();
	}
	
	/*********************************************************************
	 * Start the timer for the game. Used for the same situation as 
	 * stopTimer(). 
	 ********************************************************************/
	public void startTimer(){
		gameTimer.start();
	}
	
	/*********************************************************************
	 * Update the stop watch on the statistics area.
	 * The standard color for the stop watch is black, however it changes 
	 * to red in the case that there are less than ten seconds left.
	 ********************************************************************/
	private void getStopWatch(){
		
		//less that ten seconds left
		if((timeLimit - timePassed) < 10){
			gameStats.renderTimer(timePassed, Color.RED);
		}
		else{
			gameStats.renderTimer(timePassed, Color.BLACK);
		}
	}
	
	/*********************************************************************
	 * The method removes every single tile from the old gameBoard, 
	 * initializes new tiles and update the board area on the GUI.
	 * The call to resetBoard() is preferred to renderBoard() when the 
	 * size of the game board has to change.
	 ********************************************************************/
	private void resetBoard(){
		
		//Remove old tiles
		for(int i = 0; i < tiles.length; i++){
			for(int j = 0; j < tiles[i].length; j++){
				remove(tiles[i][j]);
			}
		}
		
		//Initialize new tiles
		initializeTiles();
		
		//Place new tiles
		renderBoard();
	}
	
	/*********************************************************************
	 * stop and reset the game timer, update the stop watch in the 
	 * statistics area and restart the timer.
	 ********************************************************************/
	private void resetTimer(){
		gameTimer.stop();
		timePassed = 0;
		getStopWatch();
		gameTimer.start();
	}
	
	/*********************************************************************
	 * Constructor's helper. It's role is to set up all the timers, and 
	 * start only the game's timer.
	 ********************************************************************/
	private void setTimer(){
		
		//Initialize variables
		this.timeLimit  = DEFAULT_TIME_LIMIT;
		this.timePassed = 0;
		
		//Initialize timer objects
		gameTimer 		= new Timer(STOPWATCH_DELAY, new TimerListener());
		transitionTimer = new Timer(0, new TransitionListener());//0 as initial delay because the transition has to start as soon as the user presses a valid key.
		fadingTimer 	= new Timer(FADING_DELAY, new FadingListener());
		
		//Start the timer
		gameTimer.start();	
	}
	
	/*********************************************************************
	 * last actions taken before ending the game and starting a new one.
	 * @param message The message to be displayed by the end game's JDialog.
	 ********************************************************************/
	private void endGame(String message){
		
		//Stop the timer and update the statistics.
		gameTimer.stop();
		getStopWatch();
		gameStats.update();
		
		//Show JDialog
		JOptionPane.showMessageDialog(frame, message, "Game Over", JOptionPane.PLAIN_MESSAGE);
        
		//Block executed after the user presses the JDialog's button.
		
		//Update new game's statistics
		GameStatistics.TOTAL++;
        GameStatistics.SLIDES = 0;
        
        //Finally, restart the game.
        resetGame();
	}
	
	/*********************************************************************
	 * Place a dot inside each tile, updating all the tiles.
	 ********************************************************************/
	private void placeDots(){
		for(int i = 0; i < tiles.length; i++){
			for(int j = 0; j < tiles[i].length; j++){
				tiles[i][j].setString(".");
			}
		}
	}
	
	/*********************************************************************
	 * Place a new value in the tiles that are not empty.
	 * This usually has to be called after placeDots(), since
	 * it won't update tiles that don't have a value anymore.
	 ********************************************************************/
	private void placeTiles(){
		for(Cell c: gameEngine.getNonEmptyTiles()){
			tiles[c.row][c.column].setString("" + c.value);
		}
	}
	
	/*********************************************************************
	 * Step 2.4 of the project description. Place a random value 
	 * between 0 and 9 in each tile.
	 * Not used.
	 ********************************************************************/
	private void placeRandom(){
		Random rng = new Random();
		
		//Iterates the array
		for(int i = 0; i < tiles.length; i++){
			for(int j = 0; j < tiles[i].length; j++){
				tiles[i][j].setString("" + rng.nextInt(10));//Values between 0 and 9
			}
		}
	}
	
	/*********************************************************************
	 * Initialize the tiles' array and each tile inside.
	 ********************************************************************/
	private void initializeTiles(){
		tiles = new BoardTile[gameEngine.getHeight()][gameEngine.getWidth()];
		for(int i = 0; i < tiles.length; i++){
			for(int j = 0; j < tiles[i].length; j++){
				tiles[i][j] = new BoardTile(gameEngine.getWinningValue());
			}
		}
	}
	
	/*********************************************************************
	 * Color all the tiles using a RGB value.
	 * The "trick" behind the fading is that the fadeEffect() gets called 
	 * once every millisecond, with a different RGB value.
	 * Ideally, it takes the method 255 milliseconds to go from white to 
	 * black, and another 255 milliseconds to go from black to white.
	 * An update every millisecond could be too fast to notice, but
	 * making it longer collides with the fact that the game has a time
	 * limit, and the user don't want to wait 1-2 seconds for an animation
	 * to finish.
	 * 
	 * Side note: 
	 *  - RGB values (0, 0, 0) correspond to a black color
	 *  - RGB values (255, 255, 255) corresponds to a white color
	 *  - Values of Red, Green and Blue that are equivalent correspond
	 *    to a shade of grey, brighter the more it gets closer to 255.
	 ********************************************************************/
	private void fadeEffect(int RGBval){
		Color color = new Color(RGBval, RGBval, RGBval);
		for(int i = 0; i < tiles.length; i++){
			for(int j = 0; j < tiles[i].length; j++){
				tiles[i][j].setColor(color);
			}
		}
	}
	
	/*********************************************************************
	 * Set the tiles' color to black.
	 * Used when the program has to make sure that, even if the fading in
	 * is not over, the tiles go back to black to be perfectly visible.
	 ********************************************************************/
	private void setBlack(){
		fadeEffect(0);//call fadeEffect() with an RGB value of zero.
	}
	
	/*********************************************************************
	 * KeyListener implementation for the board slides.
	 * @author  Gionata Bonazzi
	 * @version 21 March 2017
	 ********************************************************************/
	private class SlideListener implements KeyListener{
		
		public void keyPressed(KeyEvent e) {
			
			//The game is in progress and not in transition
			//However, even if the game is in transition and won't respond to a KeyEvent
			//The keyboard buffer won't be flushed, meaning that all the actions in the buffer
			//will be executed once the transition is over.
			if(gameEngine.getStatus() == GameStatus.IN_PROGRESS && !transition){
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP://Direction key up
						if(gameEngine.slide(SlideDirection.UP))//If the board does not change, SLIDES won't increase.
							GameStatistics.SLIDES++;
						transition = true;
						break;
					case KeyEvent.VK_RIGHT://Direction key right
						if(gameEngine.slide(SlideDirection.RIGHT))
							GameStatistics.SLIDES++;
						transition = true;
						break;
					case KeyEvent.VK_DOWN://Direction key down
						if(gameEngine.slide(SlideDirection.DOWN))
							GameStatistics.SLIDES++;
						transition = true;
						break;
					case KeyEvent.VK_LEFT://Direction key left
						if(gameEngine.slide(SlideDirection.LEFT))
							GameStatistics.SLIDES++;
						transition = true;
						break;
				}
				
				transitionTimer.start();//Start the transition	
			}
			
			//not using an else-if block so the board will slide and check if the last slide made the user win the game, or lose it.
			if(gameEngine.getStatus() != GameStatus.IN_PROGRESS){
				String message;
				
				//First, check if the user won the game.
				if(gameEngine.getStatus() == GameStatus.USER_WON){
					GameStatistics.WINS++;
					message = "You have won the game!";
				}
				
				else{
					GameStatistics.LOSSES++;
					message = "You have lost the game!";			
				}
				endGame(message);
			}
		}

		public void keyReleased(KeyEvent e) {
		}
		
		public void keyTyped(KeyEvent e) {	
		}	
	}
	
	/*********************************************************************
	 * ActionListener implementation for the game's timer
	 * @author  Gionata Bonazzi
	 * @version 21 March 2017
	 ********************************************************************/
	private class TimerListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			timePassed++;
			getStopWatch();
			
			//Reached the time limit
			if(timePassed == timeLimit){
				GameStatistics.LOSSES++;
				String message = "The time has expired, try again!";
				endGame(message);			
			}
		}
	}
	
	/*********************************************************************
	 * ActtionListener implementation for the transition's timer.
	 * The transition is made up of three different board states:
	 *  - Initial state: the board before the slide.
	 *  - Middle state: A board full of dots with no values in it.
	 *  - Final state: the board after the slide.
	 * @author  Gionata Bonazzi
	 * @version 21 March 2017
	 ********************************************************************/
	private class TransitionListener implements ActionListener{
			
		public void actionPerformed(ActionEvent e){
			
			//Starts the transition.
			if(transition){
				transitionTimer.setDelay(TRANSITION_DELAY);
				fadingOut  = true;
				resetShade = true;
				placeDots();
				fadingTimer.start();
				transition = false;
			}
			
			else{
				renderBoard();
				fadingTimer.stop();
				transitionTimer.stop();
				transitionTimer.setDelay(0);
				setBlack();
			}
		}
	}
	
	/*********************************************************************
	 * ActionListener implementation for the fading effects.
	 * Note that, with a shading every millisecond, the effect will
	 * be too fast to notice most of the time.
	 * I found that a transition delay of 1400 and a fading delay of 5
	 * shows porperly the effect, but do not show a full fading in + out 
	 * cycle.
	 * @author  Gionata Bonazzi
	 * @version 21 March 2017
	 ********************************************************************/
	private class FadingListener implements ActionListener{
		
		/** The RGB value **/
		private int shade;
		
		/** Tells the program if it is the first shading occurrence **/
		private boolean first;
			
		/*****************************************************************
		 * Constructor for the class, sets the boolean "first" to true,
		 * meaning that the listener's code has yet to occur once.
		 * This is used to better render the fading effect.
		 ****************************************************************/
		public FadingListener(){
			super();
			if(fadingOut){//It wont be necessary since the effect should start with a fading out effect.
				shade = 0;
			}
			else{
				shade = 255;
			}
			first = true;
		}
		
		public void actionPerformed(ActionEvent e) {
			reStart();
			checkShade();
			if(fadingOut){
				shade++;
			}
			else{
				shade--;
			}
			fadeEffect(shade);
			
		}
		
		/*****************************************************************
		 * Change the fading from fading in to fading out, or vice-versa.
		 ****************************************************************/
		private void reStart(){
			if(resetShade){
				if(fadingOut){
					shade = 0;
				}
				else{
					shade = 255;
				}
				resetShade = false;
			}
		}
		
		/*****************************************************************
		 * Reverse the fading effect
		 ****************************************************************/
		private void checkShade(){
			if(shade == 255){
				first = false;
				fadingOut = false;
			}
			else if(shade == 0){
				if(first){
					fadingOut = true;
				}
				else{
					shade = 1;//Don't go back to fade out if there is still time left in the transition.
				}
			}
		}
	}
}
