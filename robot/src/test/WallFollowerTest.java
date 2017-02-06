package test;

import listeners.TouchSensorListener;
import robot.Robot;
import robot.WallFollower;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.EscapeThread;

public class WallFollowerTest {
	
	public static void main(String[] args) {
		System.out.println("WallFollowerTest:");
		new EscapeThread().startThread();
		Robot robot = new Robot();
		WallFollower wf = new WallFollower(robot);
		//new TouchSensorListener(wf);
		wf.followTheWall();
	}
}
