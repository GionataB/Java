package atm;

import static org.junit.Assert.*;

import org.junit.Test;

/**********************************************************************
 * JUnit class to test the ATM class
 * @author  Gionata Bonazzi
 * @version 3 February 2017
 *********************************************************************/
public class ATMTest {

	// Testing valid constructors with wide range of values
	@Test
	public void testConstructor() {
		ATM s1 = new ATM(6, 5, 4);
		
		assertEquals (s1.getHundreds(), 6);
		assertEquals (s1.getFifties(), 5);
		assertEquals (s1.getTwenties(), 4);
		assertFalse(s1.getHundreds() != 6);
		assertFalse(s1.getFifties() != 5);
		assertFalse(s1.getTwenties() != 4);
		
		ATM s2 = new ATM();
		assertEquals (s2.getHundreds(), 0);
		assertEquals (s2.getFifties(), 0);
		assertEquals (s2.getTwenties(), 0);
		assertFalse(s2.getHundreds() > 0);
		assertFalse(s2.getFifties() > 0);
		assertFalse(s2.getTwenties() > 0);
		
		ATM s3 = new ATM(s1);
		assertEquals (s3.getHundreds(), 6);
		assertEquals (s3.getFifties(), 5);
		assertEquals (s3.getTwenties(), 4);
		assertFalse(s3.getHundreds() != 6);
		assertFalse(s3.getFifties() != 5);
		assertFalse(s3.getTwenties() != 4);
	}
	
	// testing valid takeOut with wide range of
	// quarters, dimes, nickels, pennies
	@Test
	public void testTakeOut1() {
		ATM s1 = new ATM(3,3,2);
		s1.takeOut(1,1,1);
		assertEquals (s1.getHundreds(), 2);
		assertEquals (s1.getFifties(), 2);
		assertEquals (s1.getTwenties(), 1);
		
		s1.takeOut(0,0,0);
		assertEquals (s1.getHundreds(), 2);
		assertEquals (s1.getFifties(), 2);
		assertEquals (s1.getTwenties(), 1);
		
		s1 = new ATM(5000, 5000, 5000);
		
		s1.takeOut(2599,3475,5000);
		assertEquals (s1.getHundreds(), 2401);
		assertEquals (s1.getFifties(), 1525);
		assertEquals (s1.getTwenties(), 0);
	}
	
	// testing valid takeOut with wide range of amounts
	@Test
	public void testTakeOut2() {
		ATM s1 = new ATM(5,3,3);
		ATM s2 = s1.takeOut(120);
				
		assertEquals (s1.getHundreds(), 4);
		assertEquals (s1.getFifties(), 3);
		assertEquals (s1.getTwenties(), 2);
		
		assertEquals (s2.getHundreds(), 1);
		assertEquals (s2.getFifties(), 0);
		assertEquals (s2.getTwenties(), 1);
		
		ATM s3 = s2.takeOut(100);
		
		assertEquals (s3.getHundreds(), 1);
		assertEquals (s3.getFifties(), 0);
		assertEquals (s3.getTwenties(), 0);
		
		assertEquals (s2.getHundreds(), 0);
		assertEquals (s2.getFifties(), 0);
		assertEquals (s2.getTwenties(), 1);
		
		//it will take out only 570, 
		//since there are no 10$ bills defined
		ATM s4 = s1.takeOut(580);
		
		assertEquals (s4.getHundreds(), 5);
		assertEquals (s4.getFifties(), 1);
		assertEquals (s4.getTwenties(), 1);
		
		assertEquals (s1.getHundreds(), 0);
		assertEquals (s1.getFifties(), 0);
		assertEquals (s1.getTwenties(), 1);
		
	}
	
	// testing putIn for valid low numbers
	@Test
	public void testPutIn() {
		ATM s1 = new ATM();
		s1.putIn(2,3,4);
		assertEquals (s1.getHundreds(), 2);
		assertEquals (s1.getFifties(), 3);
		assertEquals (s1.getTwenties(), 4);
		
		s1.putIn(0,0,0);
		assertEquals (s1.getHundreds(), 2);
		assertEquals (s1.getFifties(), 3);
		assertEquals (s1.getTwenties(), 4);
		
		s1.putIn(100, 5000, 2431);
		assertEquals (s1.getHundreds(), 102);
		assertEquals (s1.getFifties(), 5003);
		assertEquals (s1.getTwenties(), 2435);
	}
	
	// testing putIn and takeOut together
	@Test
	public void testPutInTakeOut() {
		ATM s1 = new ATM();
		s1.putIn(3,3,2);
		s1.takeOut(1,1,1);
		assertEquals (s1.getHundreds(), 2);
		assertEquals (s1.getFifties(), 2);
		assertEquals (s1.getTwenties(), 1);
		
		s1.putIn(1743, 2574, 9854);
		s1.takeOut(1500, 2573, 2);
		assertEquals (s1.getHundreds(), 245);
		assertEquals (s1.getFifties(), 3);
		assertEquals (s1.getTwenties(), 9853);
		
		s1.putIn(0,0,0);
		s1.takeOut(0,0,0);
		assertEquals (s1.getHundreds(), 245);
		assertEquals (s1.getFifties(), 3);
		assertEquals (s1.getTwenties(), 9853);
	}

