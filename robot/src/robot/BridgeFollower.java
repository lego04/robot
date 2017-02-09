package robot;

import sensorThreads.LightSensorThread;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.Movement;

public class BridgeFollower {

	private Robot robot;
	private UltrasonicSensorThread usSensor;
	private Movement mv;

	// TODO: anpassen
	private final int DISTANCE_LIMIT = 100;
	private final int HIGH_SPEED = 200;
	private final int LOW_SPEED = HIGH_SPEED / 2;

	public BridgeFollower(Robot robot) {

		this.robot = robot;
		mv = robot.getMovement();
		usSensor = robot.getThreadPool().getUltraSonicSensorThread();

	}

	public void start() {

		usSensor.start(Modes.Down);
		mv.backwardDirection();
		mv.goForwardSpeed(60);

		int distance = 0;

		LightSensorThread lst = robot.getThreadPool().getLightSensorThread();
		boolean nextStateReady = false;
		boolean isInterrupted = false;
		// TODO: uncomment
		do {
			// while (true) {
			nextStateReady = lst.nextStateReady();
			isInterrupted = robot.isInterrupted().get();

			distance = usSensor.getDistance();
			// System.out.println("OUT: " + distance);

			if (distance <= DISTANCE_LIMIT) {

				mv.stopAll();
				robot.getLeftWheel().setSpeed(HIGH_SPEED);
				robot.getRightWheel().setSpeed(LOW_SPEED);
				mv.goForward();
				while (usSensor.getDistance() <= DISTANCE_LIMIT) {
					nextStateReady = lst.nextStateReady();
					isInterrupted = robot.isInterrupted().get();
					if (nextStateReady || isInterrupted) {
						break;
					}
				}

			} else {

				mv.stopAll();
				robot.getRightWheel().setSpeed(HIGH_SPEED);
				robot.getLeftWheel().setSpeed(LOW_SPEED);
				mv.goForward();
				while (usSensor.getDistance() > DISTANCE_LIMIT) {
					nextStateReady = lst.nextStateReady();
					isInterrupted = robot.isInterrupted().get();
					if (nextStateReady || isInterrupted) {
						break;
					}
				}

			}

		} while (!nextStateReady && !isInterrupted);
		// TODO: uncomment
		/*
		 * if (robot.isNextStateReady()) { return; }
		 */
	}
	// System.out.println("After loop. ");

}
