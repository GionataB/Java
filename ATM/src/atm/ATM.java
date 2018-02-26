package atm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**********************************************************************
 * Class for an ATM object. 
 * The ATM handle only 100$, 50$ and 20$ bills.
 * It is possible to put money inside the ATM, take them out, transfer
 * money from an ATM to another and split the ATM object in two 
 * different ATM objects.
 * It is also possible to save the currency inside the ATM in a file,
 * and load it on a different ATM, or the same ATM.
 * Lastly, it is possible to invoke a static method that suspend
 * the function of putting in and taking out dollar bills from all ATM
 * objects, while it is impossible to do so for just one ATM.
 * 
 * @author   Gionata Bonazzi
 * @version  3 February 2017
 *********************************************************************/
public class ATM {

	/** How many Hundreds of dollars are in the ATM **/
	private int hundreds;

	/** How many Fifties of dollars are in the ATM **/
	private int fifties;
	
	/** How many Twenties of dollars are in the ATM **/
	private int twenties;
	
	/** It shows if the takeOut and putIn methods are off or on **/
	private static boolean running = true;
	
	/******************************************************************
	 * Default constructor, sets the ATM to zero, initializing all 
	 * instance variables to zero.
	 *****************************************************************/
	public ATM(){
		this.hundreds = 0;
		this.fifties  = 0;
		this.twenties = 0;
	}
	
	/******************************************************************
	 * A constructor that initializes the instance variables for this
	 * ATM object with the given parameters.
	 * @param hundreds the number of hundreds to start the ATM with.
	 * @param fifties the number of fifties to start the ATM with.
	 * @param twenties the number of twenties to start the ATM with.
	 *****************************************************************/
	public ATM(int hundreds, int fifties, int twenties){
			
		//throw an IllegalArgumentException if at least one parameter is lower than zero
		if(hundreds < 0 || fifties < 0 || twenties < 0){
			throw new IllegalArgumentException();
		}
		this.hundreds = hundreds;
		this.fifties  = fifties;
		this.twenties = twenties;
	}
	
	/******************************************************************
	 * A constructor that initializes the instance variables for this
	 * ATM object with the other ATM object parameters.
	 * @param other the other ATM object to copy parameters from.
	 *****************************************************************/
	public ATM(ATM other){
		this.hundreds = other.hundreds;
		this.fifties  = other.fifties;
		this.twenties = other.twenties;
	}
	
	/******************************************************************
	 * @return the hundreds of dollars in the ATM object
	 *****************************************************************/
	public int getHundreds(){
		return this.hundreds;
	}
	
	/******************************************************************
	 * @return the fifties of dollars in the ATM object
	 *****************************************************************/
	public int getFifties(){
		return this.fifties;
	}
	
	/******************************************************************
	 * @return the twenties of dollars in the ATM object
	 *****************************************************************/
	public int getTwenties(){
		return this.twenties;
	}
	
	/******************************************************************
	 * @return if all ATMs are running or are suspended
	 *****************************************************************/
	public static boolean isRunning(){
		return running;
	}

	/******************************************************************
	 * Modifier method for the hundreds variable
	 * @param hundreds hundred dollar bills to insert in the ATM
	 *****************************************************************/
	public void setHundreds(int hundreds){
		
		//throw an IllegalArgumentException if the parameter is lower than zero
		if(hundreds < 0){
			throw new IllegalArgumentException();
		}
		this.hundreds = hundreds;
	}
	
	/******************************************************************
	 * Modifier method for the fifties variable
	 * @param fifties fifty dollar bills to insert in the ATM
	 *****************************************************************/
	public void setFifties(int fifties){
		
		//throw an IllegalArgumentException if the parameter is lower than zero
		if(fifties < 0){
			throw new IllegalArgumentException();
		}
		this.fifties = fifties;
	}
	
	/******************************************************************
	 * Modifier method for the twenties variable
	 * @param twenties twenty dollar bills to insert in the ATM
	 *****************************************************************/
	public void setTwenties(int twenties){
		
		//throw an IllegalArgumentException if the parameter is lower than zero
		if(twenties < 0){
			throw new IllegalArgumentException();
		}
		this.twenties = twenties;
	}
	
