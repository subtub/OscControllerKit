//
// Conversion.java
// OscControllerKit (v.##library.prettyVersion##) is released under the MIT License.
//
// Copyright (c) 2012, Tim Pulver & Paul Vollmer http://www.fh-potsdam.de
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

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
