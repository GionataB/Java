package gui1024;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/******************************************************************************
 * DifficultyBox object that extends JPanel.
 * Creates a window that let the user choose the difficulty of the game.
 * Changing the difficulty, the time limit will change in the following ways:
 *  - Easy: 	 5 minutes to complete the game.
 *  - Normal: 	 2 minutes to complete the game.
 *  - Hard: 	 40 seconds to complete the game.
 *  - Challenge: 30 seconds to complete the game.
 * @author  Gionata Bonazzi
 * @Version 21 March 2017
 *****************************************************************************/
public class DifficultyBox extends JPanel{
	
	/** JComboBox for difficulty levels **/
	private JComboBox<String> difficultyBox;
	
	/** JLabel for the window's message **/
	private JLabel windowMessage;
	
	/** Array of Strings with options to add to the JComboBox **/
	private String[] possibleValues;
	
	/***************************************************************************************
	 * Constructor for a DifficultyBox object.
	 * Instantiate the array, the JLabel and the JComboBox.
	 * Uses the defaultItem() method to set the initial JComboBox's selected item to Normal.
	 * The setupPanels() method sets up all the items in the object, using a panel
	 * for each one in order to get a better look and feel for the window.
	 **************************************************************************************/
	public DifficultyBox(){
		this.possibleValues = new String[] {"Easy", "Normal", "Hard", "Challenge"};		
		this.windowMessage  = new JLabel("Select a difficulty");		
		this.difficultyBox  = new JComboBox<String>(possibleValues);
		
		defaultItem();
		
		setupPanels();
		
	}
	
	/*************************************************************************************
	 * Set the JComboBox's selected item to Normal.
	 ************************************************************************************/
	public void defaultItem(){
		this.difficultyBox.setSelectedItem("Normal");
	}
	
	/************************************************************************************
	 * @return the time limit that corresponds to the selected difficulty.
	 ***********************************************************************************/
	public int getNewTime(){
		switch((String) difficultyBox.getSelectedItem()){//cast as a string, since the general Object is not supported for a switch.
			case "Easy"   : return 300;
			case "Normal" : return 120;
			case "Hard"   : return 40;
			default 	  : return 30;//case "Challenge".
		}
	}
	
	/**************************************************************************************************************************
	 * Helper that sets up the elements in a window with the aid of JPanels, one for each element.
	 * This helps to keep the natural look and feel of a Java Swing component.
	 * It is mostly used because of the JComboBox that has an unexpected behavior if added to the main component by itself.
	 *************************************************************************************************************************/
	private void setupPanels(){
		JPanel labelsPanel = new JPanel();
		JPanel boxPanel    = new JPanel();
		
		labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
		labelsPanel.add(windowMessage);
		
		//This prevents the JComboBox area to be larger than expected.
		boxPanel.add(difficultyBox);
	
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(labelsPanel);
		this.add(boxPanel);
	}	
}
