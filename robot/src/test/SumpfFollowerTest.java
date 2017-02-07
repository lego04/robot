package test;

import robot.Robot;
import robot.SumpfFollower;
import util.EscapeThread;

public class SumpfFollowerTest {
	
	public static void main(String[] args) {
		System.out.println("WallFollowerTest:");
		new EscapeThread().startThread();
		Robot robot = new Robot();
		SumpfFollower sf = new SumpfFollower(robot);
		sf.stayOnWall();
	}
}
