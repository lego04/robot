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
			
			distance = usSensor.getDistance();
			System.out.println("OUT: " + distance);
			
			if (distance <= DISTANCE_LIMIT) {
				
		//		mv.speedUpRight(30);
		//		mv.slowDownLeft(30);
				
				mv.stopAll();
				robot.getLeftWheel().setSpeed(0);
				//robot.getLeftWheel().backward();
				//robot.getLeftWheel().stop();
				mv.speedUpRight(60);
				while (usSensor.getDistance() <= DISTANCE_LIMIT) {
					System.out.println("IN1: " + distance);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} else {
/*				
				mv.stopAll();
				robot.getLeftWheel().setSpeed(1);
				mv.goForward();
*/								
	//			mv.speedUpLeft(30);
	//			mv.slowDownRight(30);
				
				mv.stopAll();
				robot.getRightWheel().setSpeed(0);
				//robot.getRightWheel().backward();
				robot.getRightWheel().stop();
				mv.speedUpLeft(60);
				while (usSensor.getDistance() > DISTANCE_LIMIT) {
					System.out.println("IN2: " + distance);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
	/*		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		*/	
			
			
	/*		
			
			if (distance > DISTANCE_LIMIT) {
				mv.stopAll();
				robot.getLeftWheel().setSpeed(1);
				robot.getMovement().goForward();
				while (distance > DISTANCE_LIMIT) {	
				}
				robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 10);
//			}
//			else if (distance < DISTANCE_LIMIT) {
//				robot.getMovement().stopAll();
//				robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 5);
//				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 5);
//				robot.getMovement().turnOnPointLeft();
//				while (distance > GlobalValues.MAXLIGHT) {
//				}
			}
			else {
				robot.getMovement().stopAll();
				robot.getMovement().goForwardSpeed(GlobalValues.LINETRAVELSPEED * 10);
				while (DISTANCE_LIMIT > distance) {
				}
			}
			
*/			
			
			
			
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
