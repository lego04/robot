package test;

import lejos.hardware.Button;
import lejos.hardware.Key;
import robot.Robot;
import robot.UltrasonicSensor;
import util.EscapeThread;

public class USSensorTest {
	
	public static void main(String[] args) {
		new EscapeThread().startThread();
		// TODO Auto-generated method stub
		Robot robot = new Robot();
//		robot.start();
		//System.out.println("Hello World");
		
		UltrasonicSensor us = new UltrasonicSensor(robot);
		us.start();
		
		Button.ESCAPE.waitForPress();
		stop();
	}
	
	private static void stop() {
		Button.ESCAPE.simulateEvent(Key.KEY_PRESSED_AND_RELEASED);
	}

}
