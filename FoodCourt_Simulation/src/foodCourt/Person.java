package foodCourt;

/*****************************************************************
 * Person class. This is an abstract class that has to be extended.
 * It cannot be instantiated.
 * 
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 *****************************************************************/
public abstract class Person implements ClockListener {
	
	/** time the Person arrived **/
	protected int tickTime;
	
	/** the total time this Person has spent from start to finish **/
	protected int totalTime;
	
	/** the person is leaving because too much time has passed in line **/
	protected boolean leavingQ;
	
	/** The next destination, could be another eatery or a checkout **/
	protected Eatery nextDestination;
	
	/** Time the Person spend in line **/
	protected double boothTime;
	
	/** Time the Person spend at the checkout **/
	protected int cashiersTime;
	
	/** Time the person wait in line before leaving **/
	protected int leaveTime;
	
	/********************************************************************
	 * Constructor that instantiate some variables.
	 *******************************************************************/
	public Person(){
		totalTime = 0;
		leavingQ = false;
	}
	
	public abstract void setCashiersTime (int time);
	public abstract void setLeaveTime (int time);
	public abstract void setEateryTime (int time);
	
	/********************************************************************
	 * @return if the person is leaving
	 *******************************************************************/
	public boolean isLeaving(){
		return leavingQ;
	}
	
	/********************************************************************
	 * @return the booth time.
	 *******************************************************************/
	public double getBoothTime() {
		return boothTime;
	}
	
	/********************************************************************
	 * @return the next destination.
	 *******************************************************************/
	public Eatery getDestination() {
		return nextDestination;
	}

	/********************************************************************
	 * @return the cashier's time.
	 *******************************************************************/
	public int getCashiersTime(){
		return cashiersTime;
	}
	
	/********************************************************************
	 * set the next destination.
	 *******************************************************************/
	public void setDestination(Eatery destination) {
		nextDestination = destination;
	}
	
	/********************************************************************
	 * @return the time the Person arrived.
	 *******************************************************************/
	public int getTickTime() {
		return tickTime;
	}

	/********************************************************************
	 * set the arrival time for this Person.
	 *******************************************************************/
	public void setTickTime(int tickTime) {
		this.tickTime = tickTime;
	}

	/********************************************************************
	 * @return the total time spent until this moment.
	 *******************************************************************/
	public int getTotalTime(){
		return totalTime;
	}
	
	public void event(int tick){
		if(tick == (tickTime + leaveTime)){
			leavingQ = true;
		}
		else if(tick == (tickTime + boothTime)){
			leaveTime = 0;
		}
		totalTime++;		
	}
}