	/******************************************************************
	 * Given "this" ATM and a different object, the method returns if 
	 * the field parameters for the two objects are equal. 
	 * The object given is casted as ATM for this method.
	 * @param other the object to compare to "this" ATM
	 * @return true if the field parameters for the two objects are 
	 *		   equal, false otherwise.
	 *****************************************************************/
	public boolean equals(Object other){
		ATM otherCasted = (ATM) other;
		return this.equals(otherCasted);
	}
	
	/******************************************************************
	 * Given "this" ATM and another one, the method returns if
	 * the field parameters for the two ATMs are equal.
	 * @param other the ATM to compare to "this" ATM
	 * @return true if the field parameters for the two ATMs are 
	 * 	 	   equal, false otherwise.
	 *****************************************************************/
	public boolean equals(ATM other){
		return this.hundreds == other.getHundreds() && 
			   this.fifties  == other.getFifties() &&
			   this.twenties == other.getTwenties();
	}

	/******************************************************************
	 * Static method that compares two ATM objects' pointers
	 * and return if they point to the same object.
	 * @param other1 the first ATM for the comparison
	 * @param other2 the second ATM for the comparison 
	 * @return true if the variables point to the same object,
	 * 		   false otherwise.
	 *****************************************************************/
	public static boolean equals(ATM other1, ATM other2){
		return other1.equals(other2);
	}
	
	/******************************************************************
	 * Given an ATM object, the method returns if "this" ATM is equal, 
	 * less than, or greater than the ATM given.  
	 * @param other the ATM to compare to "this" ATM
	 * @return 1 if "this" ATM is greater than the other,
	 * 		   0 if they are equal,
	 * 		  -1 if "this" ATM is less than the other. 
	 *****************************************************************/
	public int compareTo(ATM other){
		int total = this.hundreds + this.fifties + this.twenties;
		int otherTotal = other.getHundreds() + other.getFifties() +
						 other.getTwenties();
		int output = 1;
		
		//this ATM is less than other
		if(total < otherTotal){
			output = -output;
		}
		
		//this ATM is equal to other
		else if(total == otherTotal){
			output = 0;
		}
		return output;
	}
	
	/******************************************************************
	 * Static method that compares two given ATMs. It returns if
	 * the first given ATM is equal, less than, or greater than 
	 * the ATM given.  
	 * @param other1 the first ATM for the comparison.
	 * @param other2 the second ATM for the comparison.
	 * @return 1 if the first ATM is greater than the other,
	 * 		   0 if they are equal,
	 * 		  -1 if the first ATM given is less than the other. 
	 *****************************************************************/
	public static int compareTo(ATM other1, ATM other2){
		return other1.compareTo(other2);
	}
	
	/******************************************************************
	 * The method adds the parameters to "this" ATM object.
	 * The parameters must be true.
	 * @param hundreds number of hundred dollars bills that are going to 
	 * 				   be added to the ATM.
	 * @param fifties  number of fifty dollars bills that are going to
	 * 				   be added to the ATM.
	 * @param twenties number of twenty dollars bills that are going to
	 * 				   be added to the ATM.
	 *****************************************************************/
	public void putIn(int hundreds, int fifties, int twenties){
		
		//Is the ATM currently suspended?
		if(running){
			
			//throw an IllegalArgumentException if at least one parameter is lower than zero
			if(hundreds < 0 || fifties < 0 || twenties < 0){
				throw new IllegalArgumentException();
			}
			
			this.hundreds += hundreds;
			this.fifties  += fifties;
			this.twenties += twenties;
		}
	}
	
	/******************************************************************
	 * The method gets the dollars that are in another ATM object and 
	 * copy them in this ATM object.
	 * It will copy the dollar bills as they are, they won't be 
	 * converted in the least number of dollar bills. 
	 * @param other ATM object to get the fields from.
	 *****************************************************************/
	public void putIn(ATM other){
		
		this.putIn(other.hundreds, other.fifties, other.twenties);
	}
	
