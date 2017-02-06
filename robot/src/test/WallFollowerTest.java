package test;

import listeners.TouchSensorListener;
import robot.Robot;
import robot.WallFollower;
import robot.WallFollower2;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.EscapeThread;

public class WallFollowerTest {
	
	public static void main(String[] args) {
		System.out.println("WallFollowerTest:");
		new EscapeThread().startThread();
		Robot robot = new Robot();
		WallFollower2 wf = new WallFollower2(robot);
		//new TouchSensorListener(wf);			
		wf.startFollowing();
	}
}
