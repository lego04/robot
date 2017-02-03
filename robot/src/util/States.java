package util;

import robot.FindLineFirst;
import robot.Robot;

public class States {
	
	private Robot robot;
	private int state = 0;
	
	public States (Robot robot) {
		this.robot = robot;
		
	}
	
	public void startLineFindAndFollow() {
		new FindLineFirst(robot).findLineFirst();
	}
}
