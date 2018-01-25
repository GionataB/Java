package mathematics;

/********************************************************************************************************************************
 * Class of static methods regarding an integer matrix (two dimensional array).
 * Outline:
 *  - transpose
 *  - invert columns or rows
 *  - initialize values to 0, or to another number.
 *  - initialize the values with increasing numbers starting from 0, or starting from a given number
 *  - compare two matrices and find if their elements are equivalent
 *  - copy the contents of a matrix in a new matrix.
 *  - find if the given matrix has the desired value as one of its elements
 *  - create a string of a matrix's elements, formatted in a way to represent the matrix as a table (similar to a spreadsheet).
 *   
 * @author Gionata Bonazzi
 * @version 0.2017303
 ********************************************************************************************************************************/
public class Matrix {
	
	/******************************************************************
	 * Static method that take a matrix and return a new matrix that is
	 * the transposition of the new one.
	 * A matrix transposed is a matrix that has rows and columns swapped
	 * in respect to the original matrix.
	 * @param matrix the matrix o transpose
	 * @return the matrix transposed
	 *****************************************************************/
	public static int[][] transpose(int[][] matrix){
		int[][] transposed = new int[matrix[0].length][matrix.length];
		
		for(int i = 0; i < transposed.length; i++){
			
			for(int j = 0; j < transposed[i].length; j++){
				transposed[i][j] = matrix[j][i];
			}
		}
		
		return transposed;
	}
	
	/******************************************************************
	 * Static method that take a matrix and create a new one that is 
	 * the original matrix mirrored along the y-axis.
	 * @param matrix the matrix to be mirrored
	 * @return the matrix mirrored
	 *****************************************************************/
	public static int[][] invertColumns(int[][] matrix){
		int[][] inverted = new int[matrix.length][matrix[0].length];
		
		for(int i = 0; i < inverted.length; i++){
		
			for(int j = 0; j < inverted[i].length; j++){
				inverted[i][j] = matrix[i][matrix[i].length - j - 1];
			}
		}
		
		return inverted;
	}
	
	/******************************************************************
	 * Static method that take a matrix and create a new one that is 
	 * the original matrix mirrored along the x-axis.
	 * @param matrix the matrix to be mirrored
	 * @return the matrix mirrored
	 *****************************************************************/
	public static int[][] invertRows(int[][] matrix){
		int[][] inverted = new int[matrix.length][matrix[0].length];
		
		for(int i = 0; i < inverted.length; i++){
		
			for(int j = 0; j < inverted[i].length; j++){
				inverted[i][j] = matrix[matrix.length - i][j];
			}
		}
	
		return inverted;
	}
	
	/******************************************************************
	 * Method that initializes the given matrix with the given value.
	 * the value will be put inside every single matrix cell.
	 * @param matrix the matrix to initialize
	 * @param value the value to initialize each matrix cell to
	 * @return the matrix initialized
	 *****************************************************************/
	public static int[][] initializeValues(int[][] matrix, int value){
		for(int i = 0; i < matrix.length; i++){
		
			for(int j = 0; j < matrix[i].length; j++){
				matrix[i][j] = value;
			}
		}
		
		return matrix;
	}
	
	/******************************************************************
	 * Method that initializes the given matrix to zero.
	 * @param matrix the matrix to initialize.
	 * @return the matrix initialized
	 *****************************************************************/
	public static int[][] initializeValues(int[][] matrix){
		Matrix.initializeValues(matrix, 0);
		return matrix;
	}
	
	/******************************************************************
	 * Method that initializes the given matrix.
	 * The first cell will be initialized to value, and then value will
	 * increase by one. This way, the matrix will be initialized with a
	 * value, increasing going to the right. At the end of the columns,
	 * the next value will be put inside the first cell in the new row.
	 * @param matrix the matrix to initialize
	 * @param value the value to start initializing the matrix with
	 * @return the matrix initialized
	 *****************************************************************/
	public static int[][] initializeValuesIncreasing(int[][] matrix, int value){
		int num = value;
		
		for(int i = 0; i < matrix.length; i++){
		
			for(int j = 0; j < matrix[i].length; j++){
				matrix[i][j] = num;
				num++;
			}
		}
		
		return matrix;
	}
	
	/******************************************************************
	 * Method that initializes the given matrix.
	 * The first cell will be initialized to zero, and then value will
	 * increase by one. This way, the matrix will be initialized with a
	 * zero, increasing going to the right. At the end of the columns,
	 * the next value will be put inside the first cell in the new row.
	 * @param matrix the matrix to initialize
	 * @return the matrix initialized
	 *****************************************************************/
	public static int[][] initializeValuesIncreasing(int[][] matrix){
		Matrix.initializeValuesIncreasing(matrix, 0);
		return matrix;
	}

	/******************************************************************
	 * Given two matrices of type int[][], the method returns if
	 * they are an exact copy of each other. In other words, an exact
	 * copy is when matrix[a][b] = other[a][b] for all values of 'a' and
	 * 'b', in that exact order. It does not take into consideration the 
	 * sum of the element, or if the same elements appear in different 
	 * positions.
	 * @param matrix1 the first matrix to compare
	 * @param matrix2 the second matrix to compare
	 * @return true if the elements of the two matrices are equal.
	 * 		   false if the element of the two matrices are not equal.
	 *****************************************************************/
	public static boolean equal(int[][] matrix1, int[][] matrix2){
		
		//The given matrices are not the same size, meaning they are not equal
		if(matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length){
			return false;
		}
		
		for(int i = 0; i < matrix1.length; i++){
			for(int j = 0; j < matrix1[i].length; j++){
		
				//Return false as soon as an element between the two matrices differs
				if(matrix1[i][j] != matrix2[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	/******************************************************************
	 * Method that takes a matrix and copies its contents inside a new
	 * matrix. This method is used to create a perfect copy of a matrix
	 * without passing a reference.
	 * @param matrix the matrix you want to copy
	 * @return the copied matrix
	 *****************************************************************/
	public static int[][] copy(int[][] matrix){
		int[][] copiedMatrix = new int[matrix.length][matrix[0].length];
		
		for(int i = 0; i < matrix.length; i++){
		
			for(int j = 0; j < matrix[i].length; j++){
				copiedMatrix[i][j] = matrix[i][j];
			}
		}
		
		return copiedMatrix;
	}
	
	/******************************************************************
	 * Given a number, the method return if the value exists in the
	 * matrix.
	 * It does not give the number's position in the matrix.
	 * @param matrix the matrix to search in the value.
	 * @param value the value to look for in the matrix.
	 * @return true if the value is present inside the matrix.
	 * 		   false if the value is not present inside the matrix.
	 *****************************************************************/
	public static boolean findValue(int[][] matrix, int value){
		for(int i = 0; i < matrix.length; i++){
			
			for(int j = 0; j < matrix[i].length; j++){
		
				//Return true as soon as there is a match.
				if(matrix[i][j] == value){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/******************************************************************
	 * Method that creates a string representation of the given matrix.
	 * @param matrix the matrix to print out
	 * @return a string representation of the given matrix.
	 *****************************************************************/
	public static String toString(int[][] matrix){
		String output = "";
		
		for(int i = 0; i < matrix.length; i++){
		
			for(int j = 0; j < matrix[i].length; j++){
				output += matrix[i][j] + "\t";
			}
			output += "\n";
		}
		
		return output;
	}
}