	/******************************************************************
	 * Method that subtract the parameters given from "this" ATM object.
	 * an exception in thrown if at least one parameter is negative
	 * or there are not enough corresponding bills in "this" ATM.
	 * @param hundreds number of hundred dollars bills that are going to 
	 * 				   be subtracted to the ATM.
	 * @param fifties  number of fifty dollars bills that are going to
	 * 				   be subtracted to the ATM.
	 * @param twenties number of twenty dollars bills that are going to
	 * 				   be subtracted to the ATM.
	 *****************************************************************/
	public void takeOut(int hundreds, int fifties, int twenties){

		//Is the ATM currently suspended?
		if(running){
			
			//throw an IllegalArgumentException if at least one parameter is lower than zero
			if(hundreds < 0 || fifties < 0 || twenties < 0){
				throw new IllegalArgumentException();
			}
			
			//throw an IllegalArgumentException if there are not enough dollar bills in this ATM
			if(hundreds > this.hundreds || fifties > this.fifties ||
			   twenties > this.twenties){
				throw new IllegalArgumentException();
			}
				this.hundreds -= hundreds;
				this.fifties  -= fifties;
				this.twenties -= twenties;
		}
	}
	
	/******************************************************************
	 * Method that subtract from "this" ATM object the equivalent of
	 * the parameter, another ATM object.
	 * an exception in thrown if at least one parameter is negative
	 * or there are not enough corresponding bills in "this" ATM.
	 * @param other ATM object, which fields will be taken out from 
	 * 		  this ATM
	 *****************************************************************/
	public void takeOut(ATM other){
		this.takeOut(other.hundreds, other.fifties, other.twenties);
	}
	
	/******************************************************************
	 * Method that subtract from "this" ATM object the amount of dollars 
	 * given. 
	 * The dollar bills that are taken out will be inserted in a new ATM. 
	 * The amount given has to be divisible by 10, and positive, or
	 * an Illegal Argument exception will be thrown.
	 * The amount desired will be inserted in the new ATM, with a focus on
	 * larger dollar bills. For example, in the case of amount 120, the
	 * method will insert 1 hundreds and 1 twenties bill, instead of 
	 * 6 twenties.
	 * As to how the taking out from the already existing ATM is handled,
	 * the larger bills will take priority over the smaller ones.
	 * @param  amount the amount to take out from this ATM, and to be 
	 * 		   inserted in the new ATM
	 * @return a new ATM with a credit equal to amount.
	 ******************************************************************/
	public ATM takeOut(double amount){
				
		//Is the ATM currently suspended?
		if(running){
			
			//throw an IllegalArgumentException if the parameter is lower than zero
			//or it is not divisible by 10
			if(amount < 0 || amount % 10 != 0){
				throw new IllegalArgumentException();
			}
			int total = this.hundreds * 100 + this.fifties * 50 +
					    this.twenties *20;
			
			//Check if there are enough dollar bills in this ATM
			if(amount < total){
				ATM other = new ATM();
				double partial = amount;
				int hundreds = (int)partial/100;
				partial %= 100;
				int fifties  = (int)partial/50;
				partial %= 50;
				int twenties = (int)partial/20;
				
				other.putIn(hundreds, fifties, twenties);
				
				partial = amount;
				
				hundreds = (int)partial/100;
				
				//There are not enough 100$ bills in this ATM
				if(this.hundreds < hundreds){
					hundreds = this.hundreds;
				}
				partial -= hundreds * 100;

				fifties = (int) partial / 50;
				
				//There are not enough 50$ bills in this ATM
				if(this.fifties < fifties){
					fifties = this.fifties;
				}
				partial -= fifties * 50;
				
				twenties = (int)partial / 20;
				
				this.takeOut(hundreds, fifties, twenties);
					
				return other;
			}
		}
		return null;
	}
	
