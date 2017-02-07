package robot; 

import lejos.robotics.TouchAdapter;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;

public class SumpfFollower {
	
	private Robot robot;
	private int oldDistance;
	private int isDistance;
	private UltrasonicSensorThread distanceSensor;
	private TouchAdapter td;
	boolean isPressed;
	
	public SumpfFollower(Robot robot) {
		this.robot = robot;
		distanceSensor = new UltrasonicSensorThread(robot);
		distanceSensor.start(Modes.Left);
		robot.getMovement().backwardDirection();
		robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		td = new TouchAdapter(robot.getTouch1());
	}
	
	public void startFollowing() {
		while (true) {
			stayOnWall();
		}
	}
	
	public void stayOnWall() {
		oldDistance = isDistance;
		updateDistanceToWall();
		if (isDistance < GlobalValues.WALL_DIST_MIN) {
			if (isDistance < GlobalValues.WALL_DIST_CRITICAL) {
				robot.getRightWheel().setSpeed(50);
				System.out.println("Critical: " + isDistance);
			}
			else {
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 80);
				System.out.println("Too close: " + isDistance);
			}
		}
		else if (isDistance > GlobalValues.WALL_DIST_MAX) {
			if (isDistance > GlobalValues.LAB_LEFT_CURVE) {
				System.out.println("Left Curve: " + isDistance);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + 110);
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 110);
			}
			else {
				System.out.println("Too far: " + isDistance);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED +80);
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);				
			}
		}
		else if (GlobalValues.WALL_DIST_MIN < isDistance && isDistance < GlobalValues.WALL_DIST_MAX) {
			System.out.println("All right: " + isDistance);
			//int diff = isDistance - oldDistance;
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		}
		robot.getMovement().goForward();
	}
	
	private void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance();
	}
}