package robot;

import lejos.hardware.Button;
import lejos.hardware.Key;
import util.EscapeThread;

public class Main {

	public static void main(String[] args) {
		startEscapeThread();
		Robot robot = new Robot();
		robot.start();
		//System.out.println("Hello World");
		//Button.ESCAPE.waitForPress();
		//stop();
	}
	
	private static void stop() {
		Button.ESCAPE.simulateEvent(Key.KEY_PRESSED_AND_RELEASED);
	}
	
	private static void startEscapeThread() {
		new EscapeThread().startThread();
	}

}
