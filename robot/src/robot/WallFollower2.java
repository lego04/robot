package robot; 

import lejos.robotics.TouchAdapter;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;

public class WallFollower2 {
	
	private Robot robot;
	private int oldDistance;
	private int isDistance;
	private UltrasonicSensorThread distanceSensor;
	private TouchAdapter td;
	boolean isPressed;
	
	public WallFollower2(Robot robot) {
		this.robot = robot;
		distanceSensor = new UltrasonicSensorThread(robot);
		distanceSensor.start(Modes.Left);
		robot.getMovement().backwardDirection();
		robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		td = new TouchAdapter(robot.getTouch1());
	}
	
	public void startFollowing() {
		while (true) {
			isPressed = td.isPressed();
			if (isPressed) {
				robot.getMovement().goBackwardDist(5);
				robot.getMovement().turnOnPointLeft(120);
			}
			stayOnWall();
		}
	}
	
	public void stayOnWall() {
		oldDistance = isDistance;
		updateDistanceToWall();
		if (isDistance < GlobalValues.WALL_DIST_MIN) {
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 50);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			System.out.println("Too close: " + isDistance);
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
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + 50);				
			}
		}
		else if (GlobalValues.WALL_DIST_MIN < isDistance && isDistance < GlobalValues.WALL_DIST_MAX) {
			System.out.println("All right: " + isDistance);
			int diff = isDistance - oldDistance;
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		}
		robot.getMovement().goForward();
	}
	
	private void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance();
	}
}