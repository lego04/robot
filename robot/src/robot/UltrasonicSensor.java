package robot;

import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor {

	private Robot robot;
	//private RotatingRangeScanner rotScanner;
	private RangeFinderAdapter rangeFinder;
	
// TODO: richtige Werte
	private float leftDistance = 0;
	private float rightDistance = 0;
	
	public UltrasonicSensor(Robot robot) {
		
		this.robot = robot;
		SampleProvider sampleProvider = robot.getUSSensor().getDistanceMode();
		rangeFinder = new RangeFinderAdapter(sampleProvider);
		//RangeFinderAdapter rfa = new RangeFinderAdapter(robot.getUSSensor());
		//rotScanner = new RotatingRangeScanner(robot.ultrasonicMotor, rfa);
		
	}
	
	public float getLeftDistance() {
		return leftDistance;
	}
	
	public float getRightDistance() {
		return rightDistance;
	}
	
	public void start() {
		
		for (int i = 0; i < 20; i++) {
			System.out.println("Distance: " + rangeFinder.getRange());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*
		 * - ASSERT START POSITION (NEUTRAL, MIDDLE) -
		 * rotate left (90°)
		 * 
		 * while(not interrupted) {
		 * 	leftDistance = readDistance()
		 * 	rotate right (180°)
		 * 	rightDistance = readDistance()
		 * 	rotate left (180°)
		 */
		
	}

}
