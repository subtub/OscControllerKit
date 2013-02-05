package de.fhpotsdam.io.osc.trackpad;

/**
 * Class TrackPadOsc makes it easier to use Osc Messages sent by MultitouchPadOsc.
 * MultitouchPadOsc can be used with Apple track pads and magic track pads. 
 * You can download it here:
 * <a href="http://wrongentertainment.github.com/MultitouchPadOsc">MultitouchPadOsc</a>
 * 
 * @author Tim Pulver
 * @version 0.01
 */

//import java.lang.reflect.Method;
import java.lang.Enum;

import de.fhpotsdam.io.osc.trackpad.*;
import de.fhpotsdam.util.Reflection;
import processing.core.PApplet;
import oscP5.*;
import netP5.*;


	public class TrackPadOsc {
		private static char SLASH = '/';

		// Function names, which need to be implemented in the main sketch
		private static String TRACKPAD_FINGER_ADDED_FUNCTION = "tpFingerPressed";
		private static String TRACKPAD_FINGER_REMOVED_FUNCTION = "tpFingerReleased";
		private static String TRACKPAD_FINGER_MOVED_FUNCTION = "tpFingerMoved";

		// typetags are sent with every OSC message, for example "ff" means there
		// are two float arguments
		private static String NO_TYPETAG = "";
		private static String FFFF_TYPETAG = "ffff";
		
		private boolean gestureRecognition = false;

		// parent sketch
		PApplet p;

		// Class types for java reflection
		private Class[] sParTypes; // string (trackpad name)
		private Class[] sffffParTypes; // string (trackpadname), float (x), float (y), float (size), float (angle)

		/**
		 * Constructor of class SnOsc
		 * 
		 * @param p
		 *            The main PApplet, pass "this" from within Processing
		 */
		
		public TrackPadOsc(PApplet p) {
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
		/*
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
		*/

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
		/*
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
		*/

		/**
		 * Initializes the parameter type arrays we need for java reflection. When
		 * we call a method in the main sketch, we need the types of parameters
		 * passed.
		 */
		
		private void initParTypes() {
			// integer, SpaceNavigatorButton
			sParTypes = new Class[1];
			sParTypes[0] = new String().getClass();
			sffffParTypes = new Class[5];
			sffffParTypes[0] = new String().getClass();
			for(int i=1; i<5; i++){
				sffffParTypes[i] = Integer.TYPE;
			}
			// integer, float, float, float (TODO is this being used??)
			/*
			ifffParTypes = new Class[4];
			ifffParTypes[0] = Integer.TYPE;
			for (int i = 1; i < ifffParTypes.length; i++) {
				ifffParTypes[i] = Float.TYPE;
			}
			*/
		}
		


}
