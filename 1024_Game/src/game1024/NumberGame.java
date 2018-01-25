package game1024;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import mathematics.*;

/*****************************************************************************************************************
 * The NumberGame class is an implementation of the NumberSlider class.
 * This class is the "game engine" for a 1024-like game, where the user interacts with a game board
 * composed of rows an columns, in each tile there is a number that is a power of 2.
 * The interactions that can be made with the program are to create a custom game,
 * with a board and a winning value decided by the user, as long as the winning value is a power of 2,
 * and to play the game by sliding the tile to the right, left, up, and down. Other than that, the user has
 * the possibility to go back and undo its last move.
 * The rules:
 * - The game starts with 2 random values placed on the board at random positions.
 * - The user can slide the tiles in 4 directions: right, left, upward, downward.
 * - The default winning value is 1024.
 * - Each cell in the matrix contains zero or a number that is a power of two going from 2 to 2^(n-1), where 2^n is our winning value.
 * - When moving the cells, only cells with zero can be occupied or, in the case the two cells have the same value, they will be merged and the valued added up
 * - The game keeps going as long as the user can slide at least one tile or a tile reached the winning value. In the case that the cells in the matrix can't slide, the games end with a lose.
 * 
 * @author  Gionata Bonazzi
 * @version 3 March 2017
 *****************************************************************************************************************/
public class NumberGame implements NumberSlider{

	/**The matrix's # of rows **/
	private int height = 4;
	
	/**The matrix's # of columns **/
	private int width = 4;
	
	/**The winning value. Default value is 1024 **/
	private int winningValue = 1024;
	
	/** the matrix that represents the game grid the game **/
	private int[][] gameBoard;
	
	/** Stack of all the past game board states **/
	private Stack<int[][]> pastBoard;
	
	
	/***********************************************************************************
	 * Constructor for a NumberGame object that starts with default values.
	 * To change one, or more, default values, invoke resizeBoard() onto the previously
	 * created object.
	 **********************************************************************************/
	public NumberGame(){
		reset();	
	}
	
	/******************************************************************
	 * Constructor that takes some parameters and call resizeBoard, to
	 * create a personalized game with a different winning value, or a
	 * different size for the board.
	 * While being redundant with the resizeBoard() method, this particular
	 * constructor is used only to avoid writing two lines of code.
	 * That is, avoid to instantiate a NumberGame object and then call the
	 * resizeBoard on the object, instead instantiate the object directly with
	 * the wanted values.
	 * @param height height the number of rows in the board
     * @param width the number of columns in the board
     * @param winningValue the value that must appear on the board to
     *        win the game
	 ******************************************************************/
	public NumberGame(int height, int width, int winningValue){
		resizeBoard(height, width, winningValue);
	}
		
	public void resizeBoard(int height, int width, int winningValue) throws IllegalArgumentException{
		
		//check if the parameters are positive, and if the winning value is a power of 2.
		//the condition 'winningValue' < 0 is considered inside the isPowerOfTwo method
		if(height < 0 || width < 0 || !isPowerOfTwo(winningValue)){
				throw new IllegalArgumentException();
			}
			this.height = height;
			this.width	= width;
			this.winningValue = winningValue;
			reset();
	}
		
	public void reset(){
		this.gameBoard = new int[height][width];	
		Matrix.initializeValues(this.gameBoard);
	
		placeRandomValue();
		placeRandomValue();
		
		pastBoard  = new Stack<int[][]>();
	}
	
	public void setValues(final int[][] ref){
		this.gameBoard = Matrix.copy(ref);
	}
	
	/******************************************************************
	 * Method used for testing purposes, it permits the JUnit to
	 * use an assertTrue on two int[][] objects.
	 * @return the gameBoard matrix
	 *****************************************************************/
	public int[][] getBoard(){
		return this.gameBoard;
	}
	
	/******************************************************************
	 * @return the height of the game board
	 *****************************************************************/
	public int getHeight(){
		return this.height;
	}
	
	/******************************************************************
	 * set the height of the board to the desired value.
	 * To change all the parameters of the game,
	 * it is preferable to use the resizeBoard() method.
	 * @param height the new height
	 *****************************************************************/
	public void setHeight(int height){
		this.height = height;
	}
	
	/******************************************************************
	 * @return the width of the game board.
	 ******************************************************************/
	public int getWidth(){
		return this.width;
	}
	
