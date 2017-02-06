package robot;

import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;
import util.Movement;

public class BridgeFollower {
	
	private Robot robot;
	private UltrasonicSensorThread usSensor;
	private Movement mv;
	
// TODO: anpassen
	private final int DISTANCE_LIMIT = 10;
	private final int HIGH_SPEED = 200;
	private final int LOW_SPEED = HIGH_SPEED / 2;
	
	public BridgeFollower(Robot robot) {
		
		this.robot = robot;
		mv = robot.getMovement();
		usSensor = new UltrasonicSensorThread(robot);
		
	}
	
	public void start() {
		
		usSensor.start(Modes.Down);
		mv.backwardDirection();
		mv.goForwardSpeed(60);
		
		int distance = 0;
		
		while (true) {
			
// TODO: standardmäßig linkskurve, bei abgrund: korrekturi
			
			distance = usSensor.getDistance();
			System.out.println("OUT: " + distance);
			
			if (distance <= DISTANCE_LIMIT) {
				
				mv.stopAll();
				
				mv.stopAll();
				robot.getLeftWheel().setSpeed(HIGH_SPEED);
				robot.getRightWheel().setSpeed(LOW_SPEED);
				mv.goForward();
				
				while (usSensor.getDistance() <= DISTANCE_LIMIT) {
				}
				
			} else {
				
				mv.stopAll();
				robot.getRightWheel().setSpeed(HIGH_SPEED);
				robot.getLeftWheel().setSpeed(LOW_SPEED);
				mv.goForward();
				while (usSensor.getDistance() > DISTANCE_LIMIT) {
				}
				
				/*
				robot.getRightWheel().setSpeed(HIGH_SPEED);
				robot.getLeftWheel().setSpeed(HIGH_SPEED);
				mv.goForward();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
			}
			
// TODO: implement
			/*
			if (von Brücke runter) {
				break;
			}
			 */
			
// TODO: besser?
			/*
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} */
			
		}
		
		// nächster Schritt // Linie folgen?
				
	}

}
