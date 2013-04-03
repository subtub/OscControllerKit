//
// Reflection.java
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

package de.fhpotsdam.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {
	private static Reflection instance;

	private Reflection() {
	}

	public static Reflection getInstance() {
		if (instance == null) {
			instance = new Reflection();
		}
		return instance;
	}

	/**
	 * @param o
	 *            the object which contains the method to call, when passing
	 *            'this' from within the processing main sketch, all functions
	 *            in your main sketch will be recognized (setup(), draw(),...,
	 *            custom methods)
	 * @param m
	 *            the name of the method to call without "()"
	 * @param params
	 *            Contains the parameters to pass to function m
	 * @param parTypes
	 *            [] Contains the classes of the arguments to pass, as params is
	 *            of type object, we need this, too
	 */
	public void invokeMethodWithParams(Object o, String m,
			Object[] params, Class[] parTypes) {
		if (o == null || m == "" || m == null) {
			System.err.println("invokeMethodWithParams(): There was an error with your arguments");
			return;
		}
			Class c = o.getClass();
			Method method = null;
			try {
				method = c.getDeclaredMethod(m, parTypes);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// let's ignore this
				//e.printStackTrace();
			}
			if(method != null){
				try {
					method.invoke(o, params);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.out.println("Try putting \"public\" before the functions");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
	}
}
