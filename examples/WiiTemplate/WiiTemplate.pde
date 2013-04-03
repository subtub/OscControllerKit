/**
 * WiiTemplate
 * 
 * @Author Tim Pulver
 * @version 0.01
 *
 * Basic template for using the WiiOsc class with OSCulator and a WiiMote. 
 * You can use up to four WiiMotes / Nunchuks at the same time (Maybe even more). 
 */

import de.fhpotsdam.io.osc.wii.*;
import de.fhpotsdam.util.*;
import oscP5.*;
import netP5.*;

  
OscP5 oscP5;
WiiOsc wiiOsc;

// OSC output port of OSCulator
int PORT_NUMBER = 9000;


void setup() {
  oscP5 = new OscP5(this, PORT_NUMBER);
  wiiOsc = new WiiOsc(this);
}


void draw() {}


public void wiimoteButtonPressed(int wiiNumber, WiiButton button){
  println("Button pressed on Wii#" + wiiNumber + ", button: " + button);
  switch(button){
  	case UP:
  		// do something
  		break;
  	case DOWN:
  		// do something
  		break;
  	case LEFT:
  		// do something
  		break;
  	case RIGHT:
  		// do something
  		break;
  	case A:
  		// do something
  		break;
  	case B:
  		// do something
  		break;
  	case MINUS:
  		// do something
  		break;
  	case PLUS:
  		// do something
  		break;
  	case HOME:
  		// do something
  		break;
  	case ONE:
  		// do something
  		break;
  	case TWO:
  		// do something
  		break;
  }
}


public void wiimoteButtonReleased(int wiiNumber, WiiButton button){
  println("Button released on Wii#" + wiiNumber + ", button: " + button);
  switch(button){
  	case UP:
  		// do something
  		break;
  	case DOWN:
  		// do something
  		break;
  	case LEFT:
  		// do something
  		break;
  	case RIGHT:
  		// do something
  		break;
  	case A:
  		// do something
  		break;
  	case B:
  		// do something
  		break;
  	case MINUS:
  		// do something
  		break;
  	case PLUS:
  		// do something
  		break;
  	case HOME:
  		// do something
  		break;
  	case ONE:
  		// do something
  		break;
  	case TWO:
  		// do something
  		break;
  }
}


public void nunchukButtonPressed(int nunchukNumber, NunchukButton button){
  println("Button pressed on Nunchuk#" + nunchukNumber + ", button: " + button);
  switch(button){
  	case C:
  		// do something
  		break;
  	case Z:
  		// do something
  		break;
  }
}


public void nunchukButtonReleased(int nunchukNumber, NunchukButton button){
  println("Button released on Nunchuk#" + nunchukNumber + ", button: " + button);
  switch(button){
  	case C:
  		// do something
  		break;
  	case Z:
  		// do something
  		break;
  }
}


/**
 * To see what's the differencne between pitch, roll, yaw and accel, have a look here:
 * http://www.osculator.net/doc/faq:wiimote (-> "What are Pitch, Yaw, and Roll?")
 */
public void wiimoteSensorChanged(int wiiNumber, float pitch, float roll, float yaw, float accel){
  //println("Sensor data changed on Wii#" + wiiNumber + ", pitch: " + pitch + ", roll: " + roll + ", yaw: " + yaw + ", accel: " + accel);
}


/**
 * To see what's the differencne between pitch, roll, yaw and accel, have a look here:
 * http://www.osculator.net/doc/faq:wiimote (-> "What are Pitch, Yaw, and Roll?")
 */
public void nunchukSensorChanged(int nunchukNumber, float pitch, float roll, float yaw, float accel){
  //println("Sensor data changed on Nunchuk#" + nunchukNumber + ", pitch: " + pitch + ", roll: " + roll + ", yaw: " + yaw + ", accel: " + accel);
}


public void nunchukJoystickChanged(int nunchukNumber, float x, float y){
  //println("Joystick changed on Nunchuk#" + nunchukNumber + ", x: " + x + ", y: " + y);
}


/** 
 * Incoming osc message are forwarded to the oscEvent method. 
 * If the message is coming from OSCulator (WIIMote / Nunchuk), 
 * the functions on top are automatically called.  
 */
void oscEvent(OscMessage theOscMessage) {
  if(wiiOsc.isWiiMessage(theOscMessage)){
    wiiOsc.process(theOscMessage);
  }
}

