package foodCourt;

/*****************************************************************
 * This is the Cashiers class that simulates people checking out 
 * at a food court.
 * 
 * The methods to handle the LinkedList, in this case the waiting 
 * line, are inherited from Eatery.
 * 
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 *****************************************************************/
public class Cashier extends Eatery implements ClockListener{

	/** The checkouts **/
	private Person[] checkouts;
	
	/** The total time a person has spent in line, used to calculate the average spent in line **/
	private int totalTime;
	
	/********************************************************************
	 * Constructor for a Cashier object. Instantiate the variables.
	 * The variable 'complete' is inherited from Eatery.
	 * @param numCheckouts the total number of cashiers where People can 
	 * 					   check out.
	 *******************************************************************/
	public Cashier(int numCheckouts){
		checkouts = new Person[numCheckouts];
		totalTime = 0;
		completed = 0;
	}
	
	/********************************************************************
	 * @return the average time people spend from start to finish
	 *******************************************************************/
	public int getAverageTime(){
		
		//Avoid division by zero.
		if(completed == 0){
			return 0;
		}
		return (int) totalTime / completed;
	}
	
	public void event(int tick){
		
		//Iterate through each checkout
		for(int i = 0; i < checkouts.length; i++){
			
			//The checkout is empty, a person can be taken from the Queue.
			if(checkouts[i] == null){
				try{
					checkouts[i] = (Person) Q.deQ();
					
					//Change the Persons's cashier time in respect to the current time
					checkouts[i].setCashiersTime(checkouts[i].getCashiersTime() + tick);
				}
				
				//Do nothing if the Queue is empty.
				catch(EmptyQException e){}
			}
			
			//The checkout is not empty, see if the Person is done and ready to leave
			else if(tick == checkouts[i].getCashiersTime()){
				completed++;
				totalTime += checkouts[i].getTotalTime();
				checkouts[i] = null;//The Person leaves.
			}
		}
	}
}
