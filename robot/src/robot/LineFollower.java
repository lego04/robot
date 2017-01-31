package robot;

import lejos.robotics.LightDetectorAdaptor;

public class LineFollower {
	
	/**
	 * pointer to robot instance
	 */
	private Robot robot;
	
	/**
	 * interface for color / light sensor
	 */
	private LightDetectorAdaptor detector;

	public LineFollower(Robot robot) {
		this.robot = robot;
		detector = new LightDetectorAdaptor(robot.getColorSensor());
	}
	
	public void followLine() {
		//TODO
	}
}
