package test;

import lejos.robotics.LightDetectorAdaptor;
import robot.Robot;
import sensorThreads.LightSensorThread;
import util.EscapeThread;
import util.GlobalValues;

public class LineFollowerTest {
	
	public static void main(String args[]) throws InterruptedException {
		new EscapeThread().startThread();
		Robot robot = new Robot();
		LightSensorThread lst = new LightSensorThread(robot);
		
		while (true) {
			if (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				System.out.println("High");
				Thread.sleep(1000);				
			}
			else if (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
				System.out.println("Low");
				Thread.sleep(1000);
			}
			else {
				System.out.println("Right");
				Thread.sleep(1000);
				
			}
		}
		
		
	}
}
