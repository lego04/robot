package test;

import lejos.robotics.LightDetectorAdaptor;
import robot.LineFollower;
import robot.Robot;
import sensorThreads.LightSensorThread;
import util.EscapeThread;
import util.GlobalValues;

public class LineFollowerTest {
	
	public static void main(String args[]) throws InterruptedException {
		new EscapeThread().startThread();
		Robot robot = new Robot();
		LightSensorThread lst = new LightSensorThread(robot);
		lst.startThread();
		
		robot.getMovement().goForwardSpeed(GlobalValues.LINETRAVELSPEED * 10);
		new LineFollower(robot, lst).adjustLine();
		/*
		while (true) {
			if (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				System.out.println("High: " + lst.getLastLightValue());
				Thread.sleep(1000);				
			}
			else if (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
				System.out.println("Low: " + lst.getLastLightValue());
				Thread.sleep(1000);
			}
			else {
				System.out.println("Right: " + lst.getLastLightValue());
				Thread.sleep(1000);
				
			}
		}
		*/
		
		
	}
}
