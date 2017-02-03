package sensorThreads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import robot.Robot;
import util.GlobalValues;


public class UltrasonicSensorThread implements Runnable {

	private static final boolean DEBUG = true;

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

		usSensorThread = new Thread(this);
		mode = Modes.Down;

		SampleProvider sampleProvider = robot.getUSSensor().getDistanceMode();
		rangeFinder = new RangeFinderAdapter(sampleProvider);

		usMotor = Robot.ultrasonicMotor;

		distance = new AtomicInteger(0);
		active = new AtomicBoolean(true);

	}

	public int getDistance() {
		return distance.get(); // in cm?
	}

	public void start(Modes mode) {
		this.mode = mode;
		usSensorThread = new Thread(this);
		usSensorThread.start();
	}

	public void stop() {
		active.set(false);
	}

	@Override
	public void run() {
		usMotor.rotateTo(0);
		distance.set(0);

		if (mode == Modes.Left) {
			usMotor.rotate(-90);
		}
		
		while (active.get()) {
			float[] ranges = rangeFinder.getRanges();
			int distanceInt = (int) (ranges[0] * GlobalValues.floatToInt);
			distance.set(distanceInt);
			if (DEBUG) {
				System.out.println("left: " + distance.get());
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
