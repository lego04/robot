package sensorThreads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import robot.Robot;
import util.GlobalValues;


public class UltrasonicSensorThread implements Runnable {

	private static final boolean DEBUG = false;

	public enum Modes {
		Left,
		Down
	}
	private Modes mode;

	private RangeFinderAdapter rangeFinder;
	private RegulatedMotor usMotor;

	private AtomicBoolean active;
	private Thread usSensorThread;

	private AtomicInteger distance;
	
	public UltrasonicSensorThread(Robot robot) {

		mode = Modes.Down;

		SampleProvider sampleProvider = robot.getUSSensor().getDistanceMode();
		rangeFinder = new RangeFinderAdapter(sampleProvider);

		usMotor = Robot.ultrasonicMotor;

		distance = new AtomicInteger(0);
		active = new AtomicBoolean(true);

	}

	public int getDistance() {
		return distance.get(); // in cm
	}

	public void start(Modes mode) {
		stop();
		this.mode = mode;
		usSensorThread = new Thread(this);
		active.set(true);
		usSensorThread.start();
	}

	public void stop() {
		active.set(false);
	}
	
	/**
	 * Only use this if you know what you're doing !!!
	 */
	public void rotateSensor(int angle) {
		usMotor.rotate(angle);
	}

	@Override
	public void run() {
		usMotor.rotateTo(0);
		distance.set(0);

		if (mode == Modes.Left) {
			usMotor.rotate(-90);
		}
		
		while (active.get()) {
			float range = rangeFinder.getRange();
			int distanceInt = (int) (range * GlobalValues.floatToInt);
			distance.set(distanceInt);
			if (DEBUG) {
				System.out.println("left: " + distance.get());
			}
			
			try {
// TODO: change to normal value
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}

}
