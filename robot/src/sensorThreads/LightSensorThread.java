package sensorThreads;

import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.LightDetectorAdaptor;
import robot.Robot;
import util.globalValues;

public class LightSensorThread implements Runnable {
	

	private AtomicInteger currentValue;
	
	private LightDetectorAdaptor detector;
	
	private Thread lightSensorThread;
	
	/**
	 * 
	 * @param values shared space in which this thread will be writing data 
	 * @param frequencyInMS time between sensor calls (in milliseconds)
	 */
	public LightSensorThread(Robot robot) {
		currentValue = new AtomicInteger(0);
		detector = new LightDetectorAdaptor(robot.getColorSensor());
		detector.setLow(0);
		detector.setHigh(1);
		detector.setReflected(true);
		lightSensorThread = new Thread(this);
	}
	
	@Override
	public void run() {
		while (true) {
			int value = (int) (detector.getLightValue() * globalValues.floatToInt);
			currentValue.set(value);
		}
		
	}
	
	public void interruptThread() {
		if (!lightSensorThread.isInterrupted()) {
			lightSensorThread.interrupt();
		}
	}
	
	public void startThread() {
		if (!lightSensorThread.isAlive() || lightSensorThread.isInterrupted()) {
			lightSensorThread.start();
		}
	}
	
	public float getLastLightValue() {
		float floatValue = ((float) currentValue.get()) / globalValues.floatToInt;
		return floatValue;
	}
	
	public LightDetectorAdaptor getDetector() {
		return detector;
	}

}