	/******************************************************************
	 * set the width of the board to the desired value.
	 * To change all the parameters of the game,
	 * it is preferable to use the resizeBoard() method.
	 * @param width the new width
	 *****************************************************************/
	public void setWidth(int width){
		this.width = width;
	}
	
	/******************************************************************
	 * @return the winning value.
	 *****************************************************************/
	public int getWinningValue(){
		return this.winningValue;
	}
	
	/******************************************************************
	 * set the winning value of the board to the desired value.
	 * To change all the parameters of the game,
	 * it is preferable to use the resizeBoard() method.
	 * @param winningValue the new winning value
	 *****************************************************************/
	public void setWinningValue(int winningValue){
		this.winningValue = winningValue;
	}
	
	public Cell placeRandomValue(){
		
		//the game board is full, preventing to add new tiles
		if(isBoardFull()){
			throw new IllegalStateException();
		}
		Random rng = new Random();
		int randomAbscissa;
		int randomOrdinate;
		
		//iterate the loop as long as the cell is not empty
		do{
			randomAbscissa = rng.nextInt(this.height);
			randomOrdinate = rng.nextInt(this.width);
		}while(this.gameBoard[randomAbscissa][randomOrdinate] != 0);
		
		int randomPower	= 1 + rng.nextInt(Exponential.getExponential(winningValue, 2) - 1);//all numbers from 2^1 to 2^n-1, where 2^n is the winning value
		Cell randomCell = new Cell(randomAbscissa, randomOrdinate, Exponential.getPower(randomPower, 2));
		this.gameBoard[randomAbscissa][randomOrdinate] = randomCell.value;
		
		return randomCell;
	}
	

	
	public boolean slide(SlideDirection dir){
		
		if(getStatus() == GameStatus.IN_PROGRESS){
			int[][] slideBoard = changeBoard(dir, this.gameBoard);
			pastBoard.push(this.gameBoard);

			//Sliding direction down or right
			if(dir == SlideDirection.DOWN || dir == SlideDirection.RIGHT){
				slideBoard = slideRight(slideBoard);
			}
			
			//Sliding direction up or left
			if(dir == SlideDirection.UP || dir == SlideDirection.LEFT){
				slideBoard = slideUp(slideBoard);
			}
			this.gameBoard = changeBoard(dir, slideBoard);
			
			//The element in the new board are in the same position
			//meaning that a slide did not occur.
			if(!Matrix.equal(this.gameBoard, pastBoard.peek())){
				placeRandomValue();
				return true;
			}
			
			else{
				pastBoard.pop();
				return false;
			}
			
		}
		else{
			return false;
		}
	}
	
	public ArrayList<Cell> getNonEmptyTiles(){
		ArrayList<Cell> nonEmptyCells = new ArrayList<Cell>();
		for(int i = 0; i < this.gameBoard.length; i++){
			for(int j = 0; j < this.gameBoard[i].length; j++){
				
				//the matrix's cell contains a non zero (non empty) tile
				if(this.gameBoard[i][j] != 0){
					nonEmptyCells.add(new Cell(i, j, this.gameBoard[i][j]));
				}
			}
		}
		return nonEmptyCells;
	}
	
	public GameStatus getStatus(){
		
		//search for a match in the matrix with the winning value, meaning the user has won.
		if(Matrix.findValue(this.gameBoard, winningValue)){
					return GameStatus.USER_WON;
		}
		
		//the board is full of non zero tiles
		if(isBoardFull()){
			
			//It is possible to move the tiles, the game is in progress.
			if(canSlide(this.gameBoard)){
				return GameStatus.IN_PROGRESS;
			}
			
			//It is not possible to move the tiles, the user has lost.
			else{
				return GameStatus.USER_LOST;
			}
		}
		
		//The board has at least 1 empty (zero) tile, it is possible to slide, the game is in progress.
		else{
			return GameStatus.IN_PROGRESS;
		}
	}
	
	public void undo(){
		
		//prevent the program to take out from the stack something that does not exist
		if(this.pastBoard.isEmpty()){
			throw new IllegalStateException();
		}
		this.gameBoard = Matrix.copy(this.pastBoard.pop());//use the pop() method instead of peek() to discharge the most recent board from the stack.
	}
	
	/******************************************************************
	 * creates a String containing a representation of the game's matrix.
	 * @return a String representing the game's matrix.
	 *****************************************************************/
	public String printTable(){
		return Matrix.toString(gameBoard);
	}
	
