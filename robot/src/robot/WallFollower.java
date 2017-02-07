package robot;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.TouchAdapter;
import robot.Robot;
import sensorThreads.LightSensorThread;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;
import util.Movement;
import util.TouchSensorID;

/** WallFollwer class is the generalisation of the Left- and RightWallFollower
 * @author Rashad Asgarbayli
 */
public class WallFollower {
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
	
	//boolean recoveringFromBump;
	
	private Movement movement;
	
	private TouchAdapter td;
	
	
	/** Standard constructor of the calls. Needs reference to the {@link Robot} and {@link UltrasonicSensorThread}
	 * @param robot : {@link Robot}
	 * @param sensor : {@link UltrasonicSensorThread}
	 */
	public WallFollower(Robot robot) {
		this.robot = robot;
		this.distanceSensor = robot.getThreadPool().getUltraSonicSensorThread();
		this.distanceSensor.start(Modes.Left);
		this.hypotenus = 13.5; // cm
		this.mustDistance = 8; // cm
		this.isDistance = this.mustDistance; // Just to be sure, that it was also initialised.
		updateDistanceToWall();
		//this.recoveringFromBump = false;
		this.movement = robot.getMovement();
		movement.setSpeed(GlobalValues.WALLFOLLOWSPEED);
		movement.backwardDirection();
		td = new TouchAdapter(robot.getTouch1());
	}
	
	/** Robot follows the wall using it as an anchor point to find its way through the labyrinth.
	 * Robot stays in this state, until it decides, that it is out of the labyrinth.
	 */
	public void followTheWall() {
		//movement.goForwardSpeed(GlobalValues.WALLFOLLOWSPEED);
		LightSensorThread lst = robot.getThreadPool().getLightSensorThread();
		while (!lst.nextStateReady()) {
			if (td.isPressed()) {
				act();
			}
			controllTheDistanceToWall();
		}
	}
	
	
	/** Controller, that tries to keep the robot at the wall. */
	private void controllTheDistanceToWall() {
		updateDistanceToWall();
		int diff = mustDistance - isDistance;
		double sin = Math.min(1.0, Math.max(-1.0, diff / hypotenus));
		double angle = - Math.toDegrees(Math.asin(sin));
		movement.updateWheelSpeeds((int) angle);
	}
	
	/** Updates the <code>distanceToWall</code> - distance between the wall and the robot */
 	private void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance();
	}

	public void act() {
		System.out.println("act to bump");
		//turn right
		//WallFollower.this.recoveringFromBump = true;
		movement.stopAll();
		movement.goBackwardDist(20);
		//movement.goForwardSpeed(GlobalValues.WALLFOLLOWSPEED);
		movement.turnOnPointRight(-90);
		//WallFollower.this.recoveringFromBump = false;
	}

	/*
	@Override
	public Robot getRobot() {
		return robot;
	}
	*/
}
