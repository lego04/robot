package robot;

import org.jfree.chart.servlet.ServletUtilities;

import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;

public class BridgeFollower {
	
	private Robot robot;
	private UltrasonicSensorThread usSensor;
	
	private boolean active;
	
// TODO: anpassen
	private final int DISTANCE_LIMIT = 30;
	
	public BridgeFollower(Robot robot) {
		
		this.robot = robot;
		usSensor = new UltrasonicSensorThread(robot);
		
		active = false;
	}
	
	public void start() {
		
		//wenden
		robot.setUltraSonicFront();
		usSensor.start(Modes.Left, 45);
		
		while (active) {
			
			int distance = usSensor.getLeftDistance();
			
			if (distance < DISTANCE_LIMIT) {
				// nach rechts drehen
			} else if (distance > DISTANCE_LIMIT) {
				// nach links drehen
			}
			
		}
		
		
		
	}

}
