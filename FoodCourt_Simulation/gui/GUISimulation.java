package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import foodCourt.*;

import foodCourt.Eatery;

/************************************************************************
 * GUI for a foodCourt simulation.
 *
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 ***********************************************************************/
public class GUISimulation extends JPanel implements ClockListener{

	/** The number of panels as a constant **/
	private final int NUM_PANELS = 4;
	
	/** The output area has the same number of JLabels to the left and to the right, a final is optimal **/
	private final int NUM_OUTPUT_LABELS = 7;
	
	/** Number of eateries in the simulation, default value is 1 **/
	private int numRestaurants = 1;
	
	/** Number of checkouts in the simulation, default value is 2 **/
	private int numCheckouts = 2;//FIXME: keep the people in each checkout balanced, so divide the central line in an equal manner
	
	/** Waiting line between the restaurants and checkouts **/
	private Cashier checkout;
	
	/** The restaurants */
	private Eatery[] restaurants;
	
	
	/** The Person generator **/
	private PersonProducer[] pGenerator;//same size as restaurants.
	
	/** The gui panel will be divided internally in 5 panels **/
	private JPanel[] framePanels;
	
	/** Seven JLabels for the input description **/
	private JLabel[] inputLabels;
	
	/** Five JLabels for the output description **/
	private JLabel[] outputLabels;
	
	/** Five JLabels for the output values. The first one is just a bunch of "-" lines **/
	private JLabel[] outputValues;
	
	/** Six JTextAreas for inputs **/
	private JTextField[] inputAreas;
	
	/** Start the simulation **/
	private JButton start;
	
	/** Quit the simulation **/
	private JButton quit;
	
	/** Open the option frame **/
	private JButton option;
	
	/*********************************************************************
	 * GUISimulation constructor.
	 * It uses multiple helper methods to keep the code clean
	 * and more easy to read by sections.
	 * 
	 * Apart from the helper methods, the last part is comprised of
	 * the code used to position each panel in the GridBagLayout by
	 * the GUISimulation panel.
	 ********************************************************************/
	public GUISimulation(){
		setupArrays();
		setupLayouts();
		setupInputAreas();
		setupInputLabels();
		setupButtons();
		setupOutputLabels();
		setupPanels();
		
		GridBagConstraints position = new GridBagConstraints();
		position.insets = new Insets(5,5,5,5);
		position.gridx = 0;
		position.gridy = 0;
		position.gridwidth = 2;
		this.add(framePanels[0], position);//Upper part, taking two columns	
		position.gridy++;
		this.add(framePanels[1], position);//Middle part, taking two columns
		position.gridwidth = 1;
		position.gridy++;
		this.add(framePanels[2], position);//Lower left corner, taking one row
		position.gridx++;
		this.add(framePanels[3], position);//Lower right corner, taking one row
		this.setVisible(true);	
	}
	
	/********************************************************************
	 * Updates only the labels for the number of eateries and checkouts.
	 * Since it's mainly used by the optionFrame, this prevents errors if 
	 * the first thing the user does after opening the program is to 
	 * change the options. 
	 * 
	 * For example, there is no constructor in the Person class, if
	 * first thing the GUI does is to ask the Person object for a value to
	 * display and that value has not been instantiated yet, an exception will
	 * be thrown, or an unexpected number will be shown
	 * (in the worst case scenario the program will crash).
	 *******************************************************************/
	public void updateOptions(){
		outputValues[1].setText("" + numRestaurants);
		outputValues[2].setText("" + numCheckouts);
		repaint();
	}
	
	/********************************************************************
	 * Display an error message each time an exception is caught.
	 * Since the exceptions thrown by the program are mainly due
	 * an error in the input fields, the message is always the same.
	 *******************************************************************/
	public void inputError(){
		JOptionPane.showMessageDialog(this, "The input is not valid, please try again.");
	}
	
	/*********************************************************************
	 * Initializes the arrays and the objects in the arrays.
	 ********************************************************************/
	private void setupArrays(){
		framePanels  = new JPanel[NUM_PANELS];
		inputLabels  = new JLabel[6];
		outputLabels = new JLabel[NUM_OUTPUT_LABELS];
		outputValues = new JLabel[NUM_OUTPUT_LABELS];
		inputAreas 	 = new JTextField[5];
		
		initializeArray(framePanels);
		initializeArray(inputLabels);
		initializeArray(outputLabels);
		initializeArray(outputValues);
		initializeArray(inputAreas);
	}
	
	/********************************************************************
	 * set the number of unique eateries
	 * @param num the number of eateries
	 *******************************************************************/
	public void setEateries(int num){
		this.numRestaurants = num;
	}
	
	/********************************************************************
	 * set the number of unique checkouts
	 * @param num the number of checkouts
	 *******************************************************************/
	public  void setCheckouts(int num){
		this.numCheckouts = num;
	}
	
	/********************************************************************
	 * @return return the number of unique eateries.
	 *******************************************************************/
	public int getNumEateries(){
		return numRestaurants;
	}
		
