//
// SpaceNavigatorOsc.java
// OscControllerKit (v##library.prettyVersion##) is released under the MIT License.
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

package subtub.io.osc.spacenavigator;

/**
 * Class SpaceNavigatorOsc makes it easier to use Osc Messages sent by OSCulator. 
 * Using Java reflection, it will call functions in the main sketch like it is done on a normal key press.
 * 
 * @author Tim Pulver
 * @version 0.01
 */

//import java.lang.reflect.Method;
import java.lang.Enum;

import subtub.io.osc.spacenavigator.*;
import subtub.util.Reflection;
import processing.core.PApplet;
import oscP5.*;
import netP5.*;

public class SpaceNavigatorOsc {
	private static char SLASH = '/';

	// Function names, which need to be implemented in the main sketch
	private static String SPACE_NAVIGATOR_BUTTON_PRESSED_FUNCTION = "snButtonPressed";
	private static String SPACE_NAVIGATOR_BUTTON_RELEASED_FUNCTION = "snButtonReleased";
	private static String SPACE_NAVIGATOR_TRANSLATED_FUNCTION = "snTranslated";
	private static String SPACE_NAVIGATOR_ROTATED_FUNCTION = "snRotated";

	// typetags are sent with every OSC message, for example "ff" means there
	// are two float arguments
	private static String F_TYPETAG = "f";
	private static String FFF_TYPETAG = "fff";

	// parent sketch
	PApplet p;

	// Class types for java reflection
	private Class[] isnbParTypes; // int, snButton (Button pressed/released)
	private Class[] ifffParTypes; // int, float, float, float
									// (translated/rotated)

	/**
	 * Constructor of class SnOsc
	 * 
	 * @param p
	 *            The main PApplet, pass "this" from within Processing
	 */
	public SpaceNavigatorOsc(PApplet p) {
		this.p = p;
		initParTypes();
	}

	/**
	 * Checks if the address pattern of the osc message conatsins the string
	 * "wii". If it is, you can call process() later.
	 */
	public boolean isSpaceNavigatorMessage(OscMessage oscMessage) {
		return oscMessage.addrPattern().indexOf("/sp/") != -1;
	}

	/**
	 * Here we do most of the work, we check which osc message we received
	 * (button, translation, rotation). Later on we call a method in the main
	 * sketch and pass on the needed parameters (i.e. the button which was
	 * pressed on the SpaceNavigator)
	 * 
	 * @param oscMessage
	 *            The OSC Message sent by OSCulator containing a SpaceNavigator
	 *            OSC Message. Make sure to call isSpaceNavigatorMessage()
	 *            before!
	 */
	public void process(OscMessage oscMessage) {
		String addrPattern = oscMessage.addrPattern();
		String snNumber = addrPattern.substring(5, 6);
		boolean isButton = addrPattern.indexOf(SpaceNavigatorInputType.BUTTON
				.toString()) != -1;
		boolean isRotation = addrPattern
				.indexOf(SpaceNavigatorInputType.ROTATION.toString()) != -1;
		boolean isTranslation = addrPattern
				.indexOf(SpaceNavigatorInputType.TRANSLATION.toString()) != -1;
		Reflection reflection = Reflection.getInstance();
		// Now let's call the main sketch and invoke the appropriate functions.
		// The Parameters will be extracted by getObjectArray, which also adds
		// the wii number to the array.
		// wiimote button
		if (isButton) {
			float value = oscMessage.get(0).floatValue();
			if (value == 1.0) { // button pressed
				reflection.invokeMethodWithParams(p,
						SPACE_NAVIGATOR_BUTTON_PRESSED_FUNCTION,
						getObjectArray(oscMessage), isnbParTypes);
			}
			// gets called all the time, useless right now
			/*
			} else if (value == 0.0) {
				reflection.invokeMethodWithParams(p,
						SPACE_NAVIGATOR_BUTTON_RELEASED_FUNCTION,
						getObjectArray(oscMessage), isnbParTypes);
			}
			*/
		}
		// space navigator translation
		else if (isTranslation) {
			reflection.invokeMethodWithParams(p,
					SPACE_NAVIGATOR_TRANSLATED_FUNCTION,
					getObjectArray(oscMessage), ifffParTypes);
		}
		// space navigator rotation
		else if (isRotation) {
			reflection.invokeMethodWithParams(p,
					SPACE_NAVIGATOR_ROTATED_FUNCTION,
					getObjectArray(oscMessage), ifffParTypes);
		}
	}

	/**
	 * Extracts the Space Navigator button code, which is sent by OSCulator.
	 * 
	 * @param addPattern
	 *            The addressPattern of the OSC message sent by OSCulator
	 * @return The extracted Button String ("1"or "2")
	 */
	String getButtonString(String addrPattern) {
		return addrPattern.substring(addrPattern.lastIndexOf(SLASH) + 1);
	}

	/**
	 * Returns an enum object of the Space Navigator button according to the OSC
	 * string passed in. This is dependent on how OSCulator names the buttons.
	 * 
	 * @param sButton
	 *            The OSC-button-code, you can use getButtonString to extract it
	 *            from the OSC address pattern.
	 * @return An enum object of the button
	 */
	private SpaceNavigatorButton getSpaceNavigatorButton(String sButton) {
		for (SpaceNavigatorButton spaceNavigatorButton : SpaceNavigatorButton
				.values()) {
			if (sButton.equals(spaceNavigatorButton.toString())) {
				return spaceNavigatorButton;
			}
		}
		return null;
	}

	/**
	 * Returns the Space Navigator number. You can use multiple Space
	 * Navigators.
	 */
	private int getSpaceNavigatorNumber(String addrPattern) {
		return Integer.parseInt(addrPattern.substring(4, 5));
	}

	/**
	 * Extracts the passed by values from the osc message and sticks them into
	 * an object array The first element of the array is ALWAYS the Space
	 * Navigator number, the other values are the button (string), sensor values
	 * (float) or coordinate values (float)
	 */
	Object[] getObjectArray(OscMessage oscMessage) {
		Object[] values = null;
		String typetag = oscMessage.typetag();
		// button press, [0]: snNumber, [1]: snButton
		if (typetag.equals(F_TYPETAG)) {
			values = new Object[2];
			String sButton = getButtonString(oscMessage.addrPattern());
			values[1] = getSpaceNavigatorButton(sButton);
		}
		// joystick, [0]: snNumber, [1]: x, [2]: y, [3]: z
		else if (typetag.equals(FFF_TYPETAG)) {
			values = new Object[4];
			values[1] = oscMessage.get(0).floatValue();
			values[2] = oscMessage.get(1).floatValue();
			values[3] = oscMessage.get(2).floatValue();
		}
		// add the Space Navigator number as first element in the array
		values[0] = getSpaceNavigatorNumber(oscMessage.addrPattern());
		return values;
	}

	/**
	 * Initializes the parameter type arrays we need for java reflection. When
	 * we call a method in the main sketch, we need the types of parameters
	 * passed.
	 */
	private void initParTypes() {
		// integer, SpaceNavigatorButton
		isnbParTypes = new Class[2];
		isnbParTypes[0] = Integer.TYPE;
		isnbParTypes[1] = SpaceNavigatorButton.class;
		// integer, float, float, float
		ifffParTypes = new Class[4];
		ifffParTypes[0] = Integer.TYPE;
		for (int i = 1; i < ifffParTypes.length; i++) {
			ifffParTypes[i] = Float.TYPE;
		}
	}
}
