package robot;

import lejos.robotics.GyroscopeAdapter;
import sensorThreads.GyroSensorThread;
import sensorThreads.LightSensorThread;

public class RopeBridgeWallFollower extends WallFollower2 {
		
	private int MIN_DIST = 80;
	private int MAX_DIST = 130;
	private int INF_DIST = 250;
	private int CRITICAL_DIST = 50;
	private int SPEED = 150;
	
	private int IDEAL_DIST = 80;
	
	private int infCounter;
	
	//private GyroSensorThread gyro;
	
	public RopeBridgeWallFollower(Robot robot) {
		super(robot);
		robot.getRightWheel().setSpeed(SPEED);
		robot.getLeftWheel().setSpeed(SPEED);
		infCounter = 0;
		//gyro = robot.getThreadPool().getGyroSensorThread();
	}
	
	@Override
	public void startFollowing() {
		LightSensorThread lst = robot.getThreadPool().getLightSensorThread();
		//gyro.start();
		while (!lst.nextStateReady() && !robot.isInterrupted().get()) {
			stayOnWall();
		}
		
		System.out.println("END OF WALL FOLLOWING");
		
		robot.getMovement().turnOnPointLeft(21);
		/*System.out.println("GYRO RESET");
		gyro.reset();
		for (int i = 0; i < 20; i++) {
			int angle = (-23) - gyro.getAngle();
			int s = i % 2 == 0 ? (-1) : 1;
			robot.getMovement().turnOnPointLeft(s * angle);
			System.out.println("Angle: " + angle);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		robot.getLeftWheel().setSpeed(SPEED);
		robot.getRightWheel().setSpeed(SPEED);
		robot.getMovement().goForward();
		
		while (!lst.nextStateReady()) {
			
		}
		
		/*
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		
		
		
		/*
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
		*/
	}
	
	@Override
	public void stayOnWall() {		
		updateDistanceToWall();
		System.out.println(isDistance);
		int diff = IDEAL_DIST - isDistance;
		//System.out.println(diff);
		int minusSpeed = (int) SPEED - (diff);
		int plusSpeed = (int) SPEED + (diff);
		//System.out.println(SPEED + "+" + diff + "=" + speed );
		if (minusSpeed > 2 * SPEED) {
			minusSpeed = 2 * SPEED;
		}
		if (plusSpeed > 2 * SPEED) {
			plusSpeed = 2 * SPEED;
		}
		// robot.getLeftWheel().setSpeed(minusSpeed);
		
		if (isDistance > INF_DIST) {
			goForwardAndHopeForTheBest();
			
		} else {
			infCounter = 0;
			robot.getRightWheel().setSpeed(plusSpeed);
			robot.getLeftWheel().setSpeed(minusSpeed);
		}
		
		
		
		

		robot.getMovement().goForward();
		
	}
	
	private void goForwardAndHopeForTheBest() {
		System.out.println("FORWARD");
		robot.getMovement().turnOnPointRight(10);
		robot.getLeftWheel().setSpeed(SPEED);
		robot.getRightWheel().setSpeed(SPEED);
		robot.getMovement().goForward();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		while (true) {
// TODO: implement
			// 	if (nächstes Hindernis) { return } // und aus aeusserem Loop raus
		}*/
	}
	
}
