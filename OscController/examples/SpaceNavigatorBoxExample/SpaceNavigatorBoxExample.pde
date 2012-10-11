/**
 * SpaceNavigatorBoxExample
 *
 * This is an example showing the behaviour of SpaceNavigator.
 * Based on a gist by Github user brysonian.
 *  
 * For this to run, you need 
 * - SpaceNavigator Controller (http://www.3dconnexion.com/products/spacenavigator.html)
 * - oscP5 library (http://www.sojamo.de/libraries/oscP5/)
 * - OSCulator - Mac only (http://www.osculator.net/)
 * 
 * For additional information have a look at the OSCulator SpaceNavigator manual: 
 * http://www.osculator.net/doc/manual:spacenavigator
 *
 * Authors: Paul Vollmer, Tim Pulver
 */

import de.fhpotsdam.io.osc.spacenavigator.*;
import oscP5.*;
import processing.opengl.*;

OscP5 oscP5;
SpaceNavigatorOsc snOsc;

PVector snTranslation; // box translation
PVector snRotation; // box rotation
int boxSize;

int PORT_NUMBER = 9000; // OSC output port of OSCulator

void setup(){
  size(400,400, OPENGL);
  oscP5 = new OscP5(this, PORT_NUMBER);
  snOsc = new SpaceNavigatorOsc(this);
  
  snTranslation = new PVector();
  snRotation = new PVector();

  boxSize = 100;
}

void draw(){
  background(0);
  lights();
  pushMatrix();
  translate(200+snTranslation.x, 200+snTranslation.y, 0+snTranslation.z);
  rotateX(snRotation.x);
  rotateY(snRotation.y);
  rotateZ(snRotation.z);
  box(boxSize);
  popMatrix();
}

/**
 * This gets called whenever a button on the SpaceNavigator is pressed
 * snNumber is the SpaceNavigator number, if there are more than one, 
 * currently OSCulator only supports one. Button is either ONE or TWO.
 */
void snButtonPressed(int snNumber, SpaceNavigatorButton button){
  switch(button){
    case ONE:
      boxSize+=5; // make box bigger
      break;
    case TWO:
      boxSize-=5; // make box smaller
      break;
  }
}

/*
 * This gets called when the SpaceNavigator has been translated / one axis has been moved.
 * snNumber is the SpaceNavigator number, if there are more than one, 
 * currently OSCulator only supports one. 
 * Default values: 0.5/0.5/0.5
 */
void snTranslated(int snNumber, float x, float y, float z){
  // translate box position
  snTranslation.x = map(x, 0, 1, -width/2, width/2);
  snTranslation.y = map(y, 0, 1, -height/2, height/2);
  snTranslation.z = map(z, 0, 1, 250, -250);
}


/*
 * This gets called when the SpaceNavigator has been rotated around an axis.
 * snNumber is the SpaceNavigator number, if there are more than one, 
 * currently OSCulator only supports one.
 * Default values: 0.5/0.5/0.5
 */
void snRotated(int snNumber, float x, float y, float z){
  // rotate box
  snRotation.x = map(x, 0, 1, PI,  -PI);
  snRotation.y = map(y, 0, 1, PI,  -PI);
  snRotation.z = map(z, 0, 1, -PI,  PI);
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
