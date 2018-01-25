package foodCourt;

/************************************************************************
 * Exception for a LinkedList class.
 * The code is from Ira Woodring's gitHub repository.
 * 
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 ***********************************************************************/
public class EmptyQException extends Exception {
	
	/********************************************************************
	 * Display a message in the command line when the Q is empty
	 * @param message message to be displayed.
	 *******************************************************************/
	public EmptyQException(String message){
		super(message);
	}
}
