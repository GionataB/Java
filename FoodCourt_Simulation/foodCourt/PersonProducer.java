package foodCourt;

import java.util.Random;

/*****************************************************************
 * Each given time the class produce a new Person object and
 * assign random values for the Person.
 * 
 * However, the values are not totally random, they are a standard
 * deviation from the given value.
 * 
 * @author Rosa Fleming
 * @author Gionata Bonazzi
 * @author Alex Woods
 * 
 * @version 4/13/17
 *****************************************************************/
public class PersonProducer implements ClockListener {
	
	private int nextPerson = 0;
	
	/** The current Eatery **/
	private Eatery eatery;
	
	/** The next Eatery **/
	private Eatery nextEatery;
	
	/** Time before the next Person arrives **/
	private int numOfTicksNextPerson;
	
	/** average time spent in the eatery **/
	private int averageEateryTime;
	
	/** average time spent at the checkout **/
	private int averageCashierTime;
	
	/** Time before leaving **/
	private int leavingTime;
	
	/** The Clock taht runs the simulation **/
	private Clock clock;
	
	/** random used to generate people's values **/
	private Random r = new Random();
	
	/********************************************************************
	 * Instantiate the fields with values that will be used to generate
	 * people.
	 * 
	 * @param eatery current eatery
	 * @param nextEatery next eatery
	 * @param clock the master clock
	 * @param numOfTicksNextPerson time before next person
	 * @param averageEateryTime average time spent inside an eatery
	 * @param averageCashierTime average time spent to checkout
	 * @param leavingTime time before leaving
	 *******************************************************************/
	public PersonProducer(Eatery eatery, Eatery nextEatery, Clock clock, int numOfTicksNextPerson, int averageEateryTime, int averageCashierTime, int leavingTime) {
		this.clock = clock;
		this.nextEatery = nextEatery;
		this.eatery = eatery;
		this.numOfTicksNextPerson = numOfTicksNextPerson;
		this.averageEateryTime = averageEateryTime;
		this.averageCashierTime = averageCashierTime;
		this.leavingTime = leavingTime;
		//r.setSeed(13);    // This will cause the same random numbers
	}
	
	public void event(int tick) {
		if (nextPerson <= tick) {
			nextPerson = tick + numOfTicksNextPerson;
			int rPerson = (int)(Math.random() * 100);
			Person person;
			if(rPerson < 10){
				person = new SpecialPerson();
			}
			else if(rPerson < 30){
				person = new LimitedTimePerson();
			}
			else{
				person = new RegularPerson();
			}
		
			person.setEateryTime((int)(averageEateryTime*0.5*r.nextGaussian() + averageEateryTime +.5));
			person.setCashiersTime((int)(averageCashierTime*0.5*r.nextGaussian() + averageCashierTime +.5));
			person.setLeaveTime(leavingTime);
			person.setTickTime(tick);
			person.setDestination(nextEatery);
			eatery.add(person);
			clock.add(person);
			
		}
	}
}
