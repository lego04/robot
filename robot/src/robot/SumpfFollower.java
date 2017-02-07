package robot; 

import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;

public class SumpfFollower {
	
	private Robot robot;
	private int isDistance;
	private UltrasonicSensorThread distanceSensor;
	boolean isPressed;
	int sumpfSpeed;
	
	public SumpfFollower(Robot robot) {
		this.robot = robot;
		distanceSensor = new UltrasonicSensorThread(robot);
		distanceSensor.start(Modes.Left);
		this.sumpfSpeed = GlobalValues.WALLFOLLOWSPEED;
		robot.getMovement().forwardDirection();
		robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
	}
	
	public void startFollowing() {
		while (true) {
			stayOnWall();
		}
	}
	
	private void stayOnWall() {
		updateDistanceToWall();
		if (isDistance < GlobalValues.WALL_DIST_MIN) {
			if (isDistance < GlobalValues.WALL_DIST_CRITICAL) {
				robot.getLeftWheel().setSpeed(50);
				System.out.println("Critical: " + isDistance);
			}
			else {
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 80);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
				System.out.println("Too close: " + isDistance);
			}
		}
		else if (isDistance > GlobalValues.WALL_DIST_MAX) {
			if (isDistance > GlobalValues.LAB_LEFT_CURVE) {
				System.out.println("Left Curve: " + isDistance);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 110);
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + 110);
			}
			else {
				System.out.println("Too far: " + isDistance);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + 80);				
			}
		}
		else if (GlobalValues.WALL_DIST_MIN < isDistance && isDistance < GlobalValues.WALL_DIST_MAX) {
			System.out.println("All right: " + isDistance);
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		}
		robot.getMovement().goForward();
	}
	
	private void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance();
	}
}