	/********************************************************************
	 * @return the number of checkouts.
	 *******************************************************************/
	public int getNumCheckouts(){
		return numCheckouts;
	}
	
	/*********************************************************************
	 * Helper method that sets the layout for each panel, GUISimulation 
	 * included.
	 * 	- GUISimulation uses a GridBagLayout, harder to use but gives 
	 *    extreme freedom to the programmer.
	 *  - the first panel (inputs) uses a GridLayout with 7 rows and 2 
	 *    columns, col 1 has the labels, col 2 has the text fields
	 *  - the second panel (buttons) uses a GridLayout with 1 row and 2 
	 *    columns, could have used a BoxLayout aligned along the X_AXIS,
	 *    either one is fine, since they do the same thing (as long as
	 *    GridLayout's rows == 1)
	 *  - The third panel (output labels, unchanging) uses a BoxLayout
	 *    aligned along the Y_AXIS, could have used a GridLayout with
	 *    1 col, but a BoxLayout helps the programmer to add new labels
	 *    indefinitely in the future, where to use GridLayout the rows and
	 *    columns have to be specified each time.
	 *  - The fourth and last panel (output labels that contain the values
	 *    obtained from the simulation) uses a BoxLayout aligned along the
	 *    Y_AXIS, the argument for the third panel can be applied here
	 *    the same way. 
	 ********************************************************************/
	private void setupLayouts(){
		this.setLayout(new GridBagLayout());
		framePanels[0].setLayout(new GridLayout(7,2));//Inputs section
		framePanels[1].setLayout(new GridLayout(1,3));//Buttons
		framePanels[2].setLayout(new BoxLayout(framePanels[2], BoxLayout.Y_AXIS));//Output labels
		framePanels[3].setLayout(new BoxLayout(framePanels[3], BoxLayout.Y_AXIS));//Output values
	}
	
	/*********************************************************************
	 * Method that sets up the text in each label in the input section.
	 * Straightforwardly enough, the content of the label is the
	 * String parameter of the setText() method, so not much
	 * explanation needed.
	 ********************************************************************/
	private void setupInputLabels(){
		inputLabels[0].setText("Input Information");
		inputLabels[1].setText("Seconds to the Next Person");
		inputLabels[2].setText("Average Seconds per cashier");
		inputLabels[3].setText("Total time in seconds");
		inputLabels[4].setText("Average Seconds per Eatery");
		inputLabels[5].setText("Seconds Before Person leaves");
	}
		
	/*********************************************************************
	 * Method that sets up the text in each label in the output section.
	 * Like setupInputLabels(), the content of each label is the parameter
	 * of the setText() method.
	 * 
	 * As a side note, I decided to use all zeroes for the labels that have
	 * values changing during the simulation.
	 * Instead of zero "null" could be used, however I think that from a
	 * user's point of view looking at a bunch of zeros is better than a
	 * bunch of "null" that could point at some problem in the program
	 * (not true, but could give that impression).
	 ********************************************************************/
	private void setupOutputLabels(){
		
		//Left part of the GUI
		outputLabels[0].setText("Output information");
		outputLabels[1].setText("Number of eateries:");
		outputLabels[2].setText("Number of checkouts:");
		outputLabels[3].setText("Throughput");
		outputLabels[4].setText("Average time for a Person from start to finish:");
		outputLabels[5].setText("Number of people left in line");
		outputLabels[6].setText("Max Q length cashier line");
		
		//Right part of the GUI
		outputValues[0].setText("------------------------------...");
		outputValues[1].setText("" + numRestaurants);
		outputValues[2].setText("" + numCheckouts);
		outputValues[3].setText("0 people with Max = 0");
		outputValues[4].setText("0 seconds");
		outputValues[5].setText("0 people");
		outputValues[6].setText("0");
	}
	
	/*********************************************************************
	 * Creates the two JButtons and adds a ButtonListener to each one. 
	 ********************************************************************/
	private void setupButtons(){
		start  = new JButton("Start Simulation");
		option = new JButton("Options...");
		quit   = new JButton("Quit Simulation");
		
		start.addActionListener(new ButtonListener());
		option.addActionListener(new ButtonListener());
		quit.addActionListener(new ButtonListener());
	}
	
	/*********************************************************************
	 * set the JTextFields to editable and accept inputs
	 ********************************************************************/
	private void setupInputAreas(){
		for(int i = 0; i < inputAreas.length; i++){
			inputAreas[i].setEditable(true);
		}
	}
	
	/********************************************************************
	 * Add the elements inside the corresponding panel.
	 * Each panel is used to encapsulate the elements before
	 * adding them to the frame.
	 *******************************************************************/
	private void setupPanels(){
		framePanels[0].add(inputLabels[0]);
		framePanels[0].add(new JLabel("---------------------------------..."));
		for(int i = 1; i < inputLabels.length; i++){
			framePanels[0].add(inputLabels[i]);
			framePanels[0].add(inputAreas[i-1]);
		}
		framePanels[1].add(start);
		framePanels[1].add(option);
		framePanels[1].add(quit);
		addElements(outputLabels, framePanels[2]);
		addElements(outputValues, framePanels[3]);
	}
	
	
	/*********************************************************************
	 * Generic method that takes an array of generic elements extending
	 * JComponent and add all of them to the desired panel.
	 * @param array array of elements extending JComponent
	 * @param panel the JPanel to add the elements to.
	 ********************************************************************/
	private <T extends JComponent> void addElements(T[] array, JPanel panel){
		for(int i = 0; i < array.length; i++){
			panel.add((JComponent) array[i]);
		}
	}
	
