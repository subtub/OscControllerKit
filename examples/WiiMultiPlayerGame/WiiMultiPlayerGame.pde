import processing.core.PApplet;
import processing.core.PVector;
import de.fhpotsdam.io.osc.wii.NunchukButton;
import de.fhpotsdam.io.osc.wii.WiiOsc;
import oscP5.OscMessage;
import oscP5.OscP5;

int OSCULATOR_OUT_PORT = 9000;
int OSCULATOR_IN_PORT = 8000;

OscP5 oscP5;
WiiOsc wiiOsc;

int N_PLAYERS = 3;
PVector[] playerPos;
PVector[] joyVals;
int[] eatenCookies;
PVector cookie;
float acc = 10;



public void setup() {
  size(600, 600);
  smooth();
  noFill();
  oscP5 = new OscP5(this, OSCULATOR_OUT_PORT);
  wiiOsc = new WiiOsc(this);
  wiiOsc.setRemote(oscP5, "localhost", OSCULATOR_IN_PORT);

  initPlayerPositions();
  initCookiePosition();
  initJoyVals();
  eatenCookies = new int[N_PLAYERS];
  initLeds();
}

void initJoyVals(){
  joyVals = new PVector[N_PLAYERS];
  for(int i=0; i<N_PLAYERS; i++){
    joyVals[i] = new PVector(0, 0);
  }
}

void nunchukJoystickChanged(int nunchukNumber, float x, float y) {
  joyVals[nunchukNumber-1].x = map(x, 0, 1, -0.5, 0.5);
  joyVals[nunchukNumber-1].y = map(y, 0, 1, 0.5, -0.5);
}

void checkBoundaries(int playerNumber) {
  PVector p = playerPos[playerNumber];
  if (p.x > width-1) p.x = width-1;
  else if (p.x < 0) p.x = 0;
  if (p.y > height-1) p.y = height-1;
  if (p.y < 0) p.y = 0;
}


public void initLeds() {
  for (int i=0; i<N_PLAYERS; i++) {
    changeLed(i);
  }
}

public void initCookiePosition() {
  cookie = new PVector(random(0, width), random(0, height));
}

public void initPlayerPositions() {
  playerPos = new PVector[N_PLAYERS];
  for (int i=0; i<N_PLAYERS; i++) {
    playerPos[i] = new PVector(random(0, width), random(0, height));
  }
}

public void draw() {
  background(0);
  fill(255);
  updatePlayerPositions();
  for (int i=0; i<playerPos.length; i++) {
    ellipse(playerPos[i].x, playerPos[i].y, 50, 50);
  }
  fill(33, 200, 0);
  ellipse(cookie.x, cookie.y, 30, 30);
  checkIfEaten();
}

void updatePlayerPositions(){
  for(int i=0; i<N_PLAYERS; i++){
    playerPos[i].x += acc*joyVals[i].x;
    playerPos[i].y += acc*joyVals[i].y;
    checkBoundaries(i);
  }
}

void checkIfEaten() {
  for (int i=0; i<N_PLAYERS; i++) {
    if (samePosition(playerPos[i], cookie)) {
      eatenCookies[i]++;
      initCookiePosition();
      changeLed(i);
      wiiOsc.vibrateForMillis(i+1, 300);
    }
  }
}

void changeLed(int wiiNumber) {
  switch(eatenCookies[wiiNumber]) {
  case 0:
    wiiOsc.led(wiiNumber+1, false, false, false, false);
    break;
  case 1:
    wiiOsc.led(wiiNumber+1, true, false, false, false);
    break;
  case 2:
    wiiOsc.led(wiiNumber+1, true, true, false, false);
    break;
  case 3:
    wiiOsc.led(wiiNumber+1, true, true, true, false);
    break;
  case 4:
    wiiOsc.led(wiiNumber+1, true, true, true, true);
    break;
  }
}

boolean samePosition(PVector p1, PVector p2) {
  if (round(p1.x) == round(p2.x) && round(p2.x) == round(p2.x)) {
    return true;
  }
  return false;
}

/**
 	 * To see what's the differencne between pitch, roll, yaw and accel, have a look here:
 	 * http://www.osculator.net/doc/faq:wiimote (-> "What are Pitch, Yaw, and Roll?")
 	 */
void nunchukSensorChanged(int nunchukNumber, float pitch, float roll, float yaw, float accel) {
  //println("Sensor data changed on Nunchuk#" + nunchukNumber + ", pitch: " + pitch + ", roll: " + roll + ", yaw: " + yaw + ", accel: " + accel);
}


void nunchukButtonReleased(int nunchukNumber, NunchukButton button) {
  println("Button released on Nunchuk#" + nunchukNumber + ", button: " + button);
  switch(button) {
  case C:
    // do something
    break;
  case Z:
    // do something
    break;
  }
}


/** 
 	 * Incoming osc message are forwarded to the oscEvent method. 
 	 * If the message is coming from OSCulator (WIIMote / Nunchuk), 
 	 * the functions on top are automatically called.  
 	 */
public void oscEvent(OscMessage theOscMessage) {
  println("incoming osc message: " + theOscMessage.addrPattern());
  if (wiiOsc.isWiiMessage(theOscMessage)) {
    wiiOsc.process(theOscMessage);
  }
}

