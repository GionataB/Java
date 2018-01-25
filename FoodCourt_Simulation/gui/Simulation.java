package gui;

import javax.swing.JFrame;

/************************************************************************
 * Contains the main method for the simulation.
 *
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 ***********************************************************************/
public class Simulation {

	public static void main(String[] args) {
		JFrame frame = new JFrame("sim");
		GUISimulation panel = new GUISimulation();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

}
