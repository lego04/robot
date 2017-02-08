package test;

import robot.BridgeFollower;
import robot.Robot;
import robot.RopeBridgeWallFollower;
import util.EscapeThread;
import util.Station;

public class RopeBridgeTest {
	
	public static void main(String[] args) {
		
		new EscapeThread().startThread();
		Robot robot = new Robot();
		robot.getStates().startFromState(Station.ROPE_BRIDGE);
		/*
		RopeBridgeWallFollower rbwf = new RopeBridgeWallFollower(robot);
		BridgeFollower bf = new BridgeFollower(robot);
		
		System.out.println("WALL");
		rbwf.startFollowing();
		System.out.println("BRIDGE");
		bf.start();
		*/
		
	}
	
}
