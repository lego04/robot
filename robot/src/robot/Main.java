package robot;

import lejos.hardware.Button;
import lejos.hardware.Key;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		robot.start();
		//System.out.println("Hello World");
		Button.waitForAnyPress();
		stop();
	}
	
	private static void stop() {
		Button.ENTER.simulateEvent(Key.KEY_PRESSED_AND_RELEASED);
	}

}
