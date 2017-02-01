package test;

import lejos.hardware.Button;
import lejos.hardware.Key;
import robot.Robot;
import robot.UltrasonicSensor;
import robot.UltrasonicSensor.Modes;
import util.EscapeThread;

public class USSensorTest {
	
	public static void main(String[] args) {
		new EscapeThread().startThread();
		Robot robot = new Robot();
//		robot.start();
		
		UltrasonicSensor us = new UltrasonicSensor(robot);
		us.start(Modes.Right, 90);
		
		Button.ESCAPE.waitForPress();
		stop();
	}
	
	private static void stop() {
		Button.ESCAPE.simulateEvent(Key.KEY_PRESSED_AND_RELEASED);
	}

}
