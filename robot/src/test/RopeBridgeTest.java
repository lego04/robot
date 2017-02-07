package test;

import robot.BridgeFollower;
import robot.Robot;
import robot.RopeBridgeWallFollower;
import util.EscapeThread;

public class RopeBridgeTest {
	
	public static void main(String[] args) {
		
		new EscapeThread().startThread();
		Robot robot = new Robot();
		
		RopeBridgeWallFollower rbwf = new RopeBridgeWallFollower(robot);
		rbwf.startFollowing();
		
		//BridgeFollower bf = new BridgeFollower(robot);
		//bf.start();
		//
	}
	
}
