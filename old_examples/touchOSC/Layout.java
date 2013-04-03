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


import java.util.ArrayList;

import wrongPowder.gui.Tabs;
import processing.core.*;



public class Layout {
	
	// TouchOsc control color variables.
	// TODO Enum or something?
	public static int RED_ON     = 0xFFFF0000;
	public static int RED_OFF    = 0xFFFF0000;
	public static int GREEN_ON   = 0xFF00FF00;
	public static int GREEN_OFF  = 0xFF00FF00;
	public static int BLUE_ON    = 0xFF00D8D7;
	public static int BLUE_OFF   = 0xFF00D8D7;
	public static int YELLOW_ON  = 0xFFFFFF00;
	public static int YELLOW_OFF = 0xFFFFFF00;
	public static int PURPLE_ON  = 0xFFDC00FF;
	public static int PURPLE_OFF = 0xFFDC00FF;
	public static int GRAY_ON    = 0xFFD6D6D6;
	public static int GRAY_OFF   = 0xFFD6D6D6;
	public static int ORANGE_OFF = 0xFFFFB700;
	public static int ORANGE_ON  = 0xFFFFB700;
	
	// The width/height of the TouchOsc file.
	// Created by readMode() & readOrientation().
	private int layoutWidth;
	private int layoutHeight;
	
	// Create Tab constructor.
	private Tabs tab;
	
	
	// Control
	//private String[] numBatteryh;
	private ArrayList batteryh;
	//private int[] batteryhTabpage;             ############### Here we go
	private Batteryv[] batteryv;
	private Faderh[] faderh;
	private Faderv[] faderv;
	private Labelh[] labelh;
	private Labelv[] labelv;
	private Led[] led;
	private Multifaderh[] multifaderh;
	private Multifaderv[] multifaderv;
	private Multitoggle[] multitoggle;
	private Push[] push;
	private Rotaryh[] rotaryh;
	private Rotaryv[] rotaryv;
	private Timeh[] timeh;
	private Timev[] timev;
	private Toggle[] toggle;
	private Xy[] xy;
	
	
	private PApplet p;
	
	
	/**
	 * A Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 */
	public Layout(PApplet p) {
		System.out.println("Start Layout class");
		this.p = p;
	}
	
	
	public void init() {
		System.out.println("Start init");
		
		// Check file mode.
		readMode();
		
		
		// Create tab class array.
		tab = new Tabs(p);
		tab.init(File.numTabs(), 0, 0, layoutWidth, 40);
		
	    
		
		
		// Set Tab size to Class Array.
		batteryh = new ArrayList();
		//batteryhTabpage = new int[File.numTabs()];           ############### Here we go
		/*batteryv = new Batteryv[File.numBatteryv];
		faderh = new Faderh[File.numFaderh];
		faderv = new Faderv[File.numFaderv];
		labelh = new Labelh[File.numLabelh];
		labelv = new Labelv[File.numLabelv];
		led = new Led[File.numLed];
		multifaderh = new Multifaderh[File.numMultifaderh];
		multifaderv = new Multifaderv[File.numMultifaderv];
		multitoggle = new Multitoggle[File.numMultitoggle];
		push = new Push[File.numPush];
		rotaryh = new Rotaryh[File.numRotaryh];
		rotaryv = new Rotaryv[File.numRotaryv];
		timeh = new Timeh[File.numTimeh];
		timev = new Timev[File.numTimev];
		toggle = new Toggle[File.numToggle];
		xy = new Xy[File.numXy];*/
		
		
		
		System.out.println("init controls---------------");
		for(int t=0; t<File.numTabs(); t++) {
			System.out.println("Tab No: " + t);
			for(int c=0; c<File.controlType[t].length; c++) {
				// Add Batteryh
				if(File.controlType[t][c].equals("batteryh")) {
					batteryh.add( new Batteryh(File.controlName[t][c],
							File.controlType[t][c],
							File.controlX[t][c],
							File.controlY[t][c],
							File.controlW[t][c],
							File.controlH[t][c],
							File.controlColor[t][c]));
					System.out.println("batteryh " + c +" "+ File.controlW[t][c]);
				}
			}
		}
		
		/*if(File.numBatteryh > 0) {
			for(int j=0; j<File.numBatteryh; j++) {
				System.out.println("bh "+j);
				batteryh[j] = new Batteryh();
				
				batteryh[j].init(name, type, x, y, w, h, color);
			}
		} else {
			System.out.println("no bh");
		}*/
		
		
		
		
			/*if(File.numBatteryv > 0) {
				for(int j=0; j<File.numBatteryv; j++) {
					System.out.println("bv " + i);
					batteryv[j] = new Batteryv();
				}
			}
			if(File.numFaderh > 0) {
				for(int j=0; j<File.numFaderh; j++) {
					System.out.println("fh " + i);
					faderh[j] = new Faderh();
				}
			}
			if(File.numFaderv > 0) {
				for(int j=0; j<File.numFaderv; j++) {
					System.out.println("fv " + i);
					faderv[j] = new Faderv();
				}
			} else {
				System.out.println("no fv");
			}*/
		}
		


