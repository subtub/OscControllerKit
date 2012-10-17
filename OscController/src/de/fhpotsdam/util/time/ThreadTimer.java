package de.fhpotsdam.util.time;

/**
 * This thread runs for a certain period of time, calls timeOver(), and terminates,
 * when the time is over. 
 * After creating a ThreadTimer object, make sure to call start()! 
 * You have to implement timeOver() in your class. Do NOT overwrite the run  
 * method in your implementation.
 * @author Tim Pulver
 *
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
