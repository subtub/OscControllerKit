/**
 * WiiLedAndVibrateMultiExample
 * 
 * This example shows, how to control the LEDs and motor of your WiiMote(s).
 *
 * Use the following keys:
 *  1: LED1 on/off 
 *  2: LED2 on/off
 *  3: LED3 on/off
 *  4: LED4 on/off
 *  v: vibrate for x milliseconds / vibration on/off
 *
 * In order for this to work, you will need the library oscP5 and OSCulator (Mac only).
 * In OSCulator, you need to load our settings file, or manually make these changes:
 * Runs this sketch and press the buttons 1 and v. Now you will see the incoming OSC message
 * from within OSCulator, like "/wii/1/led/" and "/wii/1/vibrate/". Setup the event types:
 *
 * MESSAGE          EVENT TYPE      Value
 * ---------------------------------------------------
 * /wii/1/led/      LEDs            Numeric (Wiimote 1)
 * /wii/1/vibrate/  Vibrate         Wiimote 1
 * Additionally set up the correct in-and-output ports.
 */

import subtub.io.osc.wii.WiiOsc;
import oscP5.OscP5;

int OSCULATOR_OUT_PORT = 9000;
int OSCULATOR_IN_PORT = 8000;

// Have a look at OSCulator: The number behind "/wii/" (the OSC messages from the wiimote) is your wiiNumber
// You have to edit this accordingly. If your are using more than one wiimote, just 
int WII_NUMBER = 3;

OscP5 oscP5;
WiiOsc wiiOsc;

boolean led1, led2, led3, led4;
boolean vibrate;


public void setup() {
  oscP5 = new OscP5(this, OSCULATOR_OUT_PORT);
  wiiOsc = new WiiOsc(this);
  wiiOsc.setRemote(oscP5, "localhost", OSCULATOR_IN_PORT);
  // switch all LEDs off
  wiiOsc.led(WII_NUMBER, false, false, false, false);
}


void draw() {
}


void keyPressed() {
  switch(key) {
  // swtich a specific LED if the button has been pressed
  case '1':
    led1 = !led1;
    break;
  case '2':
    led2 = !led2;
    break;
  case '3':
    led3 = !led3;
    break;
  case '4':
    led4 = !led4;
    break;
  case 'v':
    wiiOsc.vibrateForMillis(WII_NUMBER,  300);
    // use this instead to turn vibration on and off (no timing)
    //vibrate = !vibrate;
    //wiiOsc.vibrate(WII_NUMBER,  vibrate);
    break;
  }
  // send LED changes to the WiiMote
  wiiOsc.led(WII_NUMBER, led1, led2, led3, led4);
}

