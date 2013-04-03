/**
 * Class WiiOSc tries to take away the need to parse the Osc Messages sent by OSCulator manually. 
 * Using Java reflection, it will call functions in the main sketch like it is done on a normal key press.
 * 
 * @author Tim Pulver
 * @version 0.01
 *
 * TODO: Osc Messages are processed twice...!?!?!? Mayve because of localhost!?
 */

import java.lang.reflect.Method;
import processing.core.PApplet;
import oscP5.*;
import netP5.*;

class WiiOsc {
  // osc address pattern strings
  private static String BUTTON = "button";
  private static String NUNCHUK = "nunchuk";
  private static String ACCELERATOR = "pry";
  private static String JOYSTICK = "joy";
  // wiimote buttons
  public static String ONE = "1";
  public static String TWO = "2";
  public static String A = "A";
  public static String B = "B";
  public static String DOWN = "Down";
  public static String HOME = "Home";
  public static String LEFT = "Left";
  public static String MINUS = "Minus"; 
  public static String PLUS = "Plus";
  public static String RIGHT = "Right";
  public static String UP = "Up";
  // nunchuk butons
  public static String C = "C";
  public static String Z = "Z";

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

  // parent sketch
  PApplet p;

  // Class types for java reflection
  private Class[] isParTypes; // int, string
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
    // Now let's call the main sketch and invoke the appropriate functions.
    // The Parameters will be extracted by getObjectArray, which also adds the wii number to the array. 
    // wiimote button
    if (isButton && !isNunchuk) {
      float value = oscMessage.get(0).floatValue();
      if(value == 1.0){ // button pressed
        invokeMethodWithParams(p, WIIMOTE_BUTTON_FUNCTION, getObjectArray(oscMessage), isParTypes);
      }
      else if(value == 0.0){
        invokeMethodWithParams(p, WIIMOTE_BUTTON_RELEASED_FUNCTION, getObjectArray(oscMessage), isParTypes);
      }
    }
    // nunchuk button
    else if(isButton && isNunchuk){
      float value = oscMessage.get(0).floatValue();
      if(value == 1.0){ // button pressed
        invokeMethodWithParams(p, NUNCHUK_BUTTON_FUNCTION, getObjectArray(oscMessage), isParTypes);
      }
      else if(value == 0.0){ // button released
        invokeMethodWithParams(p, NUNCHUK_BUTTON_RELEASED_FUNCTION, getObjectArray(oscMessage), isParTypes);
      }
    }
    // wiimote sensor
    else if(isSensor && !isNunchuk){
      invokeMethodWithParams(p, WIIMOTE_SENSOR_FUNCTION, getObjectArray(oscMessage), iffffParTypes);
    }
    // nunchuk sensor
    else if(isSensor && isNunchuk){
      invokeMethodWithParams(p, NUNCHUK_SENSOR_FUNCTION, getObjectArray(oscMessage), iffffParTypes);
    }
    // nunchuk joystick
    else if(isJoystick){
      invokeMethodWithParams(p, NUNCHUK_JOYSTICK_FUNCTION, getObjectArray(oscMessage), iffParTypes);
    } 
  }
  
  
  /**
   * Returns the Button, which was pressed as sent by OSCulator. 
   */
  String getButton(String addrPattern) {
    return addrPattern.substring(addrPattern.lastIndexOf(SLASH)+1);
  }
  
  
  /**
   * Returns the wii number. You can use up to four wiimotes, maybe even more. 
   */
  int getWiiNumber(String addrPattern){
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
    // button press, [0]: wiiNumber, [1]: Button String
    if(typetag.equals(F_TYPETAG)){
      values = new Object[2];
      values[1] = getButton(oscMessage.addrPattern());
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
  

  /**
   @param o  the object which contains the method to call, 
   when passing 'this' from within the processing main sketch,
   all functions in your main sketch will be recognized 
   (setup(), draw(),..., custom methods)  
   @param m  the name of the method to call without "()"
   @param params Contains the parameters to pass to function m
   @param parTypes[] Contains the classes of the arguments to pass, 
   as params is of type object, we need this, too 
   */
  public void invokeMethodWithParams(Object o, String m, Object[] params, Class[] parTypes) {
    if (o==null || m=="" || m==null) {
      System.out.println("There was an error with your arguments");
      return;
    }
    try {
      Class c = o.getClass();
      Method method = c.getDeclaredMethod(m, parTypes);
      method.invoke(o, params);
    }
    catch (Throwable e) {
      System.err.println(e);
    }
  }
}

