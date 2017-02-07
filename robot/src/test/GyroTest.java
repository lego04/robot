package test;

import robot.Robot;
import sensorThreads.GyroSensorThread;
import util.EscapeThread;

public class GyroTest {
	
	public static void main(String[] args) {
		
		new EscapeThread().startThread();
		Robot robot = new Robot();
		
		GyroSensorThread gst = new GyroSensorThread(robot);
		gst.start();
		
		int i = 0;
		
		while (true) {
			System.out.println("Gyro: " + gst.getAngle());
			try {
				Thread.sleep(500);
			}
			catch (Exception e) { }
			i++;
			if (i > 20) {
				gst.reset();
				System.out.println("Resetting");
				i = 0;
			}
		}
		
	}
	
}
