package robot;

import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.GlobalValues;

public class BridgeFollower {
	
	private Robot robot;
	private UltrasonicSensorThread usSensor;
	
// TODO: anpassen
	private final int DISTANCE_LIMIT = 20;
	
	public BridgeFollower(Robot robot) {
		
		this.robot = robot;
		usSensor = new UltrasonicSensorThread(robot);
		
	}
	
	public void start() {
		
		usSensor.start(Modes.Down);
		
		int distance = 0;
		
		robot.getLeftWheel().forward();
		robot.getRightWheel().forward();
		
		while (true) {
			
			//boolean lookingLeft = usSensor.getLookingLeft();
			
			//distance = 
			
			
			System.out.println(distance);
			if (distance <= DISTANCE_LIMIT) {
				System.out.println("<=" + " " + distance);
				//usSensor.setMovementEnabled(true);
				//robot.getPilot().forward();
				robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 25);
				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 25);
			} else {
				System.out.println(">" + " " + distance);
				//usSensor.setMovementEnabled(false);
				//robot.getPilot().stop();
				robot.getLeftWheel().stop();
				robot.getRightWheel().stop();
				robot.getLeftWheel().setSpeed(0);
				robot.getRightWheel().setSpeed(0);
				
				// was wenn sich die ausrichtung des sensor inzwischen geändert hat?
				//if (lookingLeft == usSensor.getLookingLeft()) {
				//	usSensor.moveTo(lookingLeft ? Directions.Left : Directions.Right);
				//}
				
				//if (lookingLeft) {
					// nach links korrigieren (Fahrtrichtung ist umgekehrt, aber Sensorrichtung bleibt gleich)
// TODO: Wert anpassen
					// robot.getPilot().rotate(20);
					
					robot.getLeftWheel().setSpeed(0);
					robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 25);
				//} else {
					// nach rechts korrigieren
// TODO: Wert anpassen
					// robot.getPilot().rotate(-20);
					robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 25);
					robot.getRightWheel().setSpeed(0);
				//}
			}
			robot.getLeftWheel().forward();
			robot.getRightWheel().forward();
			
// TODO: implement
			/*
			if (von Brücke runter) {
				break;
			}
			 */
			
// TODO: besser?
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		// nächster Schritt // Linie folgen?
		
		
		
	}

}
