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
 
 /*test 123333*/

package wrongPowder.touchosc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import processing.core.XML;



public class File {
	
	public static XML xml;
	
	public static String[]   tabName;
	public static String[][] controlName;
	public static String[][] controlType;
	public static int[][]    controlX;
	public static int[][]    controlY;
	public static int[][]    controlW;
	public static int[][]    controlH;
	public static int[][]    controlColor;

	public static boolean[][] controlLocaloff;
	public static float[][]   controlScalef;
	public static float[][]   controlScalet;
	public static boolean[][] controlBackground;
	public static boolean[][] controlOutline;
	public static int[][]     controlSize;
	public static String[][]  controlText;
	
	/*public static int numBatteryh    = 0;
	public static int numBatteryv    = 0;
	public static int numFaderh      = 0;
	public static int numFaderv      = 0;
	public static int numLabelh      = 0;
	public static int numLabelv      = 0;
	public static int numLed         = 0;
	public static int numMultifaderh = 0;
	public static int numMultifaderv = 0;
	public static int numMultitoggle = 0;
	public static int numPush        = 0;
	public static int numRotaryh     = 0;
	public static int numRotaryv     = 0;
	public static int numTimeh       = 0;
	public static int numTimev       = 0;
	public static int numToggle      = 0;
	public static int numXy          = 0;*/
	

	// Create Base64 object to decode TouchOsc name of control tag.
	//private Base64 base64 = new Base64();
	
	/**
	 * A Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 *
	 * @param fileName
	 */
	public File(String fileName) {
		System.out.println("[File] constructor initialized");
		load(fileName);
	}
	
