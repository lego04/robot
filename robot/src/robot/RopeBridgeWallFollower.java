package robot;

import com.jcraft.jsch.UserAuthGSSAPIWithMIC;

import test.USSensorTest;
import util.GlobalValues;

public class RopeBridgeWallFollower extends WallFollower2 {
	
	private boolean active;
	
	private int MIN_DIST = 80;
	private int MAX_DIST = 130;
	private int INF_DIST = 500;
	private int CRITICAL_DIST = 25;
	private int SPEED = 150;
	
	public RopeBridgeWallFollower(Robot robot) {
		super(robot);
		active = true;
	}
	
	@Override
	public void startFollowing() {
		while (active) {
			stayOnWall();
		}
		robot.getMovement().stopAll();
		robot.getMovement().turnOnPointLeft(10);
		robot.getMovement().goForwardDist(10);
		distanceSensor.rotateSensor(90);
	}
	
	@Override
	public void stayOnWall() {
		updateDistanceToWall();
		if (isDistance < MIN_DIST) {
			if (isDistance < CRITICAL_DIST) {
				robot.getLeftWheel().setSpeed(50);
				System.out.println("Critical: " + isDistance);
			}
			else {
				robot.getLeftWheel().setSpeed(SPEED - 80);
				robot.getRightWheel().setSpeed(SPEED);
				System.out.println("Too close: " + isDistance);
			}
		}
		else if (isDistance > MAX_DIST) {
			if (isDistance > INF_DIST) {
				System.out.println("End of Wall: " + isDistance);
				active = false;
				return;
			} else {
				System.out.println("Too far: " + isDistance);
				robot.getRightWheel().setSpeed(SPEED);
				robot.getLeftWheel().setSpeed(SPEED + 80);
			}
		}
		else if (GlobalValues.WALL_DIST_MIN < isDistance && isDistance < GlobalValues.WALL_DIST_MAX) {
			System.out.println("All right: " + isDistance);
			robot.getLeftWheel().setSpeed(SPEED);
			robot.getRightWheel().setSpeed(SPEED);
		}
		robot.getMovement().goForward();
	}
	
}
