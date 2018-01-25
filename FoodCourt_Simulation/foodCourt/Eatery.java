/**
 * 
 */
package foodCourt;

/*****************************************************************
 * Eatery class, each Eatery is a Queue that contains Person 
 * objects.
 * 
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 *****************************************************************/
public class Eatery implements ClockListener {
	
	/** Queue of Person objects **/
	protected LinkedList<Person> Q = new LinkedList<Person>();
	
	/** The time it will take for the next event **/
	protected int timeOfNextEvent = 0;
	
	/** The maximum length reached, could be different from the current length **/
	protected int maxQlength = 0;
	
	/** Person currently at the Eatery, in other words the first element in the Q **/
	protected Person person;
	
	/** The total number of people the Eatery object has serviced so far **/
	protected int completed = 0;
	
	/********************************************************************
	 * Add a person to the Q
	 * @param person the person to be added.
	 *******************************************************************/
	public void add(Person person){
		Q.enQ(person);
		
		//Update the maximum length
		if (Q.size() > maxQlength){
			maxQlength = Q.size();
		}
	}
	
	public void event(int tick){
		if (tick == timeOfNextEvent) {
			if (person != null && !person.isLeaving()) {// Notice the delay that takes place here
				person.getDestination().add(person);// take this person to the next station. 
				person.setDestination(null);
				person = null;							   // I have send the person on. 
			}	
			try {
				person = (Person)Q.deQ(); // do not send this person as of yet, make them wait. 
				timeOfNextEvent = tick + (int) (person.getBoothTime() + 1);
				completed++;
			} catch (EmptyQException e) {}
														
		}
	}
	
	/********************************************************************
	 * @return the Q size.
	 *******************************************************************/
	public int getLeft() {
		return Q.size();
	}
	
	/********************************************************************
	 * @return the max length.
	 *******************************************************************/
	public int getMaxQlength() {
		return maxQlength;
	}

	/********************************************************************
	 * @return People serviced.
	 *******************************************************************/
	public int getThroughPut() {
		return completed;
	}
}
