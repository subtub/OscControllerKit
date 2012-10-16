package de.fhpotsdam.util.math;

public class Conversion {
	private static Conversion instance;
	
	private Conversion(){}
	
	public static Conversion getInstance(){
		if(instance == null){
			return new Conversion();
		}
		else{
			return instance;
		}
	}
	
	
	/**
	 * Interprets the bool array as a decimal value. 
	 * @param b The boolean values, big-endian (biggest values first)
	 * @return The decimal value
	 */
	public int boolArrayToDec(boolean[] b){
	  int result = 0;
	  for (boolean bit: b)
	  {
	    result <<= 1;
	    if (bit) result += 1;
	  }
	  return result;
	}
}
