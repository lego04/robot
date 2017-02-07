package robot;

import util.GlobalValues;

public class RopeBridgeWallFollower extends WallFollower2 {
	
	private boolean active;
	
	private int MIN_DIST = 50;
	private int MAX_DIST = 100;
	private int INF_DIST = 500;
	
	public RopeBridgeWallFollower(Robot robot) {
		super(robot);
		active = true;
	}
	
	@Override
	public void startFollowing() {
		while (active) {
			stayOnWall();
		}
	}
	
	@Override
	public void stayOnWall() {
		updateDistanceToWall();
		if (isDistance < MIN_DIST) {
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - 50);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			System.out.println("Too close: " + isDistance);
		}
		else if (isDistance > MAX_DIST) {
			if (isDistance > INF_DIST) {
				System.out.println("End of Wall: " + isDistance);
				//active = false;
				return;
			} else {
				System.out.println("Too far: " + isDistance);
				robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
				robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + 50);	
			}
		}
		else if (GlobalValues.WALL_DIST_MIN < isDistance && isDistance < GlobalValues.WALL_DIST_MAX) {
			System.out.println("All right: " + isDistance);
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED);
		}
		robot.getMovement().goForward();
	}
	
}
