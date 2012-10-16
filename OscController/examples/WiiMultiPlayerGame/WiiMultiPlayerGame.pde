import de.fhpotsdam.io.osc.wii.WiiOsc;
import oscP5.OscP5;

int OSCULATOR_OUT_PORT = 9000;
int OSCULATOR_IN_PORT = 8000;

OscP5 oscP5;
WiiOsc wiiOsc;

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
    vibrate = !vibrate;
    //wiiOsc.vibrate(3, vibrate);
    wiiOsc.vibrateForMillis(3, 500);
    break;
  }
  wiiOsc.led(WII_NUMBER, led1, led2, led3, led4);
}

