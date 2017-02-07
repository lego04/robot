package sensorThreads;

import robot.Robot;

public class ThreadPool {
	
	private UltrasonicSensorThread ultraSonic;
	private LightSensorThread lightSensor;
	private GyroSensorThread gyroThread;
	
	public ThreadPool(Robot robot) {
		ultraSonic = new UltrasonicSensorThread(robot);
		lightSensor = new LightSensorThread(robot);
		lightSensor.startThread(); //light sensor is active the whole time
		gyroThread = new GyroSensorThread(robot);
	}
	
	public UltrasonicSensorThread getUltraSonicSensorThread() {
		return ultraSonic;
	}
	
	public LightSensorThread getLightSensorThread() {
		return lightSensor;
	}
	
	public GyroSensorThread getGyroSensorThread() {
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
