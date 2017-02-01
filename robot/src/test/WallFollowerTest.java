package test;

import lejos.hardware.Button;
import lejos.hardware.Key;
import listeners.TouchSensorListener;
import robot.Robot;
import robot.UltrasonicSensor;
import robot.WallFollower;

public class WallFollowerTest {
	
	public static void main(String[] args) {
		Robot robot = new Robot();
		UltrasonicSensor us = new UltrasonicSensor(robot);
		us.start();
		WallFollower wf = new WallFollower(robot, us);
		new TouchSensorListener(wf);
		wf.followTheWall();
		
		Button.ESCAPE.waitForPress();
		stop();
	}
	
	private static void stop() {
		Button.ESCAPE.simulateEvent(Key.KEY_PRESSED_AND_RELEASED);
	}

}
