package test;

import robot.Robot;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.EscapeThread;

public class USSensorTest {
	
	public static void main(String[] args) {
		
		new EscapeThread().startThread();
		Robot robot = new Robot();
		
		UltrasonicSensorThread us = new UltrasonicSensorThread(robot);
		us.start(Modes.Down);
		
	}

}
