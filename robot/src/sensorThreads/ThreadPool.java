package sensorThreads;

import robot.Robot;

public class ThreadPool {
	
	private UltrasonicSensorThread ultraSonic;
	private LightSensorThread lightSensor;
	private GyroSensorThread gyroThread;
	
	private Robot robot;
	
	public ThreadPool(Robot robot) {
		ultraSonic = new UltrasonicSensorThread(robot);
		lightSensor = new LightSensorThread(robot);
		lightSensor.startThread(); //light sensor is active the whole time
		gyroThread = new GyroSensorThread(robot);
	}
	
	public UltrasonicSensorThread getUltraSonicSensorThread() {
		ultraSonic.stop();
		ultraSonic = new UltrasonicSensorThread(robot);
		return ultraSonic;
	}
	
	public LightSensorThread getLightSensorThread() {
		return lightSensor;
	}
	
	public GyroSensorThread getGyroSensorThread() {
		gyroThread.start();
		gyroThread = new GyroSensorThread(robot);
		return gyroThread;
	}
	
	public void stopUltraSonic() {
		ultraSonic.stop();
	}
	
	public void stopLightSensor() {
		lightSensor.interruptThread();
	}
	
	public void stopGyro() {
		gyroThread.stop();
	}

}
