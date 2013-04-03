# OscControllerKit  

## General Information  
OscControllerKit simplifies the usage of certain controllers in your [Processing](processing.org) projects. It depends on [OSCulator](http://www.osculator.net), a Mac OS X application, which forwards controller data via [OSC (Open Sound Control)](http://opensoundcontrol.org/introduction-osc). OscContollerKit uses the OSC signals sent by OSCulator and adds an event layer similar to the Processing syntax.

GitHub project page:  
[subtub.github.com/OscControllerKit](http://subtub.github.com/OscControllerKit)    
Presentation:  
[subtub.github.com/OscControllerKit/Presentation](http://subtub.github.com/OscControllerKit/Presentation)  

##Supported Controllers
- WiiMote / Wii Remote Wii Nunchuk
- 3Dconnexion SpaceNavigator

## Installation
Download the latest release from [sourceforge](https://sourceforge.net/projects/osckit/files/) and install it like described here: [How to install a contributed library](http://wiki.processing.org/w/How_to_Install_a_Contributed_Library)

## Controller usage

### WiiMote
For using a WiiMote with OscControllerKit, you need [OSCulator](http://www.osculator.net) first. Have a look at the [OSCulator WiiMote Wiki](http://www.osculator.net/doc/faq:wiimote) on how to set up OSCulator correctly. 

### Space Navigator
- [Simple example of connecting processing to a SpaceNavigator via OSCulator](https://gist.github.com/100144)
- [OSCulator Space Navigator Manual](http://www.osculator.net/doc/manual:spacenavigator)

## Changelog  
**0.1.1**  
- Initial release
- Working with Processing 1.5.1
 
## Future Ideas
- Add support for more controllers
  - PlayStation Move ([GitHub Repo for PS Move SDK here](https://github.com/thp/psmoveapi))  
  - Apple Magic Trackpad (via [MultitouchPadOsc](https://github.com/wrongentertainment/MultitouchPadOsc/)) 
  - iPhone/iPad - TouchOsc  
  - Gamepads  

## Contributors  
[Paul Vollmer](http://wrong-entertainment.com)
[Tim Pulver](http://timpulver.de)

## License  
OscControllerKit is released under the MIT License: http://www.opensource.org/licenses/MIT    
