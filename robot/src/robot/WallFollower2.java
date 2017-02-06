package robot;

import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;

public class WallFollower2 {
	
	private Robot robot;
	private int oldDistance;
	private int isDistance;
	private UltrasonicSensorThread distanceSensor;
	
	public WallFollower2(Robot robot) {
		this.robot = robot;
		distanceSensor = new UltrasonicSensorThread(robot);
		distanceSensor.start(Modes.Left);
		robot.getMovement().backwardDirection();
		robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
	}
	
	public void stayOnWall() {
		oldDistance = isDistance;
		updateDistanceToWall();
		if (isDistance < GlobalValues.WALL_DIST_MIN) {
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 50);
		}
		else if (isDistance > GlobalValues.WALL_DIST_MAX) {
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + 50);
		}
		else {
			int diff = isDistance - oldDistance;
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + diff);
		}
		robot.getMovement().goForward();
	}
	
	private void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance();
	}
}