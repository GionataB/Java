package gui1024;

import java.awt.*;
import javax.swing.*;

/*************************************************************************
 * GameStatistics object extending a JPanel.
 * A space to display the game's statistics.
 * Each statistics is made up of two JPanels, one that contains a JLabel
 * with a description that won't change, the other with the statistics
 * numeric value, constantly updated.
 * Using two JPanels for each line, meaning for each statistics, makes
 * possible to create the look and feel of an organized table.
 * @author  Gionata Bonazzi
 * @version 21 March 2017
 ************************************************************************/
public class GameStatistics extends JPanel {

	/** Constant for the arrays dimension **/
	private final int NUM_LABELS = 7;
	
	/** JPanels that contain the statistics' description **/
	private JPanel[] strings;
	
	/** JPanels that contain the real, numeric, value of the statistics **/
	private JPanel[] values;
	
	/** Label for the number of wins **/
	private JLabel userWins;
	
	/** Label for the  number of plays in the session **/
	private JLabel totalPlays;
	
	/** Label for the winning/total percentage **/
	private JLabel userWinningPercentage;
	
	/** Label for the number of losses **/
	private JLabel userLosses;

	/** Label for the number of slides **/
	private JLabel numSlides;
	
	/** Label for the number of undo used **/
	private JLabel undoUsed;
	
	/** Label for the stop-watch **/
	private JLabel timerClock;
	
	/** Numeric value for the number of wins **/
	public static int WINS;
	
	/** Numeric value for the number of total plays **/
	public static int TOTAL;
	
	/** Numeric value for the number of slides **/
	public static int SLIDES;
	
	/** Numeric value for the number of losses **/
	public static int LOSSES;
	
	/** Numeric value for the number of undo used **/
	public static int UNDO;
	
	/*********************************************************************
	 * Creates the Statistics area.
	 ********************************************************************/
	public GameStatistics(){
		this.strings = new JPanel[NUM_LABELS];
		this.values  = new JPanel[NUM_LABELS];
		
		//Access a static variable with a static call
		GameStatistics.WINS   = 0;
		GameStatistics.TOTAL  = 1;
		GameStatistics.SLIDES = 0;
		GameStatistics.LOSSES = 0;
		GameStatistics.UNDO   = 0;
		
		initializePanels();
		
		this.setLayout(new GridBagLayout());
		
		initializeLabels();
		setLabels();
		renderStats();
		update();
		
		this.setFocusable(false);
		this.setPreferredSize(new Dimension (220, 400));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setVisible(true);
		}
		
	/*********************************************************************
	 * Update all the statistics labels.
	 ********************************************************************/
	public void update(){
		this.numSlides.setText( "" + GameStatistics.SLIDES);
		this.undoUsed.setText(  "" + GameStatistics.UNDO);
		this.userWins.setText(  "" + GameStatistics.WINS);
		this.userLosses.setText("" + GameStatistics.LOSSES);
		this.totalPlays.setText("" + GameStatistics.TOTAL);
		
		int percentile;
		
		//prevent a division by zero. Faster than using a try/catch block
		if(GameStatistics.TOTAL == 0){
			percentile = 0;
		}
		else{
			percentile =(int)((double)GameStatistics.WINS/(GameStatistics.WINS + GameStatistics.LOSSES)*100);
		}
		
		//Red numbers if the percentile is less than 50, green otherwise.
		if(percentile < 50){
			this.userWinningPercentage.setForeground(Color.RED);
		}
		else{
			this.userWinningPercentage.setForeground(Color.GREEN);
		}
		
		this.userWinningPercentage.setText("" + percentile + "%" );
		repaint();
	}
	
	/*********************************************************************
	 * Updates the stop-watch.
	 * It takes the number of seconds elapsed and sets up a "real"
	 * presentation of a clock, with minutes and seconds.
	 * @param time  time elapsed in seconds
	 * @param color the foreground color for the clock's numbers
	 ********************************************************************/
	public void renderTimer(int time, Color color){
		int minutes = time / 60;
		int seconds = time % 60;
		
		String stopWatch;
		
		//Creates the minutes' space
		if(minutes > 0){
			stopWatch = "" + minutes + " : ";
		}
		else{
			stopWatch = "0 : ";
		}
		
		//put a zero before the seconds if they are less than ten to keep the format X:0X.
		if(seconds < 10){
			stopWatch += "0";
		}
		
		stopWatch += seconds;
		
		timerClock.setText("" + stopWatch);	
		timerClock.setForeground(color);
	}
	
	/*********************************************************************
	 *	Sets up the labels inside the JPanels
	 ********************************************************************/
	private void setLabels(){
		GridBagConstraints position = new GridBagConstraints();
		GridBagConstraints start    = new GridBagConstraints();
		
		start.anchor   = GridBagConstraints.LINE_START;
		position.gridx = 0;
		
		strings[0].add(new JLabel("Time elapsed:"), start);
		strings[1].add(new JLabel( "Slides:"), start);
		strings[2].add(new JLabel("Undo used:"), start);
		strings[3].add(new JLabel("Wins:"), start);
		strings[4].add(new JLabel("Losses:"), start);
		strings[5].add(new JLabel("Total:"), start);
		strings[6].add(new JLabel("Winning percentage:"), start);
		
		for(int i = 0; i < strings.length; i++){
			position.gridy = i;
			this.add(strings[i], position);
		}
	}
	
	/*********************************************************************
	 * Sets up the statistics' labels text for the first time
	 ********************************************************************/
	private void renderStats(){
		GridBagConstraints position = new GridBagConstraints();
		GridBagConstraints center = new GridBagConstraints();
		center.anchor  = GridBagConstraints.CENTER;
		position.gridy = 0;
		position.gridx = 1;
		
		addElement(timerClock, position, center, 0);
		addElement(numSlides, position, center, 1);
		addElement(undoUsed, position, center, 2);
		addElement(userWins, position, center, 3);
		addElement(userLosses, position, center, 4);
		addElement(totalPlays, position, center, 5);
		addElement(userWinningPercentage, position, center, 6);
	}
	
	/*********************************************************************
	 * Helper to place the JLabel in the panel and then the label in the
	 * GameStatistics object.
	 * @param element the JLabel to add
	 * @param pos the JPanel's position
	 * @param ctr the JLabel's position (used only to center the label in the panel)
	 * @param num the index of the array,
	 ********************************************************************/
	private void addElement(JLabel element, GridBagConstraints pos, GridBagConstraints ctr, int num){
		values[num].add(element, ctr);
		this.add(values[num], pos);
		pos.gridy++;
	}
	
	/*********************************************************************
	 * Initialize the Statistic's labels.
	 ********************************************************************/
	private void initializeLabels(){
		userWins 			  = new JLabel();
		totalPlays 			  = new JLabel();
		userWinningPercentage = new JLabel();
		userLosses			  = new JLabel();
		numSlides			  = new JLabel();
		undoUsed			  = new JLabel();
		timerClock			  = new JLabel();
	}
	
	/*********************************************************************
	 * Initialize all the JPanel's object for the first time.
	 ********************************************************************/
	private void initializePanels(){
		for(int i = 0; i < NUM_LABELS; i++){
			strings[i] = new JPanel();
			strings[i].setLayout(new GridBagLayout());
			strings[i].setPreferredSize(new Dimension(150, 25));
			strings[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			strings[i].setBackground(Color.WHITE);
			
			values[i] = new JPanel();
			values[i].setLayout(new GridBagLayout());
			values[i].setPreferredSize(new Dimension(60, 25));
			values[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			values[i].setBackground(Color.WHITE);
		}
	}
}