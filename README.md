# OscControllerKit  

## General Information  
OscControllerKit simplifies the usage of certain controllers in your [Processing](processing.org) projects. It depends on [oscP5](http://www.sojamo.de/libraries/oscP5/) and [OSCulator](http://www.osculator.net), a Mac OS X application, which forwards controller data via [OSC (Open Sound Control)](http://opensoundcontrol.org/introduction-osc). OscContollerKit uses the OSC signals sent by OSCulator and adds an event layer similar to the Processing syntax.

GitHub project page:  
[subtub.github.com/OscControllerKit](http://subtub.github.com/OscControllerKit)    
Presentation:  
[subtub.github.com/OscControllerKit/Presentation](http://subtub.github.com/OscControllerKit/Presentation)  

##Supported Controllers
- WiiMote / Wii Remote Wii Nunchuk (working with up to 4 WiiMotes per Computer – maybe even more)
- 3Dconnexion SpaceNavigator (working with one per computer)

## Installation
Download the latest release from [sourceforge](https://sourceforge.net/projects/osckit/files/) and install it like described here: [How to install a contributed library](http://wiki.processing.org/w/How_to_Install_a_Contributed_Library)

## Controller usage

### WiiMote
For using a WiiMote with OscControllerKit, you need [OSCulator](http://www.osculator.net) first. Open the file **WiiMote_Osculator.oscd** from the `examples/WiiTemplate` folder in OSCulator and test if the WiiMote signals are coming in. If you are having problems connnecting the WiiMote(s), have a look at the [OSCulator WiiMote Wiki](http://www.osculator.net/doc/faq:wiimote) on how to set up OSCulator correctly. 
If everything is working correctly pressing a button on the WiiMote should result in a green status icon flashing up next to the message name. If you see a yellow icon, it means that a signal is coming in but OSCulator does not forward it to a remote destination.
Now upen up the WiiTemplate example and press some buttons. You should see a console message like this now: `Button pressed on Wii#1, button: " A`.  
Yay, everything works!

#### Functions
For a full reference, have a look at the [Javadocs](LINK ZU DEN JAVADOCS HIIIIIIER).

##### Button imput
`void wiimoteButtonPressed(int wiiNumber, WiiButton button)`
`void wiimoteButtonReleased(int wiiNumber, WiiButton button)`
`void nunchukButtonPressed(int nunchukNumber, NunchukButton button)`
`void nunchukButtonReleased(int nunchukNumber, NunchukButton button)`

##### Sensor input
`void wiimoteSensorChanged(int wiiNumber, float pitch, float roll, float yaw, float accel)`
`void nunchukSensorChanged(int nunchukNumber, float pitch, float roll, float yaw, float accel)`
`void nunchukJoystickChanged(int nunchukNumber, float x, float y)`

##### Turn WiiMote LEDs on/off
`void led(int wiiNumber, boolean led1, boolean led2, boolean led3, boolean led4)`

##### Turn WiiMote vibrator on/off
`void vibrate(int wiiNumber, boolean b)`

##### Vibrate for a certain time
`void vibrateForMillis(int wiiNumber, int millis)`

### Space Navigator
For using a SpaceNavigator with OscControllerKit, you need [OSCulator](http://www.osculator.net) first. Open the file **SpaceNavigator_Osculator.oscd** from the `examples/SpaceNavigatorTemplate` folder in OSCulator and test if the SpaceNavigator signals are coming in. If you are having problems connnecting the SpaceNavigator, have a look at the [OSCulator SpaceNavigator Wiki](http://www.osculator.net/doc/manual:spacenavigator) on how to set up OSCulator correctly. 
If everything is working correctly pressing a button on the SpaceNavigator should result in a green status icon flashing up next to the message name. If you see a yellow icon, it means that a signal is coming in but OSCulator does not forward it to a remote destination.
Now upen up the SpaceNavigatorTemplate example and press some buttons. You should see a console message like this now: `Button pressed on SpaceNavigator#1, button: " A`.  
Yay, everything works!
 
#### Functions

#####Button input
`void snButtonPressed(int snNumber, SpaceNavigatorButton button)`

##### Sensor input
`void snTranslated(int snNumber, float x, float y, float z)`  
`void snRotated(int snNumber, float x, float y, float z)`  

## Changelog  
**0.1.1**  
- Initial release
- Working with Processing 1.5.1
- Support for WiiMote
- Support for SpaceNavigator
 
## Future Ideas
- Add support for more controllers
  - PlayStation Move ([GitHub Repo for PS Move SDK here](https://github.com/thp/psmoveapi))  
  - Apple Magic Trackpad (via [MultitouchPadOsc](https://github.com/wrongentertainment/MultitouchPadOsc/)) 
  - iPhone/iPad - TouchOsc  
  - Gamepads  

## Contributors  
[Paul Vollmer](http://wrong-entertainment.com)
[Tim Pulver](http://timpulver.de)

## Donations
If you like what we are doing here, you can support us with a tip on [GitTip](https://www.gittip.com/sub_tub/).

## License  
OscControllerKit is released under the MIT License: http://www.opensource.org/licenses/MIT    