	public File() {
		System.out.println("[File] constructor initialized");
	}
	
	
	/**
	 * Load a TouchOSC file created with TouchOSC Editor.
	 *
	 * @return String FileName
	 */
	public void load(String fileName) {
		System.out.println("[File] load start");
		
		// Read the TouchOsc file, unzip it and save to xml variable. 
		unzip(fileName);
		
	    // Print out the xml file.
	    System.out.println("[File] XML:\n" + xml);
	    
	    // List all children of xml.
	    // Use for debugging...
	    System.out.println("\nxml-file listChildren:");
	    String[] list = xml.listChildren();
	    for(int i=0; i<list.length; i++) {
	    	System.out.println("["+i+"] " + list[i]);
	    }
	    
	    
	    // Tabs
	    
	    // Split the xml datatype. The xmlChildren Array
	    // contains the <tabpage> tag.
	    XML[] xmlChildren = xml.getChildren();
	    System.out.println("\nxmlChildren.length: "+xmlChildren.length);

	    // Calculate the number of <tabpage> tags.
	    int tabLength = (xmlChildren.length-1)/2;
	    // Set the size of the tabs String Array.
	    tabName = new String[tabLength];
	    
	    controlName  = new String[tabLength][];
	    controlType  = new String[tabLength][];
	    controlX     = new int[tabLength][];
	    controlY     = new int[tabLength][];
	    controlW     = new int[tabLength][];
	    controlH     = new int[tabLength][];
	    controlColor = new int[tabLength][];
	    
	    controlLocaloff   = new boolean[tabLength][];
		controlScalef     = new float[tabLength][];
		controlScalet     = new float[tabLength][];
		controlBackground = new boolean[tabLength][];
		controlOutline    = new boolean[tabLength][];
		controlSize       = new int[tabLength][];
		controlText       = new String[tabLength][];
		
	    // Create a tabCounter for the tabs String Array.
	    int tabCounter = 0;
	    
	    // Check how many <tabpage> tags exist.
	    for(int i=1; i<xmlChildren.length; i+=2) {
	    	
	    	// If a <tabpage> tag exist...
	    	if(xmlChildren[i].getName() == "tabpage") {
	    		System.out.println("AttributeCount: "+xmlChildren[i].getAttributeCount());
	    		System.out.println("Attribute: "+xmlChildren[i].getString("name"));
	    		
	    		// get the name of the tabpage and decode it with Base64.
	    		tabName[tabCounter] = wrongPowder.util.codec.Base64.decode( xmlChildren[i].getString("name"), "UTF-8");
	    		
	    		
	    		// Controls
	    		
	    		// Get the content of <tabpage> and store it to controls array.
	    		XML[] xmlControls = xmlChildren[i].getChildren();
	    		//System.out.println("controls.length: "+controls.length);
	    		
	    		// Calculate the number of <control> tags.
	    		int tempNumControl = (xmlControls.length-1)/2;
	    		// Set the size of the controls String Array.
	    		controlName[tabCounter]  = new String[tempNumControl];
	    	    controlType[tabCounter]  = new String[tempNumControl];
	    	    controlX[tabCounter]     = new int[tempNumControl];
	    	    controlY[tabCounter]     = new int[tempNumControl];
	    	    controlW[tabCounter]     = new int[tempNumControl];
	    	    controlH[tabCounter]     = new int[tempNumControl];
	    	    controlColor[tabCounter] = new int[tempNumControl];
	    	    
	    	    controlLocaloff[tabCounter]   = new boolean[tempNumControl];
	    		controlScalef[tabCounter]     = new float[tempNumControl];
	    		controlScalet[tabCounter]     = new float[tempNumControl];
	    		controlBackground[tabCounter] = new boolean[tempNumControl];
	    		controlOutline[tabCounter]    = new boolean[tempNumControl];
	    		controlSize[tabCounter]       = new int[tempNumControl];
	    		controlText[tabCounter]       = new String[tempNumControl];
	    	    // Create a controlCounter for the controls String Array.
	    	    int controlCounter = 0;
	    	    
	    		// Check how many <control> tags exist in <tabpage>.
   				for(int j=0; j<xmlControls.length; j++) {
   					
   					// If a <control> tag with attribute "type" exist...
   					if(xmlControls[j].getName() == "control" && xmlControls[j].hasAttribute("type")) {
   						
   						// Get the name.
	    				controlName[tabCounter][controlCounter] = wrongPowder.util.codec.Base64.decode( xmlControls[j].getString("name"), "UTF-8");
	  					//System.out.println("Name: " + controlName[tabCounter][controlCounter]);
	  					
   						// Get the type.
   						controlType[tabCounter][controlCounter] = xmlControls[j].getString("type");
   						//System.out.println("Type: " + controlType[tabCounter][controlCounter]);
   						// Count the different types.
   						
   						// TODO delete
   						// Use the controlType variable to check if it is a batteryh etc.
   						/*if(controlType[tabCounter][controlCounter].equals("batteryh"))    numBatteryh++;
   						if(controlType[tabCounter][controlCounter].equals("batteryv"))    numBatteryv++;
   						if(controlType[tabCounter][controlCounter].equals("faderh"))      numFaderh++;
   						if(controlType[tabCounter][controlCounter].equals("faderv"))      numFaderv++;
   						if(controlType[tabCounter][controlCounter].equals("labelh"))      numLabelh++;
   						if(controlType[tabCounter][controlCounter].equals("labelv"))      numLabelv++;
   						if(controlType[tabCounter][controlCounter].equals("led"))         numLed++;
   						if(controlType[tabCounter][controlCounter].equals("multifaderh")) numMultifaderh++;
   						if(controlType[tabCounter][controlCounter].equals("multifaderv")) numMultifaderv++;
   						if(controlType[tabCounter][controlCounter].equals("multitoggle")) numMultitoggle++;
   						if(controlType[tabCounter][controlCounter].equals("push"))        numPush++;
   						if(controlType[tabCounter][controlCounter].equals("rotaryh"))     numRotaryh++;
   						if(controlType[tabCounter][controlCounter].equals("rotaryv"))     numRotaryv++;
   						if(controlType[tabCounter][controlCounter].equals("timeh"))       numTimeh++;
   						if(controlType[tabCounter][controlCounter].equals("timev"))       numTimev++;
   						if(controlType[tabCounter][controlCounter].equals("toggle"))      numToggle++;
   						if(controlType[tabCounter][controlCounter].equals("xy"))          numXy++;*/
   						
   						
	    				// Get the position and size.
	   					controlX[tabCounter][controlCounter] = xmlControls[j].getInt("x");
	   					controlY[tabCounter][controlCounter] = xmlControls[j].getInt("y");
	   					controlW[tabCounter][controlCounter] = xmlControls[j].getInt("w");
	   					controlH[tabCounter][controlCounter] = xmlControls[j].getInt("h");
	    				System.out.println("Pos/Size: x " + controlX[tabCounter][controlCounter] +
	   					                            " y " + controlY[tabCounter][controlCounter] +
	   					                            " w " + controlW[tabCounter][controlCounter] +
	   					                            " h " + controlH[tabCounter][controlCounter]);
	    				
	    				// Get color.
	    				String tempColor = xmlControls[j].getString("color");
	    				if(tempColor.compareTo("red") == 0)         controlColor[tabCounter][controlCounter] = Layout.RED_ON;
	    				else if(tempColor.compareTo("green") == 0)  controlColor[tabCounter][controlCounter] = Layout.GREEN_ON;
	    				else if(tempColor.compareTo("blue") == 0)   controlColor[tabCounter][controlCounter] = Layout.BLUE_ON;
	    				else if(tempColor.compareTo("yellow") == 0) controlColor[tabCounter][controlCounter] = Layout.YELLOW_ON;
	    				else if(tempColor.compareTo("purple") == 0) controlColor[tabCounter][controlCounter] = Layout.PURPLE_ON;
	    				else if(tempColor.compareTo("gray") == 0)   controlColor[tabCounter][controlCounter] = Layout.GRAY_ON;
	    				else if(tempColor.compareTo("orange") == 0) controlColor[tabCounter][controlCounter] = Layout.ORANGE_ON;
	    				
	    				// Get localoff
	    				if(xmlControls[j].hasAttribute("local_off")) controlLocaloff[tabCounter][controlCounter] = Boolean.parseBoolean(xmlControls[j].getString("local_off"));
	    				else controlLocaloff[tabCounter][controlCounter] = false;
	    				
	    				// Get scalef
	    				if(xmlControls[j].hasAttribute("scalef")) controlScalef[tabCounter][controlCounter] = xmlControls[j].getFloat("scalef");
	    				else controlScalef[tabCounter][controlCounter] = 0.0f;
	    				
	    				// Get scalet
	    				if(xmlControls[j].hasAttribute("scalet")) controlScalet[tabCounter][controlCounter] = xmlControls[j].getFloat("scalet");
	    				else controlScalet[tabCounter][controlCounter] = 0.0f;
	    				
	    				// Get background
	    				if(xmlControls[j].hasAttribute("local_off")) controlBackground[tabCounter][controlCounter] = Boolean.parseBoolean(xmlControls[j].getString("background"));
	    				else controlBackground[tabCounter][controlCounter] = false;
	    				
	    				// Get outline
	    				if(xmlControls[j].hasAttribute("outline")) controlOutline[tabCounter][controlCounter] = Boolean.parseBoolean(xmlControls[j].getString("outline"));
	    				else controlOutline[tabCounter][controlCounter] = false;
	    				
	    				// Get size
	    				if(xmlControls[j].hasAttribute("size")) controlSize[tabCounter][controlCounter] = xmlControls[j].getInt("size");
	    				else controlSize[tabCounter][controlCounter] = 0;
	    				
	    				// Get text
	    				if(xmlControls[j].hasAttribute("text")) controlText[tabCounter][controlCounter] = wrongPowder.util.codec.Base64.decode( xmlControls[j].getString("text"), "UTF-8");
	    				else controlText[tabCounter][controlCounter] = "not available";
	    				
	    				            
   						controlCounter++;
   						
   					}
   				}
   				
   				
   				tabCounter++;
   				
	    	}
	    	else {
				System.out.println("NO Control tag");
			}
	    	
	    }
	    
	}
	
	
	// TODO Create zip/ unzip class.
	
