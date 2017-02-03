package robot;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import robot.Robot;
import sensorThreads.UltrasonicSensorThread;
import util.GlobalValues;
import util.Movement;
import util.TouchSensorID;

/** WallFollwer class is the generalisation of the Left- and RightWallFollower
 * @author Rashad Asgarbayli
 */
public class WallFollower implements interfaces.Actor {
	/** Reference to {@link Robot} */
	private Robot robot;
	/** Reference to {@link UltrasonicSensorThread} */
	private UltrasonicSensorThread distanceSensor;
	/** Distance between the turn axis and {@link EV3UltrasonicSensor} sensor as <b>centimetres (cm)</b> */
	private final double hypotenus;
	/** Distance to the wall as <b>centimetres (cm)</b>, that should be between the robot and wall */
	private final int mustDistance;
	/** Current distance to the wall as <b>centimetres (cm)</b>, that read from {@link UltrasonicSensorThread}. */
	private int isDistance;
	/** Current distance to the wall as <b>centimetres (cm)</b>, that read from {@link UltrasonicSensorThread}. */
	private MovementState movState;
	
	private Movement movement;
	
	/** Standard constructor of the calls. Needs reference to the {@link Robot} and {@link UltrasonicSensorThread}
	 * @param robot : {@link Robot}
	 * @param sensor : {@link UltrasonicSensorThread}
	 */
	public WallFollower(Robot robot, UltrasonicSensorThread sensor) {
		this.robot = robot;
		this.distanceSensor = sensor;
		this.hypotenus = 13.0; // cm
		this.mustDistance = 8; // cm
		this.isDistance = this.mustDistance; // Just to be sure, that it was also initialised.
		updateDistanceToWall();
		this.movState = MovementState.FORWARD;
		this.movement = new Movement(robot);
		movement.backwardDirection();
	}
	
	/** Robot follows the wall using it as an anchor point to find its way through the labyrinth.
	 * Robot stays in this state, until it decides, that it is out of the labyrinth.
	 */
	public void followTheWall() {
		movement.goForwardSpeed(GlobalValues.WALLFOLLOWSPEED);
		this.movState = MovementState.FORWARD;
		while (isInLabyrinth()) {
			controllTheDistanceToWall();
		}
		// Out of the labyrinth.
		movement.stopAll();
	}
	
	/** Decides, if the robot still in the labyrinth or not.
	 * @return <code>true</code>, if the robot still in the labyrinth, else <code>false</code>.
	 */
	private boolean isInLabyrinth() {
		// TODO: Decide to change state, if the robot out of the maze.
		return true;
	}
	
	/** Controller, that tries to keep the robot at the wall. */
	private void controllTheDistanceToWall() {
		updateDistanceToWall();
		int diff = mustDistance - isDistance;
		/*if (Math.abs(diff) < 2) {
			return;
		}*/
		double sin = Math.min(1.0, Math.max(-1.0, diff / hypotenus));
		double angle = - Math.toDegrees(Math.asin(sin));
		/*double turnRate = angle < -0.0 ? 85.0 : -85.0;
		if (Math.abs(angle) > 0.0 && movState != MovementState.STEERING) {
			System.out.println("Angle: " + angle);
			//robot.getPilot().stop();
			//waitComplete();
			movState = MovementState.STEERING;
			robot.getPilot().steer(turnRate, angle);
			//waitComplete();
			movement.goForwardSpeed(GlobalValues.WALLFOLLOWSPEED);
			this.movState = MovementState.FORWARD;
		}*/
		movement.setSpeed((int) angle);
	}
	
	/** Updates the <code>distanceToWall</code> - distance between the wall and the robot */
 	private void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance();
	}
 	
 	/** Stops the execution until the current movement is completed. */
	private void waitComplete() {
		while(robot.getPilot().isMoving()) {
			Thread.yield();
		}
	}

	@Override
	public void act(TouchSensorID id) {
		//turnRight();
		followTheWall();
	}

	@Override
	public Robot getRobot() {
		return robot;
	}
	

	public enum MovementState {
		STEERING,
		FORWARD;
	}
}
