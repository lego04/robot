package sensorThreads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import robot.Robot;
import util.globalValues;

public class UltrasonicSensorThread implements Runnable{

	public enum Modes {
		Left,
		Right,
		BothSides
	}
	
	public enum Directions {
		Left,
		Right
	}
	
	private Modes mode;
	
	private RangeFinderAdapter rangeFinder;
	private RegulatedMotor usMotor;
	
	private AtomicBoolean active;
	private Thread usSensorThread;
	
	private AtomicInteger leftDistance;
	private AtomicInteger rightDistance;
	
	private int angle;
	private AtomicBoolean lookingLeft;	
	private AtomicBoolean movementEnabled;
	
	public UltrasonicSensorThread(Robot robot) {
		
		mode = Modes.BothSides;
		
		SampleProvider sampleProvider = robot.getUSSensor().getDistanceMode();
		rangeFinder = new RangeFinderAdapter(sampleProvider);
		
		usMotor = Robot.ultrasonicMotor;
		
		leftDistance = new AtomicInteger(0);
		rightDistance = new AtomicInteger(0);
		active = new AtomicBoolean(true);
		movementEnabled = new AtomicBoolean(true);
		lookingLeft = new AtomicBoolean(true);
		
	}
	
	public int getLeftDistance() {
		return leftDistance.get(); // in cm?
	}
	
	public int getRightDistance() {
		return rightDistance.get(); // in cm?
	}
	
	public boolean getLookingLeft() {
		return lookingLeft.get();
	}
	
	public void setMovementEnabled(boolean mv) {
		movementEnabled.set(mv);;
	}

	public void moveTo(Directions dir) {
		usMotor.rotateTo(0);
		if (dir == Directions.Left) {
			usMotor.rotate(angle);
		} else {
			usMotor.rotate(-angle);
		}
	}
	
	public void start(Modes mode, int angle) {
		this.mode = mode;
		this.angle = angle;
		usSensorThread = new Thread(this);
		usSensorThread.start();
	}
	
	public void stop() {
		active.set(false);
	}
	
	private void bothSides() {
		usMotor.rotate(angle);
		lookingLeft.set(true);;
		boolean leftRight = true;
		while (active.get()){
			if (leftRight) {
				if (movementEnabled.get()) {
					usMotor.rotate(- (2 * angle));
					lookingLeft.set(false);;
					leftRight = !leftRight;
				}				
				int rightDistanceInt = (int) (rangeFinder.getRange() * globalValues.floatToInt);
				rightDistance.set(rightDistanceInt);
				System.out.println("right: " + rightDistance.get());
			} else {
				if (movementEnabled.get()) {
					usMotor.rotate(2 * angle);
					lookingLeft.set(true);;
					leftRight = !leftRight;
				}
				
				int leftDistanceInt = (int) (rangeFinder.getRange() * globalValues.floatToInt);
				leftDistance.set(leftDistanceInt);
				System.out.println("left: " + leftDistance.get());
			}
			
		}
	}
	
	private void leftSide() {
		usMotor.rotate(angle);
		lookingLeft.set(true);
		
		while (active.get()) {
			int distanceInt = (int) (rangeFinder.getRange() * globalValues.floatToInt);
			leftDistance.set(distanceInt);
			System.out.println("left: " + leftDistance.get());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void rightSide() {
		usMotor.rotate(-angle);
		lookingLeft.set(false);
		
		while (active.get()) {
			int distanceInt = (int) (rangeFinder.getRange() * globalValues.floatToInt);
			rightDistance.set(distanceInt);
			System.out.println("right: " + rightDistance.get());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		usMotor.rotateTo(0);
		leftDistance.set(0);
		rightDistance.set(0);
		active.set(true);
		
		if (mode == Modes.BothSides) {
			bothSides();
		} else if (mode == Modes.Left) {
			leftSide();
		} else {
			rightSide();
		}
		
	}

}
