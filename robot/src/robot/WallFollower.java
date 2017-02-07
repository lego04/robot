package robot;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.TouchAdapter;
import robot.Robot;
import sensorThreads.LightSensorThread;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;

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
	/** <code>true</code> during the recovering after bumping, else <code>false</code>. */
	boolean recoveringFromBump;
	/** Reference to the {@link TouchAdapter} */
	private TouchAdapter td;
	
	
	/** Standard constructor of the calls. Needs reference to the {@link Robot} and {@link UltrasonicSensorThread}
	 * @param robot : {@link Robot}
	 */
	public WallFollower(Robot robot) {
		this.robot = robot;
		this.distanceSensor = robot.getThreadPool().getUltraSonicSensorThread();
		this.distanceSensor.start(Modes.Left);
		this.hypotenus = 13.5; // cm
		this.mustDistance = 10; // cm
		this.isDistance = this.mustDistance; // Just to be sure, that it was also initialised.
		updateDistanceToWall();
		this.recoveringFromBump = false;
		td = new TouchAdapter(robot.getTouch1());
	}
	
	/** Robot follows the wall using it as an anchor point to find its way through the labyrinth.
	 * Robot stays in this state, until it decides, that it is out of the labyrinth.
	 */
	public void followTheWall() {
		//movement.goForwardSpeed(GlobalValues.WALLFOLLOWSPEED);
		//LightSensorThread lst = robot.getThreadPool().getLightSensorThread();
		goForward();
		//while (!lst.nextStateReady()) {
		while (true) {
			recoveringFromBump = td.isPressed();
			if (recoveringFromBump) {
				act();
			} else {
				controllTheDistanceToWall();
			}
		}
	}
	
	
	/** Controller, that tries to keep the robot at the wall. */
	private void controllTheDistanceToWall() {
		updateDistanceToWall();
		int diff = mustDistance - isDistance;
		double sin = Math.min(1.0, Math.max(-1.0, diff / hypotenus));
		double angle = Math.toDegrees(Math.asin(sin));
		double percent = ((angle * 100.0) / 90.0) / 100.0 - 0.1;
		int speedChange = (int) Math.floor(GlobalValues.WALLFOLLOWSPEED * percent);
		int min = (int) (GlobalValues.WALLFOLLOWSPEED * 0.40);
		robot.getLeftWheel().setSpeed(Math.max(min, GlobalValues.WALLFOLLOWSPEED - speedChange));
		robot.getRightWheel().setSpeed(Math.max(min, GlobalValues.WALLFOLLOWSPEED + speedChange));
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().backward();
		robot.getRightWheel().backward();
		robot.getLeftWheel().endSynchronization();
	}
	
	/** Updates the <code>distanceToWall</code> - distance between the wall and the robot */
 	private void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance() / 10;
	}

	private void act() {
		System.out.print("act to bump");
		goBackward(5);
		robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		robot.getLeftWheel().resetTachoCount();
		robot.getLeftWheel().startSynchronization();
		robot.getRightWheel().backward();
		robot.getLeftWheel().forward();
		robot.getLeftWheel().endSynchronization();
		while (robot.getLeftWheel().getTachoCount() < 110) {}
		this.recoveringFromBump = false;
		System.out.println("...DONE");
		goForward();
	}
	
	private void goForward() {
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().stop();
		robot.getRightWheel().stop();
		robot.getLeftWheel().endSynchronization();
		robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().backward();
		robot.getRightWheel().backward();
		robot.getLeftWheel().endSynchronization();
	}
	
	private void goBackward(int distance) {
		robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().stop();
		robot.getRightWheel().stop();
		robot.getLeftWheel().endSynchronization();
		robot.getLeftWheel().resetTachoCount();
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().forward();
		robot.getRightWheel().forward();
		robot.getLeftWheel().endSynchronization();
		while (robot.getLeftWheel().getTachoCount() < distance * GlobalValues.DEGREE_TO_DIST) {};
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().stop();
		robot.getRightWheel().stop();
		robot.getLeftWheel().endSynchronization();
	}
}
