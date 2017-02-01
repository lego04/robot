package robot;

import robot.Robot;
import util.globalValues;
import util.TouchSensorID;

/**
 * WallFollwer class is the generalisation of the Left- and RightWallFollower
 * @author Rashad Asgarbayli
 */
public class WallFollower implements interfaces.Actor {
	private Robot robot;
	private UltrasonicSensor distanceSensor;
	private float distanceToWall;
	
	/**
	 * Standard constructor of the calls. Needs reference to the {@link Robot} and {@link UltrasonicSensor}
	 * @param robot : {@link Robot}
	 * @param sensor : {@link UltrasonicSensor}
	 */
	public WallFollower(Robot robot, UltrasonicSensor sensor) {
		this.robot = robot;
		this.distanceSensor = sensor;
		this.distanceToWall = 0.22f; // Just to be sure, that it was initialised.
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
			robot.getPilot().travel(20.0);
			// TODO: Wait to be done.
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
		float diff = 0.22f - distanceToWall;
		double turnRate = globalValues.RIGHT * (distanceToTurnRate(diff));
		robot.getPilot().steer(turnRate);
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

	@Override
	public void act(TouchSensorID id) {
		robot.getPilot().stop();
		robot.getPilot().travel(-10.0);
		robot.getPilot().rotate(globalValues.RIGHT * 90.0);
		robot.getPilot().travel(10.0);
	}

	@Override
	public Robot getRobot() {
		return robot;
	}
}
