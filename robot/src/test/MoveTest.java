package test;

import robot.Robot;
import util.EscapeThread;

public class MoveTest {
	
	public static void main(String[] args) {
		
		new EscapeThread().startThread();
		Robot robot = new Robot();
		
		robot.getPilot().setTravelSpeed(50);
		robot.getPilot().backward();
		
	}
	
}
