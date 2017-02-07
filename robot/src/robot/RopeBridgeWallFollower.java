package robot;

public class RopeBridgeWallFollower extends WallFollower2 {
	
	private boolean active;
	
	private int MIN_DIST = 80;
	private int MAX_DIST = 130;
	private int INF_DIST = 250;
	private int CRITICAL_DIST = 50;
	private int SPEED = 150;
	
	private int IDEAL_DIST = 80;
	
	private int infCounter;
	
	public RopeBridgeWallFollower(Robot robot) {
		super(robot);
		active = true;
		robot.getRightWheel().setSpeed(SPEED);
		robot.getLeftWheel().setSpeed(SPEED);
		infCounter = 0;
	}
	
	@Override
	public void startFollowing() {
		while (active) {
			stayOnWall();
		}
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
		int minusSpeed = (int) SPEED - (diff / 2) ;
		int plusSpeed = (int) SPEED + (diff / 2);
		//System.out.println(SPEED + "+" + diff + "=" + speed );
		if (minusSpeed > 2 * SPEED) {
			minusSpeed = 2 * SPEED;
		}
		if (plusSpeed > 2 * SPEED) {
			plusSpeed = 2 * SPEED;
		}
		robot.getLeftWheel().setSpeed(minusSpeed);
		
		if (isDistance > INF_DIST) {
			infCounter++;
			if (infCounter >= 10) {
				goForwardAndHopeForTheBest();
			}
		} else {
			infCounter = 0;
			robot.getRightWheel().setSpeed(plusSpeed);
			robot.getLeftWheel().setSpeed(minusSpeed);
		}
		
		
		
		
		
		
		
		
		/*
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
		*/
		robot.getMovement().goForward();
		
	}
	
	private void goForwardAndHopeForTheBest() {
		System.out.println("FORWARD");
		robot.getMovement().turnOnPointRight(10);
		robot.getLeftWheel().setSpeed(3 * SPEED);
		robot.getRightWheel().setSpeed(3 * SPEED);
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
