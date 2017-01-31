package robot;

import robot.Robot;

import lejos.robotics.navigation.DifferentialPilot;

/**
 * WallFollwer class is the generalisation of the Left- and RightWallFollower
 * @author Rashad Asgarbayli
 */
public abstract class WallFollower {
	private DifferentialPilot pilot;
	private UltrasonicSensor sensor;
	private float distanceToWall;
	private float acceptableDistance;
	
	public WallFollower(Robot robot, UltrasonicSensor sensor) {
		this.pilot = robot.getPilot();
		this.sensor = sensor;
		this.distanceToWall = sensor.getLeftDistance();
		this.acceptableDistance = 0.0f;
	}
	
	
}
