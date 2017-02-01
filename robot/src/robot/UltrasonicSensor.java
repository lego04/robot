package robot;

import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor {

	private Robot robot;
	//private RotatingRangeScanner rotScanner;
	private RangeFinderAdapter rangeFinder;
	private RegulatedMotor usMotor;
	
// TODO: richtige Werte
	private float leftDistance = 0;
	private float rightDistance = 0;
	
	public UltrasonicSensor(Robot robot) {
		
		this.robot = robot;		
		
		SampleProvider sampleProvider = robot.getUSSensor().getDistanceMode();
		rangeFinder = new RangeFinderAdapter(sampleProvider);
		
		usMotor = robot.ultrasonicMotor;
		
	}
	
	public float getLeftDistance() {
		return leftDistance; // in Metern
	}
	
	public float getRightDistance() {
		return rightDistance; // in Metern
	}
	
	public void start() {
		
		usMotor.rotate(90);
		for (int i = 0; i < 20; i++) {
			if (i % 2 == 0) {
				usMotor.rotate(-180);
				rightDistance = rangeFinder.getRange();
				System.out.println("right: " + rightDistance);
			} else {
				usMotor.rotate(180);
				leftDistance = rangeFinder.getRange();
				System.out.println("left: " + leftDistance);
			}
		}
	}

}
