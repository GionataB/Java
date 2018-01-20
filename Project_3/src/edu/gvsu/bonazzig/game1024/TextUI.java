package edu.gvsu.bonazzig.game1024;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Hans Dulimarta on Jun 27, 2016.
 * Last modified by Gionata Bonazzi on Mar 3, 2017.
 * Gionata Bonazzi: as a side note, i'm not putting java doc comments for the TextUI.java because the code is not mine.
 */
public class TextUI {
    private NumberGame game;//Gionata Bonazzi: modified to NumberGame because originally I was using a public method to print the matrix that was not given in the interface.
    private int[][] grid;
    private static int CELL_WIDTH = 3;
    private static String NUM_FORMAT, BLANK_FORMAT;
    private Scanner inp;
    private int wins;//Gionata Bonazzi: added a variable that keeps track of the wins.
    private int totalPlays;//Gionata Bonazzi: added a variable that keeps track of the total number of games played in one session.

    public TextUI() {
        
    	/*Gionata Bonazzi: added the next three statements*/
    	this.game 	    = new NumberGame();
        this.wins 	    = 0;
        this.totalPlays = 0;
        
        if (game == null) {
            System.err.println ("*---------------------------------------------*");
            System.err.println ("| You must first modify the UI program.       |");
            System.err.println ("| Look for the first TODO item in TextUI.java |");
            System.err.println ("*---------------------------------------------*");
            System.exit(0xE0);
        }

        grid = new int[4][4];

        /* Set the string to %4d */
        NUM_FORMAT = String.format("%%%dd", CELL_WIDTH + 1);

        /* Set the string to %4s, but without using String.format() */
        BLANK_FORMAT = "%" + (CELL_WIDTH + 1) + "s";
        inp = new Scanner(System.in);
    }

    private void renderBoard() {
        /* reset all the 2D array elements to ZERO */
        for (int k = 0; k < grid.length; k++)
            for (int m = 0; m < grid[k].length; m++)
                grid[k][m] = 0;
        
        /* fill in the 2D array using information for non-empty tiles */
        for (Cell c : game.getNonEmptyTiles())
            grid[c.row][c.column] = c.value;

       /* Print the 2D array using dots and numbers */
       for (int k = 0; k < grid.length; k++) {
            for (int m = 0; m < grid[k].length; m++)
            {
                if (grid[k][m] == 0)
                    System.out.printf (BLANK_FORMAT, ".");
                else
                    System.out.printf (NUM_FORMAT, grid[k][m]);
            
            }
            System.out.println();
       	}
       
       /*
        * Gionata Bonazzi: The renderBoard() was given. However, I added an original method in the game engine (NumberGame.java)
        * that returns a string that contains a representation of the table.
        * I'm not using my method in the TextUI.java.
        */
      }
 

    /**
     * The main loop for playing a SINGLE game session. Notice that
     * the following method contains NO GAME LOGIC! Its main task is
     * to accept user input and invoke the appropriate methods in the
     * game engine.
     */
    public void playLoop() {
    	this.game = new NumberGame();
    	
        renderBoard();
        this.totalPlays++;
        /* To keep the right margin within 75 columns, we split the
           following long string literal into two lines
         */
        System.out.print ("Slide direction (W, S, Z, A), " +
                "[U]ndo, [Q]uit, or [P]ercentage of winning? ");
        String resp = inp.next().trim().toUpperCase();
        while (!resp.equals("Q")) {
            switch (resp) {
                case "W": /* Up */
                    game.slide(SlideDirection.UP);
                    renderBoard();
                    break;
                case "S":
                    game.slide(SlideDirection.RIGHT);
                    renderBoard();
                    break;
                case "Z":
                    game.slide(SlideDirection.DOWN);
                    renderBoard();
                    break;
                case "A":
                    game.slide(SlideDirection.LEFT);
                    renderBoard();
                    break;
                case "R":
                	game.resizeBoard(5, 5, 1024);
                	renderBoard();
                	break;
                /*Gionata Bonazzi: added this block (case P)*/
                case "P":
                	System.out.println("\nStatistics for the session:\n" + "Number of wins:\t\t" + this.wins +
        					"\nNumber of games played:\t" + this.totalPlays + "\nWinning percentage:\t"+
        					(int)((double)this.wins/this.totalPlays*100) + " %\n");
                	renderBoard();
                	break;
                case "U":
                    try {
                        game.undo();
                        renderBoard();
                    } catch (IllegalStateException exp) {
                        System.err.println ("Can't undo that far");
                    }
            }
            if (game.getStatus() != GameStatus.IN_PROGRESS)
                break;
            /* To keep the right margin within 75 columns, we split the
                following long string literal into two lines
             */
            System.out.print ("Slide direction (W, S, Z, A), " +
                    "[U]ndo, [Q]uit, or [P]ercentage of winning? ");/*Gionata Bonazzi: added the [P]ercentage of winning part.*/
            resp = inp.next().trim().toUpperCase();
        }

        /* Almost done.... */
        switch (game.getStatus()) {
           
        	/*Gionata Bonazzi: modified the IN_PROGRESS case.*/
        	case IN_PROGRESS:
                break;
            case USER_WON:
                System.out.println ("Congratz");
                wins++;
                break;
            case USER_LOST:
                System.out.println ("Sorry....!");
                break;
        }
        
        /*Gionata Bonazzi: From this point onwards, the code is a creation of mine.*/
        System.out.println("\nPlay again? ([Y]es/[N]o)");
    	resp = inp.next().trim().toUpperCase();
    	
        while(!resp.equals("N")){
        	if(resp.equals("Y")){
        		playLoop();
        	}
        	System.out.println("Keep playing? ([Y]es/[N]o)");
        	resp = inp.next().trim().toUpperCase();
        }
        	System.out.println("Thanks for playing!");
        	System.out.println("\nStatistics for the session:\n" + "Number of wins:\t\t" + this.wins +
					"\nNumber of games played:\t" + this.totalPlays + "\nWinning percentage:\t"+
					(int)((double)this.wins/this.totalPlays*100) + " %");
    }
    
    public static void main(String[] arg) {
        TextUI t = new TextUI();
        t.playLoop();
    }
}



