package edu.gvsu.bonazzig.GUI1024;

import java.awt.*;

import javax.swing.*;

/*************************************************************************
 * Class that creates a single tile extending JPanel.
 * A tile is a single cell in the main game.
 * @author  Gionata Bonazzi
 * @version 21 March 2017
 ************************************************************************/
public class BoardTile extends JPanel{
	
	/** The standard dimension for the tile **/
	private final int PANEL_DIMENSION = 50;
	
	/** The label that will contain the numeric value for the tile **/
	private JLabel tile;
	
	/** The winning value **/
	private int winningValue;
	
	/*********************************************************************
	 * Constructor for a BoardTile object, the background of the tile is 
	 * white, while the border is a black line.
	 * When a tile has reached the winning value, the foreground of the
	 * label will be colored in green.
	 * The reason the color green is not applied to the background of the
	 * panel is because updating the background with a non-white color,
	 * the label's text will be hidden behind the color.
	 * @param winningValue The winning value for the game
	 ********************************************************************/
	public BoardTile(int winningValue){
		this.winningValue = winningValue;
		tile = new JLabel(".");
		this.setLayout(new GridBagLayout());
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(PANEL_DIMENSION, PANEL_DIMENSION));
		
		renderLabel();
		this.setVisible(true);		
	}

	/*********************************************************************
	 * Set the label's string
	 * @param str the string to put in the label.
	 ********************************************************************/
	public void setString(String str){
		tile.setText(str);
		createBorder();
		colorWinner();
		this.repaint();
	}
	
	/*********************************************************************
	 * Set the label's foreground color.
	 * @param color the Color object.
	 ********************************************************************/
	public void setColor(Color color){
		tile.setForeground(color);
		this.repaint();
	}
	
	/*******************************************************************éé
	 * @return The label's foreground color.
	 ********************************************************************/
	public Color getColor(){
		Color color = this.getForeground();
		
		//This could be not necessary, however since the specifics for getForeground() are not clear
		//it is a step to make sure that the color returned is "BLACK".
		if(color.getBlue() == 0 && color.getGreen() == 0 && color.getRed() == 0){
			return Color.BLACK;
		}
		else{
			return color;
		}
	}
	
	/*********************************************************************
	 * Adds the JLabel to the JPanel's center.
	 ********************************************************************/
	private void renderLabel(){
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.CENTER;
		this.add(tile, position);
	}
	
	/*********************************************************************
	 * Creates a border exclusively on the tiles that have a numeric value,
	 * making the empty tiles feel like an "empty" space. 
	 ********************************************************************/
	private void createBorder(){
		
		//The tile has not a numeric value.
		if(tile.getText().equals(".")){
			this.setBorder(BorderFactory.createEmptyBorder());
		}
		else{
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}
	
	/*********************************************************************
	 * Colors the tile with the winning value in green.
	 ********************************************************************/
	private void colorWinner(){
		
		//Compare two strings, not two numbers.
		if(tile.getText().equals("" + this.winningValue)){
			this.setBackground(Color.GREEN);
		}
	}
}