	/*********************************************************************
	 * Overloaded method, initialize the elements of a JPanel array
	 * @param array the array of JPanels to initialize
	 ********************************************************************/
	private void initializeArray(JPanel[] array){
		for(int i = 0; i < array.length; i++){
			array[i] = new JPanel();
		}
	}
	
	/********************************************************************
	 * Overloaded method, initialize the elements of a JLabel array
	 * @param array the array of JLabels to initialize
	 ********************************************************************/
	private void initializeArray(JLabel[] array){
		for(int i = 0; i < array.length; i++){
			array[i] = new JLabel();
		}
	}
	
	/*********************************************************************
	 * Overloaded method, initialize the elements of a JTextField array
	 * @param array the array of JTextFields to initialize
	 ********************************************************************/
	private void initializeArray(JTextField[] array){
		for(int i = 0; i < array.length; i++){
			array[i] = new JTextField();
		}
	}
	
	/*********************************************************************
	 * Overloaded method, initialize the elements of a Eatery array
	 * @param array the array of Eatery to initialize
	 ********************************************************************/
	private void initializeArray(Eatery[] array){
		for(int i = 0; i < array.length; i++){
			array[i] = new Eatery();
		}
	}
	
	/********************************************************************
	 * Initialize a PersonProduce array, each object is assigned
	 * to a different eatery, and is given the parameters from the
	 * JTextField's inputs.
	 * 
	 * @param array the array to initialize
	 * @param eatery the array of eateries
	 * @param clock the master clock
	 *******************************************************************/
	private void initializeArray(PersonProducer[] array, Eatery[] eatery, Clock clock){
		int nextPerson = Integer.parseInt(inputAreas[0].getText());
		int avrgEatTime = Integer.parseInt(inputAreas[3].getText());
		int avrgCashTime = Integer.parseInt(inputAreas[1].getText());
		int leavingTime = Integer.parseInt(inputAreas[4].getText());
		for(int i = 0; i < array.length; i++){
			array[i] = new PersonProducer(eatery[i], checkout, clock, nextPerson, avrgEatTime, avrgCashTime, leavingTime);
		}
	}
	
	/********************************************************************
	 * Create the option frame when the button is pressed
	 *******************************************************************/
	private void createFrame(){
		OptionFrame frame = new OptionFrame(this);
	}

	/********************************************************************
	 * Update the output area with the current values.
	 * As it is, the simulation is too fast to see the values
	 * changing with time, however using this method makes the gui
	 * usable with a different simulation that uses real time.
	 *******************************************************************/
	private void update(){		
		int totalCompleted = checkout.getThroughPut();
		int maxCompleted = 0;
		for(int i = 0; i < restaurants.length; i++){
			if(restaurants[i].getThroughPut() > maxCompleted){
				maxCompleted = restaurants[i].getThroughPut();
			}
			totalCompleted += restaurants[i].getThroughPut();
		}
		outputValues[3].setText("" + totalCompleted + " people with Max = " + maxCompleted);
		updateAverage();
		outputValues[5].setText("" + checkout.getLeft() + " people.");	
		outputValues[6].setText("" + checkout.getMaxQlength());
		
		updateOptions();
		repaint();
	}
	
	/********************************************************************
	 * Update only the average time
	 *******************************************************************/
	private void updateAverage(){
		outputValues[4].setText("" + checkout.getAverageTime() + " seconds");
	}
	
	public void event(int tick){
		update();
	}
	
	/********************************************************************
	 * Add every ClockListener to the Clock.
	 * @param clock the master clock
	 *******************************************************************/
	private void addListeners(Clock clock){
		clock.add(this);
		clock.add(checkout);
		for(int i = 0; i < pGenerator.length; i++){
			clock.add(pGenerator[i]);
			clock.add(restaurants[i]);
		}
	}

/************************************************************************
 * Private listener for the JButtons, implements ActionListener.
 ***********************************************************************/
private class ButtonListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == quit){
			System.exit(1);//exit the program with a "non-default" integer (1), meaning an abruptly closure from the user.
		}
		if(e.getSource() == option){
			createFrame();
		}
		if(e.getSource() == start){
			try{
				Clock simulation = new Clock();
				checkout = new Cashier(numCheckouts);
				restaurants = new Eatery[numRestaurants];
				pGenerator = new PersonProducer[numRestaurants];
				
				initializeArray(restaurants);
				initializeArray(pGenerator, restaurants, simulation);
				addListeners(simulation);
				simulation.run(Integer.parseInt(inputAreas[2].getText()));
			}
			catch(NumberFormatException exc){
				inputError();
			}
			
		}
	}
}
}
