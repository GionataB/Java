package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/************************************************************************
 * Frame that contains options for the simulation.
 *
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 ***********************************************************************/
public class OptionFrame extends JFrame {
	
	/** JButton to apply all the changes **/
	private JButton applyButton;
	
	/** JButton to discard all the changes **/
	private JButton closeButton;
	
	/** JLabel for eaetries **/
	private JLabel eateryLabel;
	
	/**  JLabel for checkouts **/
	private JLabel checkoutLabel;
	
	/** Input area for eateries **/
	private JTextField eateryField;
	
	/** Input area for checkouts **/
	private JTextField checkoutField;
	
	/** The main JPanel that runs the simulation **/
	private GUISimulation mainPanel;
	
	/********************************************************************
	 * Creates the frame
	 * @param panel the GUISimulation panel that contains the simulation's objects
	 *******************************************************************/
	public OptionFrame(GUISimulation panel){
		this.mainPanel = panel;
		
		setupElements();
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		setupFrame();
		
		this.pack();
	    this.setVisible(true);
	    
	    this.setLocationRelativeTo(null);//Pops up the frame in the middle of the screen
	    this.setAlwaysOnTop(true);
		}
	
	/********************************************************************
	 * add each element in the frame
	 *******************************************************************/
	private void setupFrame(){
		GridBagConstraints position = new GridBagConstraints();
		position.insets = new Insets(0,15,0,15);
		position.gridy = 0;
		position.gridx = 0;
		position.gridwidth = 2;
		this.getContentPane().add(eateryLabel, position);
		position.gridy = 1;
		this.getContentPane().add(eateryField, position);
		position.gridy = 2;
		this.getContentPane().add(checkoutLabel, position);
		position.gridy = 3;
		this.getContentPane().add(checkoutField, position);
		position.gridwidth = 1;
		position.gridy = 4;
		this.getContentPane().add(applyButton, position);
		position.gridx = 1;
		this.getContentPane().add(closeButton, position);
	}
	
	/********************************************************************
	 * Create all the frame's elements and add ActionListeners if needed
	 *******************************************************************/
	private void setupElements(){
		applyButton   = new JButton("Apply");
		closeButton   = new JButton("Close");
		
		applyButton.addActionListener(new ButtonListener());
		closeButton.addActionListener(new ButtonListener());
		
		eateryLabel   = new JLabel("Set the number of eateries");
		checkoutLabel = new JLabel("Set the number of checkouts");
		
		eateryField   = new JTextField(5);
		checkoutField = new JTextField(5);
		
		eateryField.setEditable(true);
		checkoutField.setEditable(true);
		
		eateryField.setText("" + mainPanel.getNumEateries());		
		checkoutField.setText("" + mainPanel.getNumCheckouts());
	}
		
/************************************************************************
 * ButtonListener class, implements ActionListener.
 * Used for the buttons
 ***********************************************************************/
private class ButtonListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeButton){
			dispose();//Close only the option's frame
		}
		if(e.getSource() == applyButton){
			try{
				mainPanel.setEateries(Integer.parseInt(eateryField.getText()));
				mainPanel.setCheckouts(Integer.parseInt(checkoutField.getText()));
				mainPanel.updateOptions();
				dispose();
			}

			catch (NumberFormatException exc){
				mainPanel.inputError();
			}
		}
	}
	
}
}
