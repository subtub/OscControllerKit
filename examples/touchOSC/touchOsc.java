/**
 *  wrongPowder is developed by wrong-entertainment & powder
 *
 *
 *  Copyright 2011 Paul Vollmer & Tim Pulver
 *  paulvollmer.net
 *  vollmerpaul@yahoo.de
 *  timpulver.de
 *  pulver.tim@googlemail.com
 * 
 *  This file is part of wrongPowder library.
 *
 *  wrongPowder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with wrongPowder. If not, see <http://www.gnu.org/licenses/>.
 * 
 *  @author		##author##
 *  @modified	##date##
 *  @version	##version##
 */


package wrongPowder.touchosc;


import oscP5.*;
import netP5.*;



public class Osc implements OscEventListener {
	
	//private static final OscEventListener t = null;
	public OscP5 oscP5;
	public OscMessage message;
	public NetAddress net;
	
	
	/**
	 * A Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 *
	 * @param ip
	 *        The IP to receive.
	 * @param send
	 *        The Port to send.
	 * @param receive
	 *        The Port to receive.
	 */
	public Osc(){//String ip, int portSend, int portReceive) {
		// start oscP5, listening for incoming messages at port 1234
		//oscP5 = new OscP5(this, portSend);
		//TestListener t = new TestListener();
		//oscP5.addListener(t);
		// myRemoteLocation is a NetAddress. a NetAddress takes 2 parameters,
		// an ip address and a port number. myRemoteLocation is used as parameter in
		// oscP5.send() when sending osc packets to another computer, device, 
		// application. usage see below. for testing purposes the listening port
		// and the port of the remote location address are the same, hence you will
		// send messages back to this sketch.
		//net = new NetAddress(ip, portReceive);
	}
	
	
	public void oscEvent(OscMessage theEvent) {
	    int a = theEvent.get(0).intValue();
	    System.out.println("\ncustom OscEventListener Test\nreceived an osc message\nvalue at index 0 (int expected) : "+a);
	  }
	  
	  public void oscStatus(OscStatus theStatus) {
		  System.out.println("osc status : "+theStatus.id());
	  }  
	
	
	
	/**
	 * Device Accelerometer X value
	 *
	 * @return float
	 */
	public float accX(OscMessage msg) {
		float accelerometerX = 0;
		// check accelerator value and get the values
		if(msg.checkAddrPattern("/accxyz") == true) {
			// check if the typetag is the right one.
			if(msg.checkTypetag("fff")) {
				accelerometerX = msg.get(0).floatValue();
			}
		}
		return accelerometerX;
	}
  
  
	/**
	 * Device Accelerometer Y value
	 *
	 * @return float
	 */
	public float accY(OscMessage msg) {
		float accelerometerY = 0;
		// check accelerator value and get the values
		if(msg.checkAddrPattern("/accxyz") == true) {
			// check if the typetag is the right one.
			if(msg.checkTypetag("fff")) {
				accelerometerY = msg.get(1).floatValue();
			}
		}
		return accelerometerY;
	}
  
  
	/**
	 * Device Accelerometer Z value
	 *
	 * @return float
   */
  public float accZ(OscMessage msg) {
    float accelerometerZ = 0;
    // check accelerator value and get the values
    if(msg.checkAddrPattern("/accxyz") == true) {
      // check if the typetag is the right one.
      if(msg.checkTypetag("fff")) {
        accelerometerZ = msg.get(2).floatValue();
      }
    }
    return accelerometerZ;
  }
  
  
  // TODO /////////////////////////////////////////////////
  /**
   * getPage
   * get the page name
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   */
  /*public String getPage(OscMessage msg, String oscName) {
    if(msg.checkAddrPattern(oscName) == true) {
      if(msg.checkTypetag("f")) {
        page = msg.get(0).floatValue();
      }
    }
    return page;
  }*/
  
  
  /**
   * getValue
   * get a float value
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @return float
   */
  public float getValue(OscMessage msg, String oscName) {
	  float value = 0;
	  if(msg.checkAddrPattern(oscName) == true) {
	      if(msg.checkTypetag("f")) {
	        System.out.println(msg.get(0).floatValue());
	        value = msg.get(0).floatValue();
	      }
	    }
    return value;
  }
  
