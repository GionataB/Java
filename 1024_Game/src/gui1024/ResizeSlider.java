package gui1024;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import game1024.NumberGame;

/*************************************************************************
 * The slider to resize the game board.
 * @author  Gionata Bonazzi
 * @version 21 March 2017
 ************************************************************************/
public class ResizeSlider extends JPanel{
	
	/** JSlider for the height (rows) **/
	private JSlider heightSlider;
	
	/** JSLider for the width (columns) **/
	private JSlider widthSlider;
	
	/** Shows the initial height **/
	private JLabel oldHeight;
	
	/** Shows the initial width **/
	private JLabel oldWidth;
	
	/** Shows the selected height **/
	private JLabel newHeight;
	
	/** Shows the selected width **/
	private JLabel newWidth;
	
	/** The selected height **/
	private int heightValue;
	
	/** The selected width **/
	private int widthValue;
	
	/*********************************************************************
	 * Creates a ResizeSlider object
	 * @param height the current height
	 * @param width the current width
	 ********************************************************************/
	public ResizeSlider(int height, int width){
		this.heightValue = height;	
		this.widthValue  = width;
		
		heightSlider = new JSlider(JSlider.HORIZONTAL, 4, 12, heightValue);//12 is the maximum value. Otherwise, the board becomes too big for the screen.
		widthSlider  = new JSlider(JSlider.HORIZONTAL, 4, 12, widthValue);
		
		oldHeight = new JLabel("Current height: " + heightValue);
		newHeight = new JLabel("New Height: " + heightValue);
		oldWidth  = new JLabel("Current width: " + widthValue);
		newWidth  = new JLabel("New width: " + widthValue);
		
		setSlider(heightSlider);
		setSlider(widthSlider);
		

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(oldHeight);
		add(newHeight);
		add(heightSlider);
		add(oldWidth);
		add(newWidth);
		add(widthSlider);
	}
	
	/*********************************************************************
	 * @return the selected height
	 ********************************************************************/
	public int getBoardHeight(){
		return heightValue;
	}
	
	/*********************************************************************
	 * @return the selected width
	 ********************************************************************/
	public int getBoardWidth(){
		return widthValue;
	}
	
	/*********************************************************************
	 * Sets up the sliders, with a major tick spacing, a minor one,
	 * makes them visible and add the ChangeListener
	 * @param sizeSlider
	 ********************************************************************/
	private void setSlider(JSlider sizeSlider){
		sizeSlider.setMajorTickSpacing(4);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.addChangeListener(new SliderListener());
	}
	
	/*********************************************************************
	 * ChangeListener implementation for the sliders
	 * @author Gionata
	 *
	 ********************************************************************/
	private class SliderListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			heightValue = heightSlider.getValue();
			widthValue  = widthSlider.getValue();
			
			newHeight.setText("New height: " + heightValue);
			newWidth.setText( "New width: " + widthValue);
		}
		
	}
}