	// Testing equals for valid numbers
	@Test
	public void testEqual () {
		ATM s1 = new ATM(2, 5, 4);
		ATM s2 = new ATM(6, 5, 4);
		ATM s3 = new ATM(2, 5, 4);

		assertFalse(s1.equals(s2));
		assertTrue(s1.equals(s3));
		
		s3 = s2;
		s2 = new ATM(2,5,4);
		assertTrue(s1.equals(s2));
		assertFalse(s1.equals(s3));
		
		s1 = s3;
		assertTrue(s1.equals(s3));
		assertFalse(s1.equals(s2));
	}

	// testing compareTo all returns
	@Test
	public void testCompareTo () {
		ATM s1 = new ATM(2, 5, 4);
		ATM s2 = new ATM(6, 5, 4);
		ATM s3 = new ATM(2, 3, 4);
		ATM s4 = new ATM(2, 5, 4);

		assertTrue(s2.compareTo(s1) > 0);
		assertTrue(s3.compareTo(s1) < 0);
		assertTrue(s1.compareTo(s4) == 0);
		assertTrue(s2.compareTo(s1) == 1);
		assertTrue(s3.compareTo(s1) == -1);
		assertTrue(s2.compareTo(s4) > 0);
		assertTrue(s4.compareTo(s2) < 0);
		assertTrue(s4.compareTo(s4) == 0);
		assertTrue(s3.compareTo(s4) < 0);
		assertTrue(s4.compareTo(s3) > 0);
	}

	// load and save combined.
	@Test
	public void testLoadSave() {
		ATM s1 = new ATM(6, 5, 4);
		ATM s2 = new ATM(6, 5, 4);

		s1.save("file1");
		s1 = new ATM();  // resets to zero

		s1.load("file1");
		assertTrue(s1.equals(s2));

	}
	


	// testing takeOut null ATM
	@Test
	public void testTakeOutNull() {
		ATM s1 = new ATM(3,1,2);
		ATM s2 = s1.takeOut(700);
		assertEquals(s2,  null);
	}
	
	// testing not able to make change
	@Test
	public void testMutate() {
		ATM s1 = new ATM(6, 5, 4);
		ATM s2 = new ATM(0, 0, 0);
		ATM.suspend(true);
		s2.putIn(s1);
		s1.takeOut(120);
		assertEquals (s2.getHundreds(), 0);
		assertEquals (s2.getFifties(), 0);
		assertEquals (s2.getTwenties(), 0);
		assertEquals (s1.getHundreds(), 6);
		assertEquals (s1.getFifties(), 5);
		assertEquals (s1.getTwenties(), 4);
		ATM.suspend(false);
		s2.putIn(s1);
		assertEquals (s2.getHundreds(), 6);
		assertEquals (s2.getFifties(), 5);
		assertEquals (s2.getTwenties(), 4);
		
	}
	
	// testing negative number for nickels, takeOut
	@Test(expected = IllegalArgumentException.class)
	public void testTakeOutNegHundreds() {
		ATM s1 = new ATM(2,2,2);
		s1.takeOut(-1,1,1);
	}
	
	// testing negative number for nickels, takeOut
	@Test(expected = IllegalArgumentException.class)
	public void testTakeOutNegFifties() {
		ATM s1 = new ATM(2,2,2);
		s1.takeOut(1,-1,1);
	}
		
	// testing negative number for nickels, takeOut
	@Test(expected = IllegalArgumentException.class)
	public void testTakeOutNegTwenties() {
		ATM s1 = new ATM(2,2,2);
		s1.takeOut(1,1,-1);
	}

	// testing negative number quarters, for constructors
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegHundreds() {
		new ATM(-300, 0, 0);		
	}
	
	// testing negative number quarters, for constructors
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegFifties() {
		new ATM(0, -300, 0);		
	}
		
	// testing negative number quarters, for constructors
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegTwenties() {
		new ATM(0, -500000, 0);		
	}
	
	// testing negative number for quarters, putIn
	@Test(expected = IllegalArgumentException.class)
	public void testPutInNegHundreds() {
		ATM s = new ATM(2,3,4);
		s.putIn(-30,2,30);
	}
	
	// testing negative number for quarters, putIn
	@Test(expected = IllegalArgumentException.class)
	public void testPutInNegFifties() {
		ATM s = new ATM(2,3,4);
		s.putIn(30,-2,30);
	}

	// testing negative number for quarters, putIn
	@Test(expected = IllegalArgumentException.class)
	public void testPutInNegTwenties() {
		ATM s = new ATM(2,3,4);
		s.putIn(30,2,-30);
	}
	
	//testing negative number for amount, takeOut
	@Test(expected = IllegalArgumentException.class)
	public void testTakeOutNegAmount(){
		ATM s = new ATM (2, 1, 7);
		s.takeOut(-50120);
	}
	
	//testing number not divisible by 10 for amount, takeOut
		@Test(expected = IllegalArgumentException.class)
		public void testTakeOutNotDivisibleByTenAmount(){
			ATM s = new ATM (2, 1, 7);
			s.takeOut(3129);
		}
}