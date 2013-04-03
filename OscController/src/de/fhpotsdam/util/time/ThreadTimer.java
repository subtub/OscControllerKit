//
// ThreadTimer.java
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

package de.fhpotsdam.util.time;

/**
 * This thread runs for a certain period of time, calls timeOver(), and terminates,
 * when the time is over. 
 * After creating a ThreadTimer object, make sure to call start()! 
 * You have to implement timeOver() in your class. Do NOT overwrite the run  
 * method in your implementation.
 * @author Tim Pulver
 */
public abstract class ThreadTimer extends Thread{
	// The time the thread will fall asleep to check again if finished
	private static int sleepMillis = 30;
	
	int millis;
	long startMillis;
	long stopMillis;
	
	
	public ThreadTimer(int millis){
		this.millis = millis;
		init();
	}
	
	public void init(){
		this.startMillis = System.currentTimeMillis();
		this.stopMillis = startMillis + millis;
	}
	
	public void reset(){
		init();
		run();
	}
	
	
	public void run(){
		while(System.currentTimeMillis() < stopMillis){
			try {
				sleep(sleepMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.err.println("Coud not go to sleep.. too much Club Mate..");				
			}
		}
		timeOver();
		return;
	}
	
	abstract protected void timeOver();
}
