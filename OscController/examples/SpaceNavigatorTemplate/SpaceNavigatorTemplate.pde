/**
 * SpaceNavigatorTemplate
 *
 * This is a blank template explaining the usage of SpaceNavigatorOsc. 
 */


// OscController imports
import de.fhpotsdam.io.osc.spacenavigator.*;
import de.fhpotsdam.util.*;
// OscP5 imports
// download it here: http://www.sojamo.de/libraries/oscP5/
import oscP5.*;
import netP5.*;

OscP5 oscP5;
SpaceNavigatorOsc snOsc;

// OSC output port of OSCulator
int PORT_NUMBER = 9000;

void setup(){
  oscP5 = new OscP5(this, PORT_NUMBER);
  snOsc = new SpaceNavigatorOsc(this);
}

void draw(){}

/**
 * This gets called whenever a button on the SpaceNavigator is pressed
 * snNumber is the SpaceNavigator number, if there are more than one, 
 * currently OSCulator only supports one. Button is either ONE or TWO.
 */
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

/*
 * This gets called when the SpaceNavigator has been translated / one axis has been moved. 
 * Default values: 0.5/0.5/0.5
 */
void snTranslated(int snNumber, float x, float y, float z){
  println("SpaceNavigator#" + snNumber + " has been translated to " + x + "/" + y + "/" + z);
}


/*
 * This gets called when the SpaceNavigator has been rotated around an axis.
 * Default values: 0.5/0.5/0.5
 */
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
