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
