package robot;

import robot.Robot;
import util.globalValues;
import util.TouchSensorID;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * WallFollwer class is the generalisation of the Left- and RightWallFollower
 * @author Rashad Asgarbayli
 */
public abstract class WallFollower {
	private DifferentialPilot pilot;
	private UltrasonicSensor distanceSensor;
	private float distanceToWall;
	
	/**
	 * Standard constructor of the calls. Needs reference to the {@link Robot} and {@link UltrasonicSensor}
	 * @param robot : {@link Robot}
	 * @param sensor : {@link UltrasonicSensor}
	 */
	public WallFollower(Robot robot, UltrasonicSensor sensor) {
		this.pilot = robot.getPilot();
		this.distanceSensor = sensor;
		this.distanceToWall = 0.0f;
		updateDistanceToWall();
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
			// TODO: Wait to be done.
			if (isBumped()) {
				tryNextSide();
				// TODO: Wait to be done.
			}
		}
	}
	
	/**
	 * Decides, if the robot still in the labyrinth or not.
	 * @return <code>true</code>, if the robot still in the labyrinth, else <code>false</code>.
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
		float diff = 0.3f - distanceToWall;
		double turnRate = (-1.0) * (distanceToTurnRate(diff));
		pilot.steer(turnRate);
	}
	
	/**
	 * Converts distance values read from {@link UltrasonicSensor} to the <code>turnRate</code> values needed for <code>pilot.steer()</code> method.
	 * @param distance : <code>float</code>, value read from {@link UltrasonicSensor}.
	 * @return <code>double</code> value for <code>turnRate</code>.
	 */
	private double distanceToTurnRate(float distance) {
		// FIXME; Factor must be set right. 1.0 is wrong.
		return Math.min(200, Math.max(-200, 1.0 * distance));
	}
	
	/**
	 * Updates the <code>distanceToWall</code> - distance between the wall and the robot
	 */
 	private void updateDistanceToWall() {
		this.distanceToWall = distanceSensor.getLeftDistance();
	}
 	
 	/**
 	 * Checks reads touch sensor values and decides, whether the robot bumped from front.
 	 * @return <code>true</code> if robot bumped from front, else <code>false</code>.
 	 */
 	private boolean isBumped() {
 		return false;
 	}

 	/**
 	 * The method turns the robot to another side, that robot tries to avoid another bumping.
 	 */
 	private void tryNextSide() {
 		pilot.travel(-10);
		pilot.rotate(globalValues.RIGHT * 90);
 	}
}
