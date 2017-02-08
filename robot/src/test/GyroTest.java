package test;

import lejos.utility.Delay;
import robot.Robot;
import sensorThreads.GyroSensorThread;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.EscapeThread;
import util.GlobalValues;

public class GyroTest {
	
	public static void main(String[] args) {
		
		new EscapeThread().startThread();
		Robot robot = new Robot();
		
		GyroSensorThread gst = new GyroSensorThread(robot);
		UltrasonicSensorThread ust = new UltrasonicSensorThread(robot);
		ust.start(Modes.Left);
		gst.start();
		
		int i = 0;
		
		while (true) {
			System.out.println("Dist1: " + ust.getDistance());
			Delay.msDelay(1000);
			//System.out.println("Gyro: " + gst.getAngle());
			System.out.println("Dist2: " + ust.getDistance());
			robot.getMovement().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			robot.getMovement().backwardDirection();
			robot.getMovement().goForwardDist(25);
			System.out.println("Dist3: " + ust.getDistance());
			while (true) {
				System.out.println("Angle: " + gst.getAngle());
				System.out.println("Dist: " + ust.getDistance());
				Delay.msDelay(2000);
			}
//			try {
//				Thread.sleep(500);
//			}
//			catch (Exception e) { }
//			i++;
//			if (i > 20) {
//				gst.reset();
//				System.out.println("Resetting");
//				i = 0;
//			}
		}
		
	}
	
}
