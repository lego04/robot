package test;

import lejos.hardware.Button;
import lejos.hardware.Key;
import robot.Robot;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.EscapeThread;

public class USSensorTest {
	
	public static void main(String[] args) {
		new EscapeThread().startThread();
		Robot robot = new Robot();
//		robot.start();
		
		UltrasonicSensorThread us = new UltrasonicSensorThread(robot);
		us.start(Modes.BothSides, 45);
		
		Button.ESCAPE.waitForPress();
		stop();
	}
	
	private static void stop() {
		Button.ESCAPE.simulateEvent(Key.KEY_PRESSED_AND_RELEASED);
	}

}
