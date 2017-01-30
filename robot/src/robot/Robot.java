package robot;

import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

/**
 * class representing robot
 * @author maik
 *
 */
public class Robot {

	private MovePilot pilot;
	
	public Robot() {
		//TODO: set variables
		Wheel[] wheels = null;
		int dim = 0;
		WheeledChassis chassis = new WheeledChassis(wheels, dim);
		pilot = new MovePilot(chassis);
	}
	
	
	/**
	 * starts the robot
	 */
	public void start() {
		//TODO: implement
	}
}
