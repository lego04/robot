package robot;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import util.globalValues;

public class UltrasonicSensor implements Runnable{

	public enum Modes {
		Labyrinth,
		Bridge
	}
	
	private Robot robot;
	private Modes mode;
	
	private RangeFinderAdapter rangeFinder;
	private RegulatedMotor usMotor;
	
	private AtomicBoolean active;
	private Thread usSensorThread;
	
	private AtomicInteger leftDistance;
	private AtomicInteger rightDistance;
	
	public UltrasonicSensor(Robot robot) {
		
		this.robot = robot;	
// TODO: Set real starting mode
		mode = Modes.Labyrinth;
		
		SampleProvider sampleProvider = robot.getUSSensor().getDistanceMode();
		rangeFinder = new RangeFinderAdapter(sampleProvider);
		
		usMotor = robot.ultrasonicMotor;
		
		leftDistance = new AtomicInteger();
		rightDistance = new AtomicInteger();
		
		leftDistance.set(0);
		rightDistance.set(0);
		
	}
	
	public float getLeftDistance() {
		return ((float) leftDistance.get()) / globalValues.floatToInt; // in Metern?
	}
	
	public float getRightDistance() {
		return ((float) rightDistance.get()) / globalValues.floatToInt; // in Metern?
	}
	
	public void start() {
		
		startWalldetection();
	}
	
	private void startWalldetection() {
		usMotor.rotateTo(0);
		usMotor.rotate(90);
		for (int i = 0; i < 100000; i++) {
			if (i % 2 == 0) {
				usMotor.rotate(-180);
				int rightDistanceInt = (int) (rangeFinder.getRange() * globalValues.floatToInt);
				rightDistance.set(rightDistanceInt);
				System.out.println("right: " + rightDistance);
			} else {
				usMotor.rotate(180);
				int leftDistanceInt = (int) (rangeFinder.getRange() * globalValues.floatToInt);
				leftDistance.set(leftDistanceInt);
				System.out.println("left: " + leftDistance);
			}
		}
	}
	
	private void startBridgeDetection() {
		usMotor.rotateTo(0);
// TODO: implement
	}

	@Override
	public void run() {
		if (mode == Modes.Labyrinth) {
			
		}
		
	}

}
