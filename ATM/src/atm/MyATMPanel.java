package atm;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**********************************************************************
 * A class that build up a graphical user interface to interact with
 * an ATM object.
 * Through the GUI the user can insert money, take them out	and suspend 
 * all ATMs.
 * @author  Gionata Bonazzi
 * @version 3 February 2017
 *********************************************************************/
public class MyATMPanel extends JFrame{
		
	/** Declare the ATM object**/
	private ATM atm;
	
	/** Button to add bills to the ATM **/
	private JButton addBills;
	
	/** Button to subtract bills from the ATM **/
	private JButton subBills;
	
	/** Stop all ATMs **/
	private JButton suspend;
	
	/** Display the currency inside the ATM **/
	private JTextArea atmBills;
	
	/** Input the 100$ bills that will be put in or taken out from the ATM **/
	private JTextField hundredsInput;
	
	/** Input the 50$ bills that will be put in or taken out from the ATM **/
	private JTextField fiftiesInput;
	
	/** Input the 20$ bills that will be put in or taken out from the ATM **/
	private JTextField twentiesInput;
	
	/******************************************************************
	 * The static method invokes three different GUI panels, as per
	 * request of the assignment.
	 * Note that the main does not invoke the constructor once, but
	 * thrice, since each constructor build up an user interface
	 * for just one ATM.
	 * As a last note, is it possible to change the number of
	 * GUI panels invoked by changing the size of the MyATMPanel array.
	 *****************************************************************/
	public static void main(String[] args){		
		MyATMPanel[] atmGui = new MyATMPanel[3];
		
		//instantiate a panel for each element of atmGui
		for(MyATMPanel gui : atmGui){
			gui = new MyATMPanel();
			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
			gui.setTitle("ATM");
			gui.pack();
			gui.setVisible(true);
		}
		
	}
	
	/******************************************************************
	 * Constructor for a GUI with a single ATM.
	 * Three different areas are provided where to input, respectively,
	 * the 100$ bills, the 50$ bills and 20$.
	 * Do not input in the text areas anything except numbers, otherwise
	 * an exception will be thrown.
	 *****************************************************************/
	public MyATMPanel(){
		atm = new ATM();
		
		//creating the GridBag layout
		setLayout(new GridBagLayout());
	    GridBagConstraints position = new GridBagConstraints();
	    
	    //The text area for the ATM
	    atmBills = new JTextArea(10, 25);
		position = makeConstraints(0, 0, 10, 15, GridBagConstraints.LINE_START);
		position.insets =  new Insets(15,15,0,15); 
		atmBills.setText(atm.toString());
		add(atmBills, position);
		atmBills.setEditable(false);
		
		//The button to add bills
		addBills = new JButton("Insert");
		position = new GridBagConstraints();
		position = makeConstraints(20, 0, 0, 0, GridBagConstraints.CENTER);
		position.insets = new Insets(0, 0, 170, 0);
		add(addBills, position);
		addBills.addActionListener(new TimerListener());
		
		//The button to suspend all ATMs
		suspend = new JButton("Suspend");
		position = new GridBagConstraints();
		position = makeConstraints(20, 10, 0, 0, GridBagConstraints.CENTER);
		position.insets = new Insets(20, 0, 0, 0);
		add(suspend, position);
		suspend.addActionListener(new TimerListener());
		
		//The button to take out bills
		subBills = new JButton("Take out");
		position = makeConstraints(20, 0, 0, 0, GridBagConstraints.CENTER);
		position.insets = new Insets(0, 0, 100, 0);
		add(subBills, position);
		subBills.addActionListener(new TimerListener());
		
		//Text field for the user to input the # of 100$ bills
		hundredsInput = new JTextField(5);
		position = new GridBagConstraints();
		position = makeConstraints(0, 10, 0, 0, GridBagConstraints.LINE_START);
		position.insets = new Insets(20, 20, 0, 0);
		add(hundredsInput, position);
		
		//Label for the 100$ text field
		position = new GridBagConstraints();
        position = makeConstraints(0, 10, 0, 0, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,23,15,0);   
        add(new JLabel("Hundred:"), position);
        
        //Text field for the user to input the # of 50$ bills
        fiftiesInput = new JTextField(5);
		position = new GridBagConstraints();
		position = makeConstraints(0, 10, 0, 0, GridBagConstraints.LINE_START);
		position.insets = new Insets(20, 110, 0, 0);
		add(fiftiesInput, position);
		
		//Label for the 50$ text field
		position = new GridBagConstraints();
        position = makeConstraints(0, 10, 0, 0, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,113,15,40);   
        add(new JLabel("Fifties:"), position);
        
        //Text field for the user to input the # of 20$ bills
        twentiesInput = new JTextField(5);
		position = new GridBagConstraints();
		position = makeConstraints(0, 10, 0, 0, GridBagConstraints.LINE_START);
		position.insets = new Insets(20, 200, 0, 0);
		add(twentiesInput, position);
		
		//Label for the 20$ text field
		position = new GridBagConstraints();
        position = makeConstraints(0, 10, 0, 0, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,203,15,40);   
        add(new JLabel("Twenties:"), position);
	}
	   
