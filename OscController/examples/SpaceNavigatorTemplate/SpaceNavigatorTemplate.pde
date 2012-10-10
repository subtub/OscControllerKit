import oscP5.*;
import netP5.*;

import de.fhpotsdam.io.osc.spacenavigator.*;
import de.fhpotsdam.util.*;

OscP5 oscP5;
SpaceNavigatorOsc snOsc;

// OSC output port of OSCulator
int PORT_NUMBER = 9000;

void setup(){
  oscP5 = new OscP5(this, PORT_NUMBER);
  snOsc = new SpaceNavigatorOsc(this);
}

void draw(){}

void snButtonPressed(int snNumber, SpaceNavigatorButton button){
  println("A buton has been pressed on SpaceNavigator#" + snNumber + ": " + button.toString());
  switch(button){
    case ONE:
      // do something
      break;
    case TWO:
      // do something
      break;
  }
}

// There seems to be a problem with the drivers/OSCulator, wrong button presses all the time. 
// Don't use it!
void snButtonReleased(int snNumber, SpaceNavigatorButton button){
  //println("A button has been released on SpaceNavigator#" + snNumber + ": " + button.toString());
  switch(button){
    case ONE:
      // do something
      break;
    case TWO:
      // do something
      break;
  }
}


void snTranslated(int snNumber, float x, float y, float z){
  println("SpaceNavigator#" + snNumber + " has been translated to " + x + "/" + y + "/" + z);
}


void snRotated(int snNumber, float x, float y, float z){
  println("SpaceNavigator#" + snNumber + " has been rotated to " + x + "/" + y + "/" + z);
}


/** 
 * Incoming osc message are forwarded to the oscEvent method. 
 * If the message is coming from OSCulator (Space Navigator), 
 * the functions on top are automatically called.  
 */
void oscEvent(OscMessage theOscMessage) {
  if(snOsc.isSpaceNavigatorMessage(theOscMessage)){
    snOsc.process(theOscMessage);
  }
}
