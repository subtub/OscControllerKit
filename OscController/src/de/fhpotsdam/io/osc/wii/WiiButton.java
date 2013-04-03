//
// WiiButton.java
// OscControllerKit (v.##library.prettyVersion##) is released under the MIT License.
//
// Copyright (c) 2012, Tim Pulver & Paul Vollmer http://www.fh-potsdam.de
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package de.fhpotsdam.io.osc.wii;

import java.lang.Enum;

public enum WiiButton {
	UP{
		public String toString(){
			return "Up";
		}
	},
	RIGHT{
		public String toString(){
			return "Right";
		}
	},
	DOWN{
		public String toString(){
			return "Down";
		}
	},
	LEFT{
		public String toString(){
			return "Left";
		}
	},
	A{
		public String toString(){
			return "A";
		}
	},
	MINUS{
		public String toString(){
			return "Minus";
		}
	},
	HOME{
		public String toString(){
			return "Home";
		}
	},
	PLUS{
		public String toString(){
			return "Plus";
		}
	},
	ONE{
		public String toString(){
			return "1";
		}
	},
	TWO{
		public String toString(){
			return "2";
		}
	},
	B{
		public String toString(){
			return "B";
		}
	}
}