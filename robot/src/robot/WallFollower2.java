package robot; 

import lejos.robotics.TouchAdapter;
import sensorThreads.LightSensorThread;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;

public class WallFollower2 {
	
	protected Robot robot;
	private int oldDistance;
	protected int isDistance;
	protected UltrasonicSensorThread distanceSensor;
	private TouchAdapter td;
	private boolean isPressed;
	
	public WallFollower2(Robot robot) {
		this.robot = robot;
		distanceSensor = robot.getThreadPool().getUltraSonicSensorThread();
		distanceSensor.start(Modes.Left);
		robot.getMovement().backwardDirection();
		robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		td = new TouchAdapter(robot.getTouch1());
	}
	
	public void startFollowing() {
		LightSensorThread lst = robot.getThreadPool().getLightSensorThread();
		boolean isInterupted = false;
		while (!lst.nextStateReady() && !isInterupted) {
			isPressed = td.isPressed();
			if (isPressed) {
				robot.getMovement().goBackwardDist(5);
				robot.getMovement().turnOnPointLeft(110);
			}
			stayOnWall();
			isInterupted = robot.isInterrupted().get();
		}
		robot.getMovement().stopAll();
	}
	
	public void stayOnWall() {
		oldDistance = isDistance;
		updateDistanceToWall();
		if (isDistance < GlobalValues.WALL_DIST_MIN) {
			if (isDistance < GlobalValues.WALL_DIST_CRITICAL) {
				robot.getLeftWheel().setSpeed(50);
				//System.out.println("Critical: " + isDistance);
			}
			else {
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 50);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
				//System.out.println("Too close: " + isDistance);
			}
		}
		else if (isDistance > GlobalValues.WALL_DIST_MAX) {
			if (isDistance > GlobalValues.LAB_LEFT_CURVE) {
				//System.out.println("Left Curve: " + isDistance);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 120);
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + 120);
			}
			else {
				//System.out.println("Too far: " + isDistance);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + 50);				
			}
		}
		else if (GlobalValues.WALL_DIST_MIN < isDistance && isDistance < GlobalValues.WALL_DIST_MAX) {
			//System.out.println("All right: " + isDistance);
			//int diff = isDistance - oldDistance;
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		}
		robot.getMovement().goForward();
	}
	
	protected void updateDistanceToWall() {
		this.isDistance = distanceSensor.getDistance();
	}
}