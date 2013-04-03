//
// WiiOsc.java
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

package subtub.io.osc.wii;

/**
 * Class WiiOSc makes it easier to use Osc Messages sent by OSCulator. 
 * Using Java reflection, it will call functions in the main sketch like it is done on a normal key press.
 * 
 * @author Tim Pulver
 * @version 0.01
 */

import java.lang.reflect.Method;
import java.lang.Enum;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

import subtub.io.osc.wii.*;
import subtub.util.*;
import subtub.util.math.Conversion;
import subtub.util.time.ThreadTimer;
import processing.core.PApplet;
import oscP5.*;
import netP5.*;

public class WiiOsc {
  // osc address pattern strings
  private static String BUTTON = "button";
  private static String NUNCHUK = "nunchuk";
  private static String ACCELERATOR = "pry";
  private static String JOYSTICK = "joy";

  // nunchuk butons

  private static char SLASH = '/';
  
  // Function names, which need to be implemented in the main sketch
  private static String WIIMOTE_BUTTON_FUNCTION = "wiimoteButtonPressed";
  private static String WIIMOTE_BUTTON_RELEASED_FUNCTION = "wiimoteButtonReleased";
  private static String NUNCHUK_BUTTON_FUNCTION = "nunchukButtonPressed";
  private static String NUNCHUK_BUTTON_RELEASED_FUNCTION = "nunchukButtonReleased";
  private static String WIIMOTE_SENSOR_FUNCTION = "wiimoteSensorChanged";
  private static String NUNCHUK_SENSOR_FUNCTION = "nunchukSensorChanged";
  private static String NUNCHUK_JOYSTICK_FUNCTION = "nunchukJoystickChanged";
  
  // typetags are sent with every OSC message, for example "ff" means there are two float arguments
  private static String F_TYPETAG = "f";
  private static String FF_TYPETAG = "ff";
  private static String FFFF_TYPETAG = "ffff";
  
  // if only one wii is in use, this wii-number will be used
  private int defWiiNumber = 1;

  // parent sketch
  PApplet p;
  
  // for Osc sending
  private NetAddress myRemoteLocation;
  private String remoteIp;
  private int remotePort;
  private OscP5 oscP5;
  

  // Class types for java reflection
  private Class[] isParTypes; // int, string
  private Class[] iwbParTypes; // int, wiiButton
  private Class[] inbParTypes; // int, nunchukButton
  private Class[] iffParTypes; // int, float, float
  private Class[] iffffParTypes; // int, float, float, float, float
  
  // HashMap for timed vibration
  private HashMap<Integer, ThreadTimer> timers;
  
  // Logging
  private final static Logger LOGGER = Logger.getLogger(WiiOsc.class.getName());

  /**
   * Constructor of class WiiOsc
   * @param p The main PApplet, pass "this" from within Processing 
   */
  public WiiOsc(PApplet p) {
	  LOGGER.log(Level.FINEST, "Constructor called");
    this.p = p;
    initParTypes();
    timers = new HashMap<Integer, ThreadTimer>();
  }


  /**
   * Checks if the address pattern of the osc message conatsins the string "wii". 
   * If it is, you can call process() later.
   */
  public boolean isWiiMessage(OscMessage oscMessage) {
    return oscMessage.addrPattern().indexOf("/wii/") != -1;
  } 
  
  
  public void setRemote(OscP5 oscP5, String ip, int port){
	  this.oscP5 = oscP5;
	  this.remoteIp = ip;
	  this.remotePort = port;
	  myRemoteLocation = new NetAddress(ip, port);
  }
  
  
  /**
   * If only one wii is in use, you can call led() and vibrate() withou adding the wii number. 
   * The default wii number will be used instead. 
   * @param defWiiNumber
   */
  private void setDefaultWiiNumber(int defWiiNumber){this.defWiiNumber = defWiiNumber;}
  

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
   * Overloaded version of led(). 
   * This an be used if there is only one wii (default wii number is 1). 
   * @param led1
   * @param led2
   * @param led3
   * @param led4
   */
  public void led(boolean led1, boolean led2, boolean led3, boolean led4){
	  led(defWiiNumber, led1, led2, led3, led4);
  }
  
  
  /**
   * Overloaded version of led(). 
   * @param wiiNumber
   * @param led1
   * @param led2
   * @param led3
   * @param led4
   */
  public void led(int wiiNumber, boolean led1, boolean led2, boolean led3, boolean led4){
	  // we need to reverse the order of the led booleans and convert it to decimal
	  int revHexValue = Conversion.getInstance().boolArrayToDec(new boolean[] {led4, led3, led2, led1});
	  led(wiiNumber, revHexValue);
  }
  
  
  
  public void led(int wiiNumber, int decVal){
	  if(myRemoteLocation == null){
		  System.err.println("you need to call setRemote() before sending messages. "
				  + "if you want to send messages on the same machine, use 127.0.0.1 and the osc-imput-port");
	  }
	  OscMessage message = new OscMessage("/wii/" + wiiNumber + "/led/");
	  message.add(decVal);
	  oscP5.send(message, myRemoteLocation);
  }
  
  
  public void vibrate(int wiiNumber, boolean b){
	  if(myRemoteLocation == null){
		  LOGGER.log(Level.WARNING, "you need to call setRemote() before sending messages. "
				  + "if you want to send messages on the same machine, use 127.0.0.1 and the osc-imput-port");
	  }
	  OscMessage message = new OscMessage("/wii/" + wiiNumber + "/vibrate/");
	  message.add(b ? 1.0 : 0.0);
	  oscP5.send(message, myRemoteLocation);
  }
  
  
  /**
   * Creates a new thread, which terminates, when the time is over. 
   * @param wiiNumber
   * @param millis
   */
  public void vibrateForMillis(int wiiNumber, int millis){
	  vibrate(wiiNumber, true);
	  ThreadTimer timer = timers.get(wiiNumber); 
	  if(timer == null){
		  LOGGER.log(Level.FINE, "Creating new Timer");
		  timer = new VibrationTimer(this, millis, wiiNumber);
		  timers.put(wiiNumber, timer);
		  timer.start();
	  }
	  else{
		  LOGGER.log(Level.FINE, "Resetting timer");
		  timer = timers.get(wiiNumber);
		  timer.reset();
	  }
  }
  
  
  /**
   * Overloaded version of vibrate(), will use default wii number
   * @param b True: vibration on, false: vibration off
   */
  public void vibrate(boolean b){
	  vibrate(defWiiNumber, b);
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

