package test;

import robot.BridgeFollower;
import robot.Robot;
import util.EscapeThread;

public class BridgeTest {
	
	public static void main(String[] args) {
		
		new EscapeThread().startThread();
		Robot robot = new Robot();
		
		BridgeFollower bf = new BridgeFollower(robot);
		bf.start();
		
	}
	
	
	
}
