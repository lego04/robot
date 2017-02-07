package robot;

//import sensorThreads.GyroSensorThread;
import util.GlobalValues;

public class RopeBridgeWallFollower extends WallFollower2 {
	
	private boolean active;
	
	private int MIN_DIST = 80;
	private int MAX_DIST = 130;
	private int INF_DIST = 500;
	private int CRITICAL_DIST = 50;
	private int SPEED = 150;
	
//	private GyroSensorThread gyro;
	
	public RopeBridgeWallFollower(Robot robot) {
		super(robot);
		active = true;
		//gyro = robot.getThreadPool().getGyroSensorThread();
	}
	
	@Override
	public void startFollowing() {
		while (active) {
			stayOnWall();
		}
		robot.getMovement().stopAll();
		robot.getMovement().turnOnPointLeft(10);
		robot.getLeftWheel().setSpeed(SPEED);
		robot.getRightWheel().setSpeed(SPEED);
		robot.getMovement().goForward();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//robot.getMovement().stopAll();
		distanceSensor.rotateSensor(90);
	}
	
	@Override
	public void stayOnWall() {
		updateDistanceToWall();
		if (isDistance < MIN_DIST) {
			if (isDistance < CRITICAL_DIST) {
				robot.getLeftWheel().setSpeed(25);
				System.out.println("Critical: " + isDistance);
			}
			else {
				robot.getLeftWheel().setSpeed(SPEED - 50);
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
				robot.getLeftWheel().setSpeed(SPEED + 50);
			}
		}
		else {
			System.out.println("All right: " + isDistance);
			robot.getLeftWheel().setSpeed(SPEED);
			robot.getRightWheel().setSpeed(SPEED);
		}
		robot.getMovement().goForward();
	}
	
}