	/**
	 * 
	 */
	public void draw() {
		p.pushMatrix();
		
			// TODO Add int x, int y for position.
			//p.translate(x, y);
		
			// ground
			p.noStroke();
			p.fill(0);
			p.rect(0, 0, layoutWidth, layoutHeight);
			
			// tabs
			tab.draw();
		
			if(File.orientation().equals("vertical")) {
				p.translate(0, layoutHeight);
				p.rotate(-p.radians(90));
			}
			
			
			// Draw control.
			for(int j=0; j<File.controlType[tab.activeTab].length; j++) {
				// Batteryh
				if(File.controlType[tab.activeTab][j].equals("batteryh")) {
					//batteryh[tab.activeTab][j].display();
					for (int i=batteryh.size()-1; i>=0; i--) {
						//System.out.println("BATTERYYYYY "+i);
					    // An ArrayList doesn't know what it is storing so we have to cast the object coming out
					    Batteryh bh = (Batteryh) batteryh.get(i);
					    bh.display();
					}
					/*p.fill(File.controlColor[tab.activeTab][j]);
					p.ellipse(File.controlX[tab.activeTab][j],
							File.controlY[tab.activeTab][j],
							File.controlW[tab.activeTab][j],
							File.controlH[tab.activeTab][j]);*/
				}
				
			}
				
				
			/*	
				p.fill(wrongPowder.touchosc.File.controlColor[tab.activeTab][j]);
				p.rect(wrongPowder.touchosc.File.controlX[tab.activeTab][j],
					   wrongPowder.touchosc.File.controlY[tab.activeTab][j],
					   wrongPowder.touchosc.File.controlW[tab.activeTab][j],
					   wrongPowder.touchosc.File.controlH[tab.activeTab][j]);
			}
			*/
			
			
			//int counter = 0;
			/*p.noStroke();
			for(int i=0; i<numTabs(); i++) {
				for(int j=0; j<numBatteryh; j++) {
					
					// TODO Klassenbasierte version mit init methode im setup É
					
					// Led
					/*if(wrongPowder.touchosc.File.controlType[i][j].equals("led")) {
						//System.out.println("LED");
						p.fill(wrongPowder.touchosc.File.controlColor[i][j]);
						p.ellipse(wrongPowder.touchosc.File.controlX[i][j],
							      wrongPowder.touchosc.File.controlY[i][j],
							      wrongPowder.touchosc.File.controlW[i][j],
							      wrongPowder.touchosc.File.controlH[i][j]);
					}
					
					// Led
					if(wrongPowder.touchosc.File.controlType[i][j].equals("labelv")) {
						//System.out.println("LED");
						p.fill(wrongPowder.touchosc.File.controlColor[i][j]);
						p.ellipse(wrongPowder.touchosc.File.controlX[i][j],
							      wrongPowder.touchosc.File.controlY[i][j],
							      wrongPowder.touchosc.File.controlW[i][j],
							      wrongPowder.touchosc.File.controlH[i][j]);
					}
					
					//if(wrongPowder.touchosc.TouchOscFile.controlType[i][j].equals("led")) {
						//System.out.println("LED");
					p.fill(wrongPowder.touchosc.File.controlColor[i][j]);
					p.rect(wrongPowder.touchosc.File.controlX[i][j],
						   wrongPowder.touchosc.File.controlY[i][j],
						   wrongPowder.touchosc.File.controlW[i][j],
						   wrongPowder.touchosc.File.controlH[i][j]);
					//}*/
					
					/*batteryh[j].display();
					//counter++;
				}
			}*/
		
		p.popMatrix();
		
	}
	
	
	/**
	 * 
	 */
	public void mousePressed() {
		tab.mousePressed(p.mouseX, p.mouseY);
	}
	
	
	/**
	 * Read the file mode.
	 * 0 = iPhone/iPod-Touch
	 * 1 = iPad.
	 * Read orientation, set the size and store it to
	 * layoutWidth, layoutHeight variable.
	 */
	private void readMode() {
        System.out.println("Read Mode: mode = " + File.mode());
		
        switch(File.mode()) {
        case(0):
        	readOrientation("horizontal", 360, 480);
			readOrientation("vertical", 480, 360);
        	break;
        case(1):
        	readOrientation("horizontal", 768, 1024);
			readOrientation("vertical", 1024, 768);
        	break;
        }
	}

