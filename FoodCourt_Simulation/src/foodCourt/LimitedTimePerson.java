package foodCourt;

/************************************************************************
 * Limited time Person, the time before leaving and the eatery time
 * is half the time of a normal Person.
 *
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 ***********************************************************************/
public class LimitedTimePerson extends Person{

	public void setCashiersTime(int time) {
		this.cashiersTime = time;		
	}

	public void setLeaveTime(int time) {
		this.leaveTime = (int) (time*.5);	
	}

	public void setEateryTime(int time) {
		this.boothTime = (int) (time*.5);		
	}
	
}
