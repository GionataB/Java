package foodCourt;

/************************************************************************
 * Limited time Person, the time before leaving is 3 times the given value,
 * the eatery time is 4 times the given value, the cashier's time is 2
 * times the given value.
 *
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 ***********************************************************************/
public class SpecialPerson extends Person{

	@Override
	public void setCashiersTime(int time) {
		this.cashiersTime = time*2;
		
	}

	@Override
	public void setLeaveTime(int time) {
		this.leaveTime = time*3;
		
	}

	@Override
	public void setEateryTime(int time) {
		this.boothTime = time*4;		
	}
	
}
