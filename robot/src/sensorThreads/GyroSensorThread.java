package sensorThreads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.OffsetCorrectionFilter;
import robot.Robot;

public class GyroSensorThread implements Runnable {
	
	private Thread thread;
	private GyroscopeAdapter gyro;
	private OffsetCorrectionFilter filter;
	
	private AtomicBoolean active;
	private AtomicInteger angle;
	
	public GyroSensorThread(Robot robot) {
		
		SampleProvider sp = robot.getGyroSensor().getAngleMode();
		gyro = new GyroscopeAdapter(sp, 1000);
		
		filter = new OffsetCorrectionFilter(sp);
		
		active = new AtomicBoolean(true);
		angle = new AtomicInteger(0);
		
	}
	
	public int getAngle() {
		
		return angle.get();
		
	}
	
	public void start() {
		
		stop();
		gyro.reset();
		thread = new Thread(this);
		active.set(true);
		thread.start();
		
	}
	
	public void stop() {
		active.set(false);
	}
	
	public void reset() {
		gyro.reset();
		filter.reset();
		angle.set(0);
	}
	
	@Override
	public void run() {
		
		angle.set(0);
		
		float[] samples = new float[10];
		
		while (active.get()) {
			
			filter.reset();
			filter.fetchSample(samples, 0);			
			
			int intAngle = (int) filter.getMean()[0];
			angle.set(intAngle);
			
			// System.out.println(intAngle);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	

}
