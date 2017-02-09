package robot;

import lejos.hardware.sensor.EV3UltrasonicSensor;
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
	/** Distance to the wall as <b>millimetres (mm)</b>, that should be between the robot and wall */
	private final int mustDistance;
	/** Current distance to the wall as <b>millimetres (mm)</b>, that read from {@link UltrasonicSensorThread}. */
	private int isDistance;
	/** <code>true</code> during the recovering after bumping, else <code>false</code>. */
	boolean steered;
	
	private int speed;
	
	/** Standard constructor of the calls. Needs reference to the {@link Robot} and {@link UltrasonicSensorThread}
	 * @param robot : {@link Robot}
	 */
	public WallFollower(Robot robot) {
		this.robot = robot;
		this.distanceSensor = robot.getThreadPool().getUltraSonicSensorThread();
		this.distanceSensor.start(Modes.Left);
		this.hypotenus = 135.0; // mm
		this.mustDistance = 80; // mm
		this.isDistance = this.mustDistance; // Just to be sure, that it was also initialised.
		updateDistanceToWall();
		this.steered = false;
		this.speed = GlobalValues.WALLFOLLOWSPEED / 2;
	}
	
	/** Robot follows the wall using it as an anchor point to find its way through the labyrinth.
	 * Robot stays in this state, until it decides, that it is out of the labyrinth.
	 */
	public void followTheWall() {
		//movement.goForwardSpeed(speed);
		//LightSensorThread lst = robot.getThreadPool().getLightSensorThread();
		goForward();
		//while (!lst.nextStateReady()) {
		while (true) {
			updateDistanceToWall();
			if (this.isDistance > 400) {
				if (!steered) {
					System.out.println("================");
					robot.getLeftWheel().startSynchronization();
					robot.getLeftWheel().stop();
					robot.getRightWheel().stop();
					robot.getLeftWheel().endSynchronization();
					steered = true;
					System.out.println("================");
					robot.getLeftWheel().resetTachoCount();
					robot.getLeftWheel().forward();
					//while (robot.getLeftWheel().getTachoCount() < speed / 2) {}
					System.out.println("================");
					goForward();
				}
				continue;
			} else {
				controllTheDistanceToWall();
			}
		}
	}
	
	
	/** Controller, that tries to keep the robot at the wall. */
	private void controllTheDistanceToWall() {
		double diff = mustDistance - isDistance;
		System.out.println(isDistance + " => " + diff);
		double sin = Math.min(1.0, Math.max(-1.0, diff / hypotenus));
		double angle = Math.toDegrees(Math.asin(sin));
		double percent = ((angle * 100.0) / 90.0) / 100.0;
		int speedChange = (int) Math.floor(speed * percent);
		robot.getLeftWheel().setSpeed(speed - speedChange);
		robot.getRightWheel().setSpeed(speed + speedChange);
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().backward();
		robot.getRightWheel().backward();
		robot.getLeftWheel().endSynchronization();
	}
	
	/** Updates the <code>distanceToWall</code> - distance between the wall and the robot */
 	private void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance();
	}
	
	private void goForward() {
		robot.getLeftWheel().setSpeed(speed);
		robot.getRightWheel().setSpeed(speed);
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().backward();
		robot.getRightWheel().backward();
		robot.getLeftWheel().endSynchronization();
	}
}