	/******************************************************************
	 * Method that returns a string with the number of 100$, 50$ and 20$
	 * bills that are currently stored in the ATM.
	 * In the case that the ATM is suspended, it will be added a line
	 * at the start of the String with a message that the ATM is 
	 * suspended.
	 * The method overrides the toString() method of the Object class.
	 * @return the String that contains the bills stored in the ATM
	 *****************************************************************/
	public String toString(){
		
		String output = "";
		
		//The ATM is suspended
		if(!running){
			output += "--- THE ATM IS SUSPENDED ---\n";
		}
		output += hundreds + " hundred dollar bill";
		
		//The ATM has less, or more, than a single hundred dollar bill
		if(hundreds != 1){
			output += "s";
		}
		
		output += "\n" + fifties + " fifty dollar bill";
		
		//The ATM has less, or more, than a single hundred dollar bill
		if(fifties != 1){
			output += "s";
		}
		
		output += "\n" + twenties + " twenty dollar bill";
		
		//The ATM has less, or more, than a single hundred dollar bill
		if(twenties != 1){
			output += "s";
		}
		
		return output;
	}
	
	/******************************************************************
	 * The method save the ATM fields inside a file.
	 * The fields that will be saved are the number of 100$, 50$ and 20$
	 * bills stored in "this" ATM.
	 * The order in which the bills will be saved is 100$, 50$, 20$.
	 * It is recommended to specify the absolute path in the file's name,
	 * in order to save it in a known location.
	 * If an error occurs while creating the file, an IOException will
	 * be thrown and will be printed the throwable and its backtrace
	 * to the standard error stream. (source: Java API documentation for
	 * java.lang.Throwable.printStackTrace())
	 * @param filename the name of the file that will be created
	 *****************************************************************/
	public void save(String filename){
		PrintWriter out = null;
		try {
			
			//Open an output stream directed to a file
			out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		String s = this.hundreds + " " + this.fifties + " " + this.twenties;
		out.println(s);
		out.close(); 

	}
	
	/******************************************************************
	 * The method will try to load the file that has the same filename.
	 * if no such file is found, an exception will be thrown, and
	 * a string will be printed, with a "File not found".
	 * Note: the method read a file and save the first integer on the file
	 * as value for 100$ bills, the second integer as 50$, and last integer
	 * as 20$. To prevent errors, file created from another method or from
	 * an user should follow the format "hundreds fifties twenties" keeping
	 * the space between two words.
	 * If the format is not followed, an exception will be thrown in the case:
	 *  1)	The file does not contain numbers.
	 * 	2)	The file is empty.
	 * @param filename the name of the file to be loaded
	 *****************************************************************/
	public void load(String filename){
		Scanner fileReader = null;
		
		try{
			
			// open the data file
			fileReader = new Scanner(new File(filename));

			//The file is empty
			if(!fileReader.hasNext()){
				fileReader.close();
				throw new NoSuchElementException();	
			}
			
			// read the file and retrieve all the integers
			
			//the file does not have an integer to use for 100$ bills
			if(!fileReader.hasNextInt()){
				fileReader.close();
				throw new InputMismatchException();
			}
			
			hundreds = fileReader.nextInt();
			
			//the file does not have an integer to use for 50$ bills
			if(!fileReader.hasNextInt()){
				fileReader.close();
				throw new InputMismatchException();
			}
			
			fifties  = fileReader.nextInt();
			
			//the file does not have an integer to use for 20$ bills
			if(!fileReader.hasNextInt()){
				fileReader.close();
				throw new InputMismatchException();
			}
			
			twenties = fileReader.nextInt();
			
			fileReader.close();
		}

		//The strings retrieved from the file are not numbers
		catch(InputMismatchException error){
			System.out.println("The file does not contain numbers");
		}
		
		//The file is empty
		catch(NoSuchElementException error){
			System.out.println("The file is empty");
		}
		
		// could not find file
		catch(Exception error) {
			System.out.println("File not found ");
		}
		finally{
			fileReader.close();
		}

	}
	
	/******************************************************************
	 * Change the state of a static field. Meaning
	 * that this method can start, or stop, ALL the ATM objects,
	 * but you can't suspend only one.
	 * @param on boolean value where:
	 * 			 true:  suspend all ATMs
	 * 			 false: activate all ATMs
	 *****************************************************************/
	public static void suspend(Boolean on){
		
		// assigned "not on" to running because a user expect that 
		// suspend(true) would mean that the ATM is suspended.
		// meaning that if suspended is true, running should be false.
		running = !on;
	}
	
	
}
