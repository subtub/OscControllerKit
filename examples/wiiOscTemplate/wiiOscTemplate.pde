/**
 * wiiOscTemplate
 * 
 * @Author Tim Pulver
 * @version 0.01
 *
 * Basic template for using the WiiOsc class with OSCulator and a WiiMote. 
 * You can use four WiiMotes / Nunchuks at the same time (Maybe even more). 
 */

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


void wiimoteButtonPressed(int wiiNumber, String button){
  println("Button pressed on Wii#" + wiiNumber + ", button: " + button);
  if(button.equals(WiiOsc.UP)){}
  else if(button.equals(WiiOsc.RIGHT)){}
  else if(button.equals(WiiOsc.DOWN)){}
  else if(button.equals(WiiOsc.LEFT)){}
  else if(button.equals(WiiOsc.A)){}
  else if(button.equals(WiiOsc.MINUS)){}
  else if(button.equals(WiiOsc.HOME)){}
  else if(button.equals(WiiOsc.PLUS)){}
  else if(button.equals(WiiOsc.ONE)){}
  else if(button.equals(WiiOsc.TWO)){}
  else if(button.equals(WiiOsc.B)){}
}


void nunchukButtonPressed(int nunchukNumber, String button){
  println("Button pressed on Nunchuk#" + nunchukNumber + ", button: " + button);
  if(button.equals(WiiOsc.C)){}
  else if(button.equals(WiiOsc.Z)){}
}

/**
 * To see what's the differencne between pitch, roll, yaw and accel, have a look here:
 * http://www.osculator.net/doc/faq:wiimote (-> "What are Pitch, Yaw, and Roll?")
 */
void wiimoteSensorChanged(int wiiNumber, float pitch, float roll, float yaw, float accel){
  //println("Sensor data changed on Wii#" + wiiNumber + ", pitch: " + pitch + ", roll: " + roll + ", yaw: " + yaw + ", accel: " + accel);
}

/**
 * To see what's the differencne between pitch, roll, yaw and accel, have a look here:
 * http://www.osculator.net/doc/faq:wiimote (-> "What are Pitch, Yaw, and Roll?")
 */
void nunchukSensorChanged(int nunchukNumber, float pitch, float roll, float yaw, float accel){
  //println("Sensor data changed on Nunchuk#" + nunchukNumber + ", pitch: " + pitch + ", roll: " + roll + ", yaw: " + yaw + ", accel: " + accel);
}


void nunchukJoystickChanged(int nunchukNumber, float x, float y){
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