	/******************************************************************************
	 * Given a number, the method returns if that number is a perfect power of two
	 * @param number the number supposed to be a power of two
	 * @return true if the number is a perfect power of two
	 * 		   false if the number is not a perfect power of two
	 *****************************************************************************/
	private boolean isPowerOfTwo(int number){
		try{
			Exponential.getExponential(number, 2);
			return true;
		}
		
		//the number is not a perfect power of 2
		catch(IllegalArgumentException e){
			return false;
		}
	}
	
	/************************************************************************
	 * Iterate the matrix to find any empty tile.
	 * In the given array of integer, an empty tile is represented by a zero
	 * @return true if there are only non zero values in the matrix
	 * 		   false if there is at least one zero in the matrix
	 ***********************************************************************/
	private boolean isBoardFull(){
		for(int i = 0; i < this.gameBoard.length; i++){
			for(int j = 0; j < this.gameBoard[i].length; j++){
				
				//0 correspond to an empty tile
				if(this.gameBoard[i][j] == 0){
					return false;
				}
			}
		}
		return true;
	}
	
	/******************************************************************
	 * Given a direction to slide and a matrix, it returns a new
	 * matrix that is transposed from the original if, and only if,
	 * the sliding direction is either DOWN or LEFT.
	 * The transposition has to be done in order to apply a sliding
	 * to the right, or up, instead of writing four different sliding
	 * methods.
	 * If the sliding direction is either UP or RIGHT, then the method
	 * will return the parameter unchanged.
	 * @param dir the direction where to slide
	 * @param gameBoard the matrix to change
	 * @return a matrix in which the tiles are ready to slide.
	 *****************************************************************/
	private int[][] changeBoard(SlideDirection dir, int[][] gameBoard){
		
		//transpose the matrix if the sliding direction is either DOWN or LEFT
		if(dir == SlideDirection.DOWN || dir == SlideDirection.LEFT){
			return Matrix.transpose(gameBoard);
		}
		return Matrix.copy(gameBoard);//return an address to a different object than the gameBoard object, but with the same elements.
	}
	
	/******************************************************************
	 * The method check if each tile has an adjacent, to the left
	 * or down, tile that has the same value. If such a pair exists,
	 * then a slide is possible.
	 * 
	 * @param gameBoard the matrix to get the tiles from.
	 * @return true if exists a pair of tiles such that the two tiles are
	 * 				equivalent.
	 * 		   false if the pair does not exists.
	 *****************************************************************/
	private boolean canSlide(int[][] gameBoard){
		boolean slide = false;
		for(int i = 0; i < gameBoard.length - 1; i++){
			for(int j = 0; j < gameBoard[i].length - 1; j++){
				slide = slide || isEqualRight(i, j, gameBoard) || isEqualDown(i, j, gameBoard);
				
				//no need to iterate again if the user can slide at least once
				//not using two break statements because a return uses less lines of code.
				if(slide){
					return slide;
				}
			}
		}
		return slide;
	}
	
	/******************************************************************
	 * Given a y-coordinate and a x-coordinate, the method returns if the
	 * tile in the position (y,x) of the matrix is equal to the matrix
	 * in the position (y,x+1), that is, if the tile to the right of the
	 * given (y,x) tile has the same value.
	 * In this example, I am representing the matrix as quadrant IV of
	 * the Cartesian plane (but with y positive).
	 * @param row y-coordinate of the tile in the matrix
	 * @param column x-coordinate of the tile in the matrix
	 * @param gameBoard the matrix
	 * @return true if the tile to the right of our (y,x) tile has the same value.
	 * 		   false otherwise.
	 *****************************************************************/
	private boolean isEqualRight(int row, int column, int[][] gameBoard){
		try{
			
			//Out of bounds
			if(column >= gameBoard[0].length|| column < 0 || row >= gameBoard.length || row < 0){
				throw new IllegalArgumentException();
			}
			
			//The elements' value match
			else if(gameBoard[row][column] == gameBoard[row][column+1]){
				return true;
			}
			else{
				return false;
			}
		}
		catch(IllegalArgumentException e){
			return false;
		}
	}
	
