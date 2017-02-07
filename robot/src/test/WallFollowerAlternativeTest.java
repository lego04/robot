package test;

import robot.Robot;
import robot.WallFollower;
import util.EscapeThread;

public class WallFollowerAlternativeTest {
	public static void main(String[] args) {
		System.out.println("WallFollowerTest:");
		new EscapeThread().startThread();
		Robot robot = new Robot();
		WallFollower wf = new WallFollower(robot);
		wf.followTheWall();
	}
}
