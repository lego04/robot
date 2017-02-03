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
		
	}
	
}
