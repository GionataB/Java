package edu.gvsu.bonazzig.GUI1024;

import javax.swing.*;

import edu.gvsu.bonazzig.mathematics.Exponential;

/*************************************************************************
 * Creates a JComboBox to choose the new winning value from.
 * @author  Gionata Bonazzi
 * @version 21 March 2017
 ************************************************************************/
public class WinningValueBox extends JPanel{

	/** JComboBox for the winning value **/
	private JComboBox<String> winningBox;
	
	/** JLabel that show an info message **/
	private JLabel infoMessage;
	
	/** Array of possible values for the winning value, in a String form **/
	private String[] possibleValues;
	
	/** The new winning value **/
	private int winningValue;
	
	/*********************************************************************
	 * Creates the Object
	 * @param winningValue the current winning value.
	 ********************************************************************/
	public WinningValueBox(int winningValue){
		this.winningValue = winningValue;
		
		setValues();
		
		winningBox = new JComboBox<String>(possibleValues);
		winningBox.setEditable(true);
		
		setDefaultItem();
		
		infoMessage = new JLabel("Please select a value between 4 and " + Exponential.getPower(30, 2) + ".");
		
		setupPanels();
	}
	
	/*********************************************************************
	 * Sets the default string showed in the JComboBox.
	 ********************************************************************/
	public void setDefaultItem(){
		winningBox.setSelectedItem("" + winningValue);
	}
	
	/*********************************************************************
	 * @return the selected winning value.
	 ********************************************************************/
	public int getWinningValue(){
		return Integer.parseInt((String) winningBox.getSelectedItem());
	}
	
	
	/*********************************************************************
	 * Puts the possible values in an array that will be added to the 
	 * JComboBox.
	 ********************************************************************/
	private void setValues(){
		possibleValues = new String[18];//max. number: 524288. More than that, and the tiles will be too small to render the full number.
		for(int i = 2; i < 20; i++){
			possibleValues[i-2] = "" + Exponential.getPower(i, 2);
		}
	}
	
	/*********************************************************************
	 * Puts the JComboBox and the JLabel inside panels to makes
	 * them look nicer on the GUI.
	 ********************************************************************/
	private void setupPanels(){
		JPanel labelsPanel = new JPanel();
		JPanel boxPanel    = new JPanel();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		labelsPanel.add(infoMessage);
				
		//This prevents the JComboBox area to be larger than expected.
		boxPanel.add(winningBox);
	
		add(labelsPanel);
		add(boxPanel);
	}
	
}
