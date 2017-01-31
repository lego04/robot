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
	private final float acceptableDistance;
	
	public WallFollower(Robot robot, UltrasonicSensor sensor) {
		this.pilot = robot.getPilot();
		this.sensor = sensor;
		this.distanceToWall = 0.0f;
		updateDistanceToWall();
		this.acceptableDistance = 0.0f;
	}
	
	/**
	 * Robot follows the wall using it as an anchor point to find its way through the labyrinth.
	 * Robot stays in this state, until it decides, that it is out of the labyrinth.
	 */
	public void followTheWall() {
		while (isInLabyrinth()) {
			controllTheDistanceToWall();
		}
	}
	
	/**
	 * Decides, if the robot still in the labyrinth or not.
	 * @return <b>true</b>, if the robot still in the labyrinth, else <b>false</b>.
	 */
	private boolean isInLabyrinth() {
		// TODO: Decide to change state, if the robot out of the maze.
		return true;
	}
	
	/**
	 * Controller, that tries to keep the robot at the wall.
	 */
	private void controllTheDistanceToWall() {
		// TODO: Regler to follow the wall and at the same time keep the distance acceptable between them.
	}
	
	/**
	 * Updates the distance between the wall and the 
	 */
	private void updateDistanceToWall() {
		this.distanceToWall = sensor.getLeftDistance();
	}
}