  /**
   * selectPage
   * Though not configurable controls, the pages in a TouchOSC layout can be addressed
   * with OSC messages. Sending an OSC message to TouchOSC with only the page name
   * as the address will make that page the active page and display it on the device.
   * info @ http://hexler.net/docs/touchosc-controls/
   *
   * @param oscName
   *        TouchOSC will make the page named <oscName> the active page.
   */
  public void selectPage(String oscName) {
    //message = new OscMessage(oscName);
    //osc.send(message, net);
  }
  
  
  /**
   * setLED
   * Incoming messages are mapped to the control’s value range and
   * update the brightness of the LED display.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlValue
   *        int value
   */
  public void setLED(OscP5 tempOsc, String oscName, int controlValue) {
    setLED(tempOsc, oscName, (float)controlValue);
  }
  
  
  /**
   * setLED
   * Incoming messages are mapped to the control’s value range and
   * update the brightness of the LED display.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlValue
   *        float value
   */
  public void setLED(OscP5 tempOsc, String oscName, float controlValue) {
    sendMessage(tempOsc, oscName, controlValue);
  }
  
  
  /**
   * setLabel
   * Values of incoming messages are displayed as text. Both textual and numeric values
   * can be received and displayed.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param lableName
   *        int value
   */
  public void setLabel(OscP5 tempOsc, String oscName, int lableName) {
    setLabel(tempOsc, oscName, (float)lableName);
  }
  
  
  /**
   * setLabel
   * Values are displayed as text. Both textual and numeric values can be send.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param lableName
   *        float value
   */
  public void setLabel(OscP5 tempOsc, String oscName, float lableName) {
    sendMessage(tempOsc, oscName, lableName);
  }
  
  
  /**
   * setLabel
   * Values are displayed as text. Both textual and numeric values can be send.
   * do not use Ä, ä, Ö, ö, Ü, ü and ß
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param lableName
   *        String value
   */
  public void setLabel(OscP5 tempOsc, String oscName, String lableName) {
    sendMessage(tempOsc, oscName, lableName);
  }
  
  
  /**
   * setButton
   * Set the control state/value of Push/Toggle Buttons.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlState
   *        int
   */
  public void setButton(OscP5 tempOsc, String oscName, int controlState) {
    sendMessage(tempOsc, oscName, controlState);
  }
  
  
  /**
   * setXYpad
   * Set the control x and y value of XY Pad.
   * Both x and y values use the same value range.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlX
   *        x axis integer value of the control
   * @param controlY
   *        y axis integer value of the control
   */
  public void setXYpad(String oscName, int controlX, int controlY) {
    setXYpad(oscName, (int)controlX, (int)controlY);
  }
  
  
  /**
   * setXYpad
   * Set the control x and y value of XY Pad.
   * Both x and y values use the same value range.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlX
   *        x axis float value of the control
   * @param controlY
   *        y axis float value of the control
   */
  public void setXYpad(OscP5 tempOsc, String oscName, float controlX, float controlY) {
    message = new OscMessage(oscName);
    message.add(controlX);
    message.add(controlY);
    tempOsc.send(message, net);
  }
  
  
  /**
   * setFader
   * Set the control value of Fader.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlValue
   *        integer value
   */
  public void setFader(OscP5 tempOsc, String oscName, int controlValue) {
    setFader(tempOsc, oscName, (float)controlValue);
  }
  
  
  /**
   * setFader
   * Set the control value of Fader.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlValue
   *        float value
   */
  public void setFader(OscP5 tempOsc, String oscName, float controlValue) {
    sendMessage(tempOsc, oscName, controlValue);
  }
  
  
  /**
   * setRotary
   * Set the control value of Rotary.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlValue
   *        integer value
   */
  public void setRotary(OscP5 tempOsc, String oscName, int controlValue) {
    setRotary(tempOsc, oscName, (float)controlValue);
  }
  
  
  /**
   * setRotary
   * Set the control value of Rotary.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlValue
   *        float value
   */
  public void setRotary(OscP5 tempOsc, String oscName, float controlValue) {
    sendMessage(tempOsc, oscName, controlValue);
  }
  
  
  /**
   * setMultiToggle
   * Set the control state/value of all Multi-Toggle element.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param rows
   *        number of Multi-Toggle rows
   * @param cols
   *        number of Multi-Toggle columns
   * @param controlState
   *        int
   */
  public void setMultiToggle(OscP5 tempOsc, String oscName, int rows, int cols, int controlState) {
    message = new OscMessage(oscName);
    for(int i=0; i<rows; i++) {
      for(int j=0; j<cols; j++) {
        message.add(controlState);
      }
    }
    tempOsc.send(message, net);
  }
  
  
  /**
   * setMultiToggle
   * Set the control state/value of each Multi-Toggle element.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param rows
   *        number of Multi-Toggle rows
   * @param cols
   *        number of Multi-Toggle columns
   * @param controlState
   *        int array for each Button element.
   */
  public void setMultiToggle(OscP5 tempOsc, String oscName, int rows, int cols, int[] controlState) {
    message = new OscMessage(oscName);
    for(int i=0; i<rows; i++) {
      for(int j=0; j<cols; j++) {
        message.add(controlState[i+j]);
      }
    }
    tempOsc.send(message, net);
  }
  
  
  // TODO ////////////////////////////////////////////////////////////////////////////////////////
  /**
   * setMultiToggle
   * Set the control state/value of each Multi-Toggle element.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlState
   *        int array for each Button element.
   *
  public void setMultiToggle(String oscName, int[][] controlState) {
    print("### ["+year()+"/"+month()+"/"+day()+" "+hour()+":"+minute()+":"+second()+"] TouchOSC Multi-Toggle "+oscName+" set to ");
    
    myMessage = new OscMessage(oscName);
    for(int i=0; i<controlState.length; i++) {
      for(int j=0; j<controlState[0].length; j++) {
        myMessage.add(controlState[i][j]);
        
        print(controlState[i][j]+", ");
      }
    }
    osc.send(myMessage, net);
    
    println();
  }*/
  
  
  /**
   * setMultiFader
   * Set the control state/value of all Multi-Fader element.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param rows
   *        number of Multi-Fader
   * @param controlValue
   *        int value
   */
  public void setMultiFader(OscP5 tempOsc, String oscName, int num, int controlValue) {
    setMultiFader(tempOsc, oscName, num, (float)controlValue);
  }
  
  
  /**
   * setMultiFader
   * Set the control state/value of all Multi-Fader element.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param rows
   *        number of Multi-Fader
   * @param controlValue
   *        float value
   */
  public void setMultiFader(OscP5 tempOsc, String oscName, int num, float controlValue) {
    message = new OscMessage(oscName);
    for(int i=0; i<num; i++) {
      message.add(controlValue);
    }
    tempOsc.send(message, net);
  }
  
  
  /**
   * setMultiFader
   * Set the control state/value of each Multi-Fader element.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlValue
   *        int array for each Fader element.
   */
  public void setMultiFader(OscP5 tempOsc, String oscName, int[] controlValue) {
    message = new OscMessage(oscName);
    for(int i=0; i<controlValue.length; i++) {
      message.add(controlValue[i]);
    }
    tempOsc.send(message, net);
  }
  
  
  /**
   * setMultiFader
   * Set the control state/value of each Multi-Fader element.
   * info @ http://hexler.net/docs/touchosc-controls-reference/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlValue
   *        float array for each Fader element.
   */
  public void setMultiFader(OscP5 tempOsc, String oscName, float[] controlValue) {
    message = new OscMessage(oscName);
    for(int i=0; i<controlValue.length; i++) {
      message.add(controlValue[i]);
    }
    tempOsc.send(message, net);
  }
  
  
  /**
   * sendMessage
   * Send a Integer value to the TouchOSC control element.
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param msg
   *        int to be send.
   */
  public void sendMessage(OscP5 tempOsc, String oscName, int msg) {
    message = new OscMessage(oscName);
    message.add(msg);
    tempOsc.send(message, net);
  }
  
  
  /**
   * sendMessage
   * Send a Float value to the TouchOSC control element.
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param msg
   *        float to be send.
   */
  public void sendMessage(OscP5 tempOsc, String oscName, float msg) {
    message = new OscMessage(oscName);
    message.add(msg);
    tempOsc.send(message, net);
  }
  
  
  /**
   * sendMessage
   * Send a String value to the TouchOSC control element.
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param msg
   *        String to be send.
   */
  public void sendMessage(OscP5 tempOsc, String oscName, String msg) {
    message = new OscMessage(oscName);
    message.add(msg);
    tempOsc.send(message, net);
  }
  
  
  /**
   * vibrate
   * Triggers device vibration (iPhone only)
   * info @ http://hexler.net/docs/touchosc-controls/
   */
  public void vibrate(OscP5 tempOsc) {
    message = new OscMessage("/vibrate");
    tempOsc.send(message, net);
  }
  
  
  /**
   * visible
   * * Set TouchOSC control to be visible / invisible.
   * info @ http://hexler.net/docs/touchosc-controls/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlStatus
   *        Status of the TouchOSC control.
   *        0 set the control to be invisible
   *        1 set the control to be visible
   */
  public void visible(OscP5 tempOsc, String oscName, int controlStatus) {
    sendMessage(tempOsc, oscName+"/visible", controlStatus);
  }
  
  
  /**
   * show
   * Set TouchOSC control to be visible.
   * info @ http://hexler.net/docs/touchosc-controls/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   */
  public void show(OscP5 tempOsc, String oscName) {
    sendMessage(tempOsc, oscName+"/visible", 1);
  }
  
  
  /**
   * hide
   * Set TouchOSC control to be invisible.
   * info @ http://hexler.net/docs/touchosc-controls/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   */
  public void hide(OscP5 tempOsc, String oscName) {
    sendMessage(tempOsc, oscName+"/visible", 0);
  }
  
  
  /**
   * setColor
   * Change a control’s color palette to <color>. Possible values for the color parameter
   * are all the available color settings for the Color property in the TouchOSC editor.
   * Available colors are "red", "green", "blue", "yellow", "purple", "gray" and "orange".
   * info @ http://hexler.net/docs/touchosc-controls/
   *
   * @param oscName
   *        OSC name of the TouchOSC control element.
   * @param controlColor
   *        String of available color property.
   */
  public void setColor(OscP5 tempOsc, String oscName, String controlColor) {
    sendMessage(tempOsc, oscName+"/color", controlColor);
  }
  
  
}
