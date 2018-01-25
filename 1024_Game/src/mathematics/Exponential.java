package mathematics;

import java.security.InvalidParameterException;

/**********************************************************************************************
 * Class of static methods regarding integer exponentials and results
 * outline:
 *  - given an integer, return it's exponential only if it is a power of the given number
 *  - given an integer and an exponential, give the integer to the power of the exponential
 * @author Gionata Bonazzi
 * @version 0.2017303
 *********************************************************************************************/
public class Exponential {
	
	/******************************************************************
	 * Method that given a positive integer value and a positive number, 
	 * returns it's power of number.
	 * @param value the value to get the power of number from.
	 * @param number the base of the power.
	 * @return the power of two
	 * @throws an IllegalArgumentException
	 *****************************************************************/
	public static int getExponential(int power, int number){

		//base case: the number is 1, the power is 0.
		if(power == 1){
			return 0;
		}
		
		//block executed only when the original value is not a perfect power of two
		//the case value = 0 is also considered, because y = 0 is an asymptote of exponential functions 
		if(power <= 0 || power % number != 0){
			throw new IllegalArgumentException();
		}
		
		return 1 + getExponential(power/number, number);
	}
	
	/*************************************************************************
	 * Method that given a positive integer value, it returns a number that
	 * is the value raised  to the power of the exponential.
	 * @param exponential the power's index
	 * @return the number that is two to the power of exponential.
	 * @throws an InvalidParameterException if the parameter 'exponential'
	 * 		   is less than zero, or if it is high enough to make the resulting 
	 * 		   power exceed the integer's maximum value, thus potentially 
	 * 		   going overflow.
	 ************************************************************************/
	public static int getPower(int exponential, int base){
		
		//the parameter is negative
		if(exponential < 0 || Math.pow(base, exponential) > Integer.MAX_VALUE){
			throw new InvalidParameterException();
		}
		return (int) Math.pow(base, exponential);
	}
}
