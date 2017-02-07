package robot;

import lejos.hardware.Button;
import lejos.hardware.Key;
import util.EscapeThread;

public class Main {

	public static void main(String[] args) {
		EscapeThread escape = new EscapeThread();
		escape.startThread();
		Robot robot = new Robot();
		escape.setRobot(robot);
		robot.start(true);
		//System.out.println("Hello World");
		//Button.ESCAPE.waitForPress();
		//stop();
	}
	
	private static void stop() {
		Button.ESCAPE.simulateEvent(Key.KEY_PRESSED_AND_RELEASED);
	}
	

}
