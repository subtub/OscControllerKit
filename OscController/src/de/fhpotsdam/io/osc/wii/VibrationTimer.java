package de.fhpotsdam.io.osc.wii;

import de.fhpotsdam.util.time.ThreadTimer;

public class VibrationTimer extends ThreadTimer {
	public VibrationTimer(WiiOsc wiiOsc, int millis, int wiiNumber) {
		super(millis);
		this.wiiOsc = wiiOsc;
		this.wiiNumberToCall = wiiNumber;
	}

	WiiOsc wiiOsc;
	int wiiNumberToCall;

	protected void timeOver() {
		System.out.println("Time over");
		wiiOsc.vibrate(wiiNumberToCall, false);
	}
}