	/**
	 * Unzip to get the XML file, parse and save it to variable.
	 * 
	 * @param fileName The TouchOsc file.
	 */
	private void unzip(String fileName) {
		try {
	    	ZipFile zf = new ZipFile(fileName);
	    	Enumeration entries = zf.entries();

	    	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	    	while (entries.hasMoreElements ()) {
	    		ZipEntry ze = (ZipEntry) entries.nextElement();
	    		//System.out.println("ZE: "+ze);
	            
	    		// create xml
	    		xml = xml.parse(inputStreamToString(zf.getInputStream(ze)));
	    		
	    	}
	    }
	    catch (IOException e) {
	    		e.printStackTrace();
	    }

	}
	
	
	/**
	 * Get an inputStream and return a String.
	 * 
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	private String inputStreamToString(InputStream in) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		while ( (line = bufferedReader.readLine ()) != null) {
			stringBuilder.append(line + "\n");
		}
		
		bufferedReader.close();
		return stringBuilder.toString();
	}
	
	
	
	
	/**
	 * Get the Version of TouchOSC Editor.
	 * 
	 * @return version
	 */
	public static int version() {
		return xml.getInt("version");
	}
	
	/**
	 * Get the Layout Size of the TouchOSC file.<br>
	 * 0 = iPhone/iPod Touch<br>
	 * 1 = iPad
	 * 
	 * @return mode
	 */
	public static int mode() {
		return xml.getInt("mode");
	}
	
	/**
	 * Get the Layout Orientation of the TouchOSC file.<br>
	 * vertical or horizontal
	 * 
	 * @return orientation
	 */
	public static String orientation() {
		return xml.getString("orientation");
	}
	
	
	/**
	 * Number of Tab Pages in the file.
	 * 
	 * @return Integer
	 */
	public static int numTabs() {
		return tabName.length;
	}
	
	
	public static int numControls() {
		int value = 0;
		for(int i=0; i<tabName.length; i++) {
			value = value+controlType[i].length;
		}
		return value;
	}
	
}