	/**
	 * Read the orientation
	 * @param ori The file mode. 0 or 1.
	 * @param width 
	 * @param height 
	 */
	private void readOrientation(String ori, int width, int height) {
		// If the ori == the file orientation, set variables.
		if(wrongPowder.touchosc.File.orientation().equals(ori)) {
			System.out.println("Read Orientation: orientation = " + ori +
			         "\n                  width = " + width +
			         "\n                  height = " + height);
			
			layoutWidth = width;
			layoutHeight = height;
			System.out.println("                  layoutWidth  = " + layoutWidth +
			                 "\n                  layoutHeight = " + layoutHeight);
		} else {
			System.out.println("Read Orientation: not correct orientation.");
		}
	}
	
	
	
	
	
	
	private class Control {//extends wrongPowder.gui.Button {
		
		public String controlName;
		public String controlType;
		public int controlColor;
		
		/*Control(String name, String type, int x, int y, int w, int h, int color) {
			this.name = name;
			this.type = type;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.color = color;
		}*/
		
		/*public void init(String name, String type, int x, int y, int w, int h, int color) {
			this.name = name;
			this.type = type;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.color = color;
		}*/
		
		/*public void display() {
			p.fill(color);
			p.rect(x, y, w, h);
		}*/
	}
	
	
	private class Batteryh extends Control {
		Batteryh(String name, String type, int x, int y, int w, int h, int color) {
			controlName = name;
			controlType = type;
			/*buttonX = x;
			buttonY = y;
			buttonWidth = w;
			buttonHeight = h;*/
			controlColor = color;
		}
		
		public void display() {
			p.fill(controlColor);
			//p.rect(buttonX, buttonY, buttonWidth, buttonHeight);
		}
	}
	
	
	private class Batteryv extends Control {
		Batteryv() {
			
		}
	}
	
	
	private class Faderh extends Control {
		Faderh() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Faderv extends Control {
		Faderv() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Labelh extends Control {
		Labelh() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Labelv extends Control {
		Labelv() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Led extends Control {
		Led() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Multifaderh extends Control {
		Multifaderh() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Multifaderv extends Control {
		Multifaderv() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Multitoggle extends Control {
		Multitoggle() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Push extends Control {
		Push() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Rotaryh extends Control {
		Rotaryh() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Rotaryv extends Control {
		Rotaryv() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Timeh extends Control {
		Timeh() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Timev extends Control {
		Timev() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Toggle extends Control {
		Toggle() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
	
	private class Xy extends Control {
		Xy() {//String name, String type, int x, int y, int w, int h, int color) {
			//super.init(name, type, x, y, w, h, color);
		}
	}
	
}