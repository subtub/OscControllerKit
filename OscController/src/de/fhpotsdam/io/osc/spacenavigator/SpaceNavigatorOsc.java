package de.fhpotsdam.io.osc.spacenavigator;

/**
 * Class SpaceNavigatorOsc makes it easier to use Osc Messages sent by OSCulator. 
 * Using Java reflection, it will call functions in the main sketch like it is done on a normal key press.
 * 
 * @author Tim Pulver
 * @version 0.01
 */

import java.lang.reflect.Method;
import java.lang.Enum;

import de.fhpotsdam.io.osc.spacenavigator.*;
import de.fhpotsdam.util.Reflection;
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
	  
	  // typetags are sent with every OSC message, for example "ff" means there are two float arguments
	  private static String F_TYPETAG = "f";
	  private static String FFFF_TYPETAG = "ffff";

	  // parent sketch
	  PApplet p;

	  // Class types for java reflection
	  private Class[] isParTypes; // int, string
	  private Class[] iwbParTypes; // int, wiiButton
	  private Class[] inbParTypes; // int, nunchukButton
	  private Class[] iffParTypes; // int, float, float
	  private Class[] iffffParTypes; // int, float, float, float, float

	  /**
	   * Constructor of class WiiOsc
	   * @param p The main PApplet, pass "this" from within Processing 
	   */
	  public WiiOsc(PApplet p) {
	    this.p = p;
	    initParTypes();
	  }


	  /**
	   * Checks if the address pattern of the osc message conatsins the string "wii". 
	   * If it is, you can call process() later.
	   */
	  public boolean isWiiMessage(OscMessage oscMessage) {
	    return oscMessage.addrPattern().indexOf("/wii/") != -1;
	  } 

	  /**
	   * Here we do most of the work, we check which osc message we received (button, sensor, joystick) 
	   * and the source (wiimote, nunchuk). Later on we call a method in the main sketch and pass on 
	   * the needed parameters (i.e. the button which was pressed on the wii)
	   * @param oscMessage The OSC Message sent by OSCulator containing a Wii OSC Message. 
	   * Make sure to call isWiiMessage() before!
	   */
	  public void process(OscMessage oscMessage) {
	    String addrPattern = oscMessage.addrPattern();
	    String wiiNumber = addrPattern.substring(5, 6);
	    boolean isButton = addrPattern.indexOf(BUTTON) != -1;
	    boolean isNunchuk = addrPattern.indexOf(NUNCHUK) != -1;
	    // wii or nunchuk accel data
	    boolean isSensor = addrPattern.indexOf(ACCELERATOR) != -1;
	    boolean isJoystick = addrPattern.indexOf(JOYSTICK) != -1;
	    Reflection reflection = Reflection.getInstance();
	    // Now let's call the main sketch and invoke the appropriate functions.
	    // The Parameters will be extracted by getObjectArray, which also adds the wii number to the array. 
	    // wiimote button
	    if (isButton && !isNunchuk) {
	      float value = oscMessage.get(0).floatValue();
	      if(value == 1.0){ // button pressed
	        reflection.invokeMethodWithParams(p, WIIMOTE_BUTTON_FUNCTION, getObjectArray(oscMessage), iwbParTypes);
	      }
	      else if(value == 0.0){
	        reflection.invokeMethodWithParams(p, WIIMOTE_BUTTON_RELEASED_FUNCTION, getObjectArray(oscMessage), iwbParTypes);
	      }
	    }
	    // nunchuk button
	    else if(isButton && isNunchuk){
	      float value = oscMessage.get(0).floatValue();
	      if(value == 1.0){ // button pressed
	        reflection.invokeMethodWithParams(p, NUNCHUK_BUTTON_FUNCTION, getObjectArray(oscMessage), inbParTypes);
	      }
	      else if(value == 0.0){ // button released
	        reflection.invokeMethodWithParams(p, NUNCHUK_BUTTON_RELEASED_FUNCTION, getObjectArray(oscMessage), inbParTypes);
	      }
	    }
	    // wiimote sensor
	    else if(isSensor && !isNunchuk){
	      reflection.invokeMethodWithParams(p, WIIMOTE_SENSOR_FUNCTION, getObjectArray(oscMessage), iffffParTypes);
	    }
	    // nunchuk sensor
	    else if(isSensor && isNunchuk){
	      reflection.invokeMethodWithParams(p, NUNCHUK_SENSOR_FUNCTION, getObjectArray(oscMessage), iffffParTypes);
	    }
	    // nunchuk joystick
	    else if(isJoystick){
	      reflection.invokeMethodWithParams(p, NUNCHUK_JOYSTICK_FUNCTION, getObjectArray(oscMessage), iffParTypes);
	    } 
	  }
	  
	  
	  /**
	   * Extracts the Wii/Nunchuk button code, which is sent by OSCulator.
	   * @param addPattern The addressPattern of the OSC message sent by OSCulator
	   * @return The extracted Button String (e.g. "1", "Home", "Minus") 
	   */
	  String getButtonString(String addrPattern) {
	    return addrPattern.substring(addrPattern.lastIndexOf(SLASH)+1);
	  }
	  
	  
	  /**
	   * Returns an enum object of the Wii button according to the OSC string passed in. 
	   * This is dependent on how OSCulator names the buttons.
	   * @param sButton The OSC-button-code, you can use getButtonString to extract it from the OSC address pattern. 
	   * @return An enum object of the button
	   */
	  private WiiButton getWiiButton(String sButton){
		  for(WiiButton wiiButton: WiiButton.values()){
			  if(sButton.equals(wiiButton.toString())){
				  return wiiButton;
			  }
		  }
		  return null;
	  }
	  
	  
	  /**
	   * Returns an enum object of the Nunchuk button according to the OSC string passed in. 
	   * This is dependent on how OSCulator names the buttons. 
	   * @param sButton The OSC-button-code, you can use getButtonString to extract it from the OSC address pattern.
	   * @return An enum object of the button
	   */
	  private NunchukButton getNunchukButton(String sButton){
		  for(NunchukButton nunchukButton: NunchukButton.values()){
			  if(sButton.equals(nunchukButton.toString())){
				  return nunchukButton;
			  }
		  }
		  return null;
	  }
	  
	  
	  /**
	   * Returns the wii number. You can use up to four wiimotes, maybe even more. 
	   */
	  private int getWiiNumber(String addrPattern){
	    return Integer.parseInt(addrPattern.substring(5, 6));
	  }
	  
	  
	  /**
	   * Extracts the passed by values from the osc message and sticks them into an object array 
	   * The first element of the array is ALWAYS the wii number, the other values are the button (string), 
	   * sensor values (float) or coordinate values (float) 
	   */ 
	  Object[] getObjectArray(OscMessage oscMessage){
	    Object[] values = null;
	    String typetag = oscMessage.typetag();
	    // button press, [0]: wiiNumber, [1]: WiiButton / NunchukButton
	    if(typetag.equals(F_TYPETAG)){
	      values = new Object[2];
	      String sButton = getButtonString(oscMessage.addrPattern());
	      // if it is a nunchuk button
	      if(oscMessage.addrPattern().indexOf(NUNCHUK) != -1){
	    	  values[1] = getNunchukButton(sButton);
	      }
	      // WiiMote button
	      else{ 
	    	  values[1] = getWiiButton(sButton);
	      }
	    }
	    // joystick, [0]: wiiNumber, [1]: x, [2]: y
	    else if(typetag.equals(FF_TYPETAG)){
	      values = new Object[3];
	      values[1] = oscMessage.get(0).floatValue();
	      values[2] = oscMessage.get(1).floatValue();
	    }
	    // sensor, [0]: wiiNumber, [1]: pitch, [2]: roll, [3]: yaw, [4]: accel
	    else if(typetag.equals(FFFF_TYPETAG)){
	      values = new Object[5];
	      values[1] = oscMessage.get(0).floatValue();
	      values[2] = oscMessage.get(1).floatValue();
	      values[3] = oscMessage.get(2).floatValue();
	      values[4] = oscMessage.get(3).floatValue();
	    }
	    // add the wii number as first element in the array
	    values[0] = getWiiNumber(oscMessage.addrPattern());
	    return values;
	  }


	  /**
	   * Initializes the parameter type arrays we need for java reflection. 
	   * When we call a method in the main sketch, we need the types of parameters passed. 
	   */
	  private void initParTypes() {
	    // integer, string
	    isParTypes = new Class[2];
	    isParTypes[0] = Integer.TYPE;
	    isParTypes[1] = new String("").getClass();
	    // integer, WiiButton
	    iwbParTypes = new Class[2];
	    iwbParTypes[0] = Integer.TYPE;
	    iwbParTypes[1] = WiiButton.class;
	    // integer, NunchukButton
	    inbParTypes = new Class[2];
	    inbParTypes[0] = Integer.TYPE;
	    inbParTypes[1] = NunchukButton.class;
	    // integer, float, float
	    iffParTypes = new Class[3];
	    iffParTypes[0] = Integer.TYPE;
	    for (int i=1; i<iffParTypes.length; i++) {
	      iffParTypes[i] = Float.TYPE;
	    }
	    // integer, float, float, float, float
	    iffffParTypes = new Class[5];
	    iffffParTypes[0] = Integer.TYPE;
	    for (int i=1; i<iffffParTypes.length; i++) {
	      iffffParTypes[i] = Float.TYPE;
	    }
	  }
	}


}