	/******************************************************************
	 * Given a y-coordinate and a x-coordinate, the method returns if the
	 * tile in the position (y,x) of the matrix is equal to the matrix
	 * in the position (y + 1,x), that is, if the tile below the
	 * given y,x tile has the same value.
	 * In this example, I am representing the matrix as quadrant IV of
	 * the Cartesian plane (but with y positive).
	 * @param row y-coordinate of the tile in the matrix
	 * @param column x-coordinate of the tile in the matrix
	 * @param gameBoard the matrix
	 * @return true if the tile below our (y,x) tile has the same value.
	 * 		   false otherwise.
	 *****************************************************************/
	private boolean isEqualDown(int row, int column, int[][] gameBoard){
			return isEqualRight(column, row, Matrix.transpose(gameBoard));
	}
	
	/******************************************************************
	 * Method that slides all the tiles in the board to the right.
	 * This slide method follows the rules of the game, the tiles
	 * at the rightmost end of the matrix won't be slid, and it will slid
	 * only the tiles that have a zero to the right or a tile of equal
	 * value. In the latter case, the two tiles' values will be merged as
	 * a sum of the two.
	 * Note that sliding to the right a transposed matrix,
	 * and then transpose it back to the original,
	 * gives a matrix in which the tiles have been slid down.
	 * @param gameBoard the matrix to slide
	 * @return a new matrix that is a copy of the original with a slide
	 * 		   applied to its elements.
	 *****************************************************************/
	private int[][] slideRight(int[][] gameBoard){
		int[][] slideBoard = Matrix.copy(gameBoard);
		int k = 0;
		
		//Using the while loop avoids sliding the tiles by just one space.
		while(k < slideBoard[0].length){
			for(int i = 0; i < slideBoard.length ; i++){
				for(int j = slideBoard[i].length - 1; j > 0; j--){
					
					//slide non-zero tiles if there is a zero tile to the right.
					 if(slideBoard[i][j] == 0){
						slideBoard[i][j] = slideBoard[i][j - 1];
						slideBoard[i][j - 1] = 0;
					}
				}
			}
			k++;
		}
		
		//merging tiles
		//having a different for loop to slide and to merge prevents some errors when merging.
		//For example, having a line like this: 4 | 2 | 2, you want to prevent the program from merging
		//0 | 4 | 4 and then 0 | 0 | 8. The same goes for the slide Up method.
		for(int i = 0; i < slideBoard.length ; i++){
			for(int j = slideBoard[i].length - 1; j > 0; j--){
				if(isEqualRight(i, j-1, slideBoard)){
					slideBoard[i][j] += slideBoard[i][j - 1];
					slideBoard[i][j - 1] = 0;
				}
				else if(slideBoard[i][j] == 0){
					slideBoard[i][j] = slideBoard[i][j-1];
					slideBoard[i][j - 1] = 0;
				}
			}
		}
		return slideBoard;
	}
	
	/******************************************************************
	 * Method that slides all the tiles in the board up.
	 * This slide method follows the rules of the game, the tiles
	 * at the upper part of the matrix won't be slid, and it will slid
	 * only the tiles that have a zero above or a tile of equal
	 * value. In the latter case, the two tiles' values will be merged as
	 * a sum of the two.
	 * Note that sliding up a transposed matrix,
	 * and then transpose it back to the original,
	 * gives a matrix in which the tiles have been slid to the left.
	 * @param gameBoard the matrix to slide
	 * @return a new matrix that is a copy of the original with a slide
	 * 		   applied to its elements.
	 *****************************************************************/
	private int[][] slideUp(int[][] gameBoard){
		int[][] slideBoard = Matrix.copy(gameBoard);
		int k = 0;
		
		//Using the while loop avoids sliding the tiles by just one space.
		while(k < slideBoard.length){
			for(int i = 0 ; i < slideBoard.length - 1; i++){
				for(int j = 0; j < slideBoard[i].length; j++){
					
					//slide non-zero tiles if there is a zero tile up.
					if(slideBoard[i][j] == 0){
						
						slideBoard[i][j]  = slideBoard[i+1][j];
						slideBoard[i+1][j] = 0;
					}		
				}
			}
			k++;
		}
		
		//merge tiles
		//Look at the comments in slideRight() method for an explanation as to why there are two for loop blocks
		for(int i = 0 ; i < slideBoard.length - 1; i++){
			for(int j = 0; j < slideBoard[i].length; j++){
			
				if(isEqualDown(i, j, slideBoard)){
					slideBoard[i][j] += slideBoard[i+1][j];
					slideBoard[i + 1][j] = 0;
				}
				if(slideBoard[i][j] == 0){
					slideBoard[i][j] = slideBoard[i + 1][j];
					slideBoard[i + 1][j] = 0;
				}
			}
			
		}
		return slideBoard;
	}
	
	
}

