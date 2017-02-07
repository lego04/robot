package test;

import robot.LineFollower;
import robot.Robot;
import sensorThreads.LightSensorThread;
import util.EscapeThread;
import util.GlobalValues;

public class SeesawTest {
	public static void main (String args[]) {
		new EscapeThread().startThread();
		Robot robot = new Robot();
		LightSensorThread lst = new LightSensorThread(robot);
		lst.startThread();
		
		new LineFollower(robot).adjustLine(true);
		robot.getMovement().setSpeed(GlobalValues.LINETRAVELSPEED / 2);
		robot.getMovement().goForwardDist(10);
		//robot.getMovement().goBackwardDist(20);
		robot.getMovement().setSpeed(400);
		robot.getMovement().goForwardDist(100);
	}
}
