package foodCourt;


/*****************************************************************
 * The master clock that runs the simulation.
 * 
 * Each second is simulated through a for-loop's iteration.
 * 
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 *****************************************************************/
public class Clock {

	/** Each listener counts the time for one object **/
	private ClockListener[] myListeners;
	
	/** The number of listeners currently used **/
	private int numListeners;
	
	/** The total number of listeners that can be used **/
	private int MAX = 1000000;

	/********************************************************************
	 * Constructor for a Clock object, instantiate the
	 * array made up of multiple ClockListener objects.
	 *******************************************************************/
	public Clock() {
		numListeners = 0;
		myListeners = new ClockListener[MAX];
	}

	/********************************************************************
	 * Run the simulation, each iteration simulates a second passing.
	 * @param endingTime the total time the simulation runs.
	 *******************************************************************/
	public void run(int endingTime) {
		for (int currentTime = 0; currentTime <= endingTime; currentTime++) {
			for (int j = 0; j < numListeners; j++)
				myListeners[j].event(currentTime);
		}
	}

	/********************************************************************
	 * Add a clockListener to the array.
	 * @param cl the ClockListener to be added.
	 *******************************************************************/
	public void add(ClockListener cl) {
		myListeners[numListeners] = cl;
		numListeners++;
	}

	/********************************************************************
	 * @return the array of ClockListeners.
	 *******************************************************************/
	public ClockListener[] getMyListeners() {
		return myListeners;
	}

	/********************************************************************
	 * Method used to replace the array with a given new array.
	 * Used for testing.
	 * @param myListeners the new array.
	 *******************************************************************/
	public void setMyListeners(ClockListener[] myListeners) {
		this.myListeners = myListeners;
	}

	/********************************************************************
	 * @return the number of ClockListeners currently used
	 *******************************************************************/
	public int getNumListeners() {
		return numListeners;
	}

	/********************************************************************
	 * Set the number of ClockListeners currently used.
	 * Setting the number does not change the ClockListeners inside
	 * the array.
	 * Used for testing purposes with setMyListeners(), if not used in
	 * combination the run() method could throw an exception.
	 * @param numListeners ClockListeners used.
	 *******************************************************************/
	public void setNumListeners(int numListeners) {
		this.numListeners = numListeners;
	}

	/********************************************************************
	 * @return the maximum ClockListeners that can be used.
	 *******************************************************************/
	public int getMAX() {
		return MAX;
	}

}