	/******************************************************************
	 *Create a custom GridBag constraint  
	 *@param  x the distance from the left side
	 *@param  y the distance from the upper side
	 *@param  h the distance from the lower side
	 *@param  w the distance from the right side
	 *@param  align the position of the element, right side, centered, 
	 *        left side.
	 *@return the GridBagConstraints object
     *****************************************************************/    
	private GridBagConstraints makeConstraints(int x, int y, int h, int w, int align){ 
		GridBagConstraints rtn = new GridBagConstraints(); 
		rtn.gridx = x; 
        rtn.gridy = y; 
        rtn.gridheight = h; 
        rtn.gridwidth = w; 
        
        // set alignment: LINE_START, CENTER, LINE_END
        rtn.anchor = align; 
        return rtn; 
    }   
	

/**********************************************************************
 * Private class that handle Action events from the GUI	
 * @author  Gionata Bonazzi
 * @version 3 February 2017
 *********************************************************************/
private class TimerListener implements ActionListener{
	
	/******************************************************************
	 * The method receive an ActionEvent from the ActionListener.
	 * Through the method there will be multiple if blocks, in which
	 * the source of the event will be requested, and an action will
	 * take place. Meaning that, the method will notice not the
	 * event, but the source of the event, and will respond accordingly.
	 *****************************************************************/
	public void actionPerformed(ActionEvent e){
		int hundreds = 0;
		int fifties  = 0;
		int twenties = 0;
		
		//Click on the suspend button
		if(e.getSource() == suspend){
			ATM.suspend(ATM.isRunning());
		}
		
		//Click on the addBills button
		if(e.getSource() == addBills){
			
			//Check if the text areas are empty or not
			if(!hundredsInput.getText().isEmpty()){
				hundreds = Integer.parseInt(hundredsInput.getText());
			}
			if(!fiftiesInput.getText().isEmpty()){
				fifties = Integer.parseInt(fiftiesInput.getText());
			}
			if(!twentiesInput.getText().isEmpty()){
				twenties = Integer.parseInt(twentiesInput.getText());
			}
			
			atm.putIn(hundreds, fifties, twenties);					
		}
		
		//Click on the subBills button
		if(e.getSource() == subBills){
			
			//Check if the text areas are empty or not
			if(!hundredsInput.getText().isEmpty()){
				hundreds = Integer.parseInt(hundredsInput.getText());
			}
			if(!fiftiesInput.getText().isEmpty()){
				fifties = Integer.parseInt(fiftiesInput.getText());
			}
			if(!twentiesInput.getText().isEmpty()){
				twenties = Integer.parseInt(twentiesInput.getText());
			}
			atm.takeOut(hundreds, fifties, twenties);
		}
		
		//Update the text areas to an empty string if the ATMs are running
		if(ATM.isRunning()){
			hundredsInput.setText("");
			fiftiesInput.setText("");
			twentiesInput.setText("");
		}
		
		//At the end, update the textArea
		atmBills.setText(atm.toString());
	}
}
}