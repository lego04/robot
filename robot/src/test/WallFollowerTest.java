package test;

import util.globalValues;
import listeners.TouchSensorListener;
import robot.Robot;
import robot.UltrasonicSensor;
import robot.WallFollower;
import robot.UltrasonicSensor.Modes;
import util.EscapeThread;

public class WallFollowerTest {
	
	public static void main(String[] args) {
		System.out.println("Testing WallFollower:");
		new EscapeThread().startThread();
		Robot robot = new Robot();
		UltrasonicSensor us = new UltrasonicSensor(robot);
		us.start(Modes.Left, 90);
		WallFollower wf = new WallFollower(robot, us, globalValues.RIGHT);
		new TouchSensorListener(wf);
		wf.followTheWall();
	}
}
