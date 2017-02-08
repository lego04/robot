package sensorThreads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.LightDetectorAdaptor;
import lejos.utility.Delay;
import robot.Robot;
import util.GlobalValues;

public class LightSensorThread implements Runnable {
	

	private AtomicInteger currentValue;
	
	private LightDetectorAdaptor detector;
	
	private Thread lightSensorThread;
	
	private AtomicBoolean active;
	
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
		active = new AtomicBoolean(true);
	}
	
	@Override
	public void run() {
		while (active.get()) {
			int value = (int) (detector.getLightValue() * GlobalValues.floatToInt);
			currentValue.set(value);
		}
		
	}
	
	public void interruptThread() {
		/*
		 * set active to false, so while loop in run method will exit and thread is finished
		 */
		active.set(false);
	}
	
	public void startThread() {
		//instanciate new Thread and start it
		interruptThread(); //kill thread if its still alive
		//start new one
		lightSensorThread = new Thread(this);
		active.set(true);
		lightSensorThread.start();
	}
	
	public float getLastLightValue() {
		float floatValue = ((float) currentValue.get()) / GlobalValues.floatToInt;
		return floatValue;
	}
	
	public LightDetectorAdaptor getDetector() {
		return detector;
	}

// TODO: implement !!!
	public boolean nextStateReady() {
		if (getLastLightValue() > GlobalValues.MAXLIGHT) {
			Delay.msDelay(1000);
			return true;
		}
		else {
			return false;
		}
	}

}
