package sensorThreads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.SampleProvider;
import robot.Robot;

public class GyroSensorThread implements Runnable {
	
	private Thread thread;
	private GyroscopeAdapter gyro;
	
	private AtomicBoolean active;
	private AtomicInteger angle;
	
	public GyroSensorThread(Robot robot) {
		
		//thread = new Thread(this);
		
		SampleProvider sp = robot.getGyroSensor().getAngleMode();
		gyro = new GyroscopeAdapter(sp, 1000);
		
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
	
	public void stop() {
		active.set(false);
	}
	
	@Override
	public void run() {
		
		angle.set(0);
		
		while (active.get()) {
			int lastAngle = angle.get();
			
			int intAngle = (int) gyro.getAngle();
			angle.set(intAngle);
			
			System.out.println(intAngle + "-" + lastAngle + "=" + (intAngle-lastAngle));
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	

}
