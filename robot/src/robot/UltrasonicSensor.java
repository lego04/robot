package robot;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import util.globalValues;

public class UltrasonicSensor implements Runnable{

	public enum Modes {
		Left,
		Right,
		BothSides
	}
	
	private Modes mode;
	
	private RangeFinderAdapter rangeFinder;
	private RegulatedMotor usMotor;
	
	private AtomicBoolean active;
	private Thread usSensorThread;
	
	private AtomicInteger leftDistance;
	private AtomicInteger rightDistance;
	
	private int angle;
	
	public UltrasonicSensor(Robot robot) {
		
		usSensorThread = new Thread(this);
		
		mode = Modes.BothSides;
		
		SampleProvider sampleProvider = robot.getUSSensor().getDistanceMode();
		rangeFinder = new RangeFinderAdapter(sampleProvider);
		
		usMotor = Robot.ultrasonicMotor;
		
		leftDistance = new AtomicInteger(0);
		rightDistance = new AtomicInteger(0);
		active = new AtomicBoolean(true);
		
	}
	
	public int getLeftDistance() {
		return leftDistance.get(); // in cm?
	}
	
	public int getRightDistance() {
		return rightDistance.get(); // in cm?
	}
	
	public void start(Modes mode, int angle) {
		this.mode = mode;
		this.angle = angle;
		usSensorThread.start();
	}
	
	public void stop() {
		active.set(false);
	}
	
	private void bothSides() {
		usMotor.rotate(angle);
		boolean leftRight = true;
		while (active.get()){
			if (leftRight) {
				usMotor.rotate(- (2 * angle));
				int rightDistanceInt = (int) (rangeFinder.getRange() * globalValues.floatToInt);
				rightDistance.set(rightDistanceInt);
				System.out.println("right: " + rightDistance.get());
			} else {
				usMotor.rotate(2 * angle);
				int leftDistanceInt = (int) (rangeFinder.getRange() * globalValues.floatToInt);
				leftDistance.set(leftDistanceInt);
				System.out.println("left: " + leftDistance.get());
			}
			leftRight = !leftRight;
		}
	}
	
	private void oneSide() {
		
	}

	@Override
	public void run() {
		usMotor.rotateTo(0);
		leftDistance.set(0);
		rightDistance.set(0);
		active.set(true);
		
		if (mode == Modes.BothSides) {
			bothSides();
		}
		
	}

}
