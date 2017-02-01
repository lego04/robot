package test;

import lejos.hardware.Button;
import lejos.hardware.Key;
import robot.Robot;
import robot.UltrasonicSensor;

public class USSensorTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		robot.start();
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
