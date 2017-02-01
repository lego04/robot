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
	private final float mustDistance;
	
	public WallFollower(Robot robot, UltrasonicSensor sensor) {
		this.pilot = robot.getPilot();
		this.sensor = sensor;
		this.distanceToWall = 0.0f;
		updateDistanceToWall();
		// TODO: Need to find out the must distance via measuring.
		this.mustDistance = 0.0f;
	}
	
	/**
	 * Robot follows the wall using it as an anchor point to find its way through the labyrinth.
	 * Robot stays in this state, until it decides, that it is out of the labyrinth.
	 */
	public void followTheWall() {
		while (isInLabyrinth()) {
			controllTheDistanceToWall();
			// TODO: Wait to be done.
			pilot.travel(20.0);
			if (isBumped()) {
				tryNextSide();
			}
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
		updateDistanceToWall();
		float diff = mustDistance - distanceToWall;
		double turnRate = (-1.0) * (distanceToTurnRate(diff));
		pilot.steer(turnRate);
	}
	
	/**
	 * Converts distance values read from UltrasonicSensor to the turnRate values needed for pilot.steer() method.
	 * @param distance : <b>float</b>, value read from UltrasonicSensor.
	 * @return <b>double</b> value for turnRate.
	 */
	private double distanceToTurnRate(float distance) {
		// FIXME; Factor must be set right. 1.0 is wrong.
		return Math.min(200, Math.max(-200, 1.0 * distance));
	}
	
	/**
	 * Updates the distance between the wall and the 
	 */
 	private void updateDistanceToWall() {
		this.distanceToWall = sensor.getLeftDistance();
	}
 	
 	/**
 	 * Checks reads touch sensor values and decides, whether the robot bumped from front.
 	 * @return <b>true</b> if robot bumped from front, else <b>false</b>.
 	 */
 	private boolean isBumped() {
 		// TODO: Read touch sensor values and make a decision.
 		return false;
 	}

 	/**
 	 * The method turns the robot to another side, that robot tries to avoid another bumping.
 	 */
 	private void tryNextSide() {
 		pilot.travel(-10.0);
 		// TODO: Turn robot according to the bumping result
 	}
}
