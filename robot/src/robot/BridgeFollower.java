package robot;

import sensorThreads.LightSensorThread;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.Movement;

public class BridgeFollower {
	
	private Robot robot;
	private UltrasonicSensorThread usSensor;
	private LightSensorThread lightSensor;
	private Movement mv;
	
// TODO: anpassen
	private final int DISTANCE_LIMIT = 10;
	private final int HIGH_SPEED = 200;
	private final int LOW_SPEED = HIGH_SPEED / 2;
	
	public BridgeFollower(Robot robot) {
		
		this.robot = robot;
		mv = robot.getMovement();
		usSensor = new UltrasonicSensorThread(robot);
		lightSensor = robot.getLightSensorThread();
		
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
				
			}			

			if (lightSensor.nextStateReady()) {
				return;
			}
			
		}
				
	}

}
