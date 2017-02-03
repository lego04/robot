package sensorThreads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.SampleProvider;
import robot.Robot;

public class GyroSensorThread implements Runnable {
	
	private Thread thread;
	private GyroscopeAdapter gyro;
	
	AtomicBoolean active;
	AtomicInteger angle;
	
	public GyroSensorThread(Robot robot) {
		
		//thread = new Thread(this);
		
		SampleProvider sp = robot.getGyroSensor().getAngleMode();
		gyro = new GyroscopeAdapter(sp, 1);
		
		active = new AtomicBoolean(true);
		angle = new AtomicInteger(0);
		
	}
	
	public int getAngle() {
		
		return angle.get();
		
	}
	
	public void start() {
		
		gyro.reset();
		thread = new Thread(this);
		thread.start();
		
	}
	
	@Override
	public void run() {
		
		angle.set(0);
		
		while (active.get()) {
			
			int intAngle = (int) gyro.getAngle();
			angle.set(intAngle);
			
			System.out.println(intAngle);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	

}
