package robot;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.chassis.WheeledChassis.Modeler;
import lejos.robotics.navigation.MovePilot;

/**
 * class representing robot
 * @author maik
 *
 */
public class Robot {
	
	public static final double diameter = 0; //TODO
	

	private MovePilot pilot;
	//TODO: Ports der Motoren anpassen
	private static final RegulatedMotor leftWheel = Motor.A;
	private static final RegulatedMotor rightWheel = Motor.A;
	
	public Robot() {
		//TODO: set variables
		Modeler left = new Modeler(leftWheel, diameter);
		Modeler right = new Modeler(rightWheel, diameter);
		Wheel[] wheels = new Wheel[] { left, right };
		WheeledChassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
	}
	
	
	/**
	 * starts the robot
	 */
	public void start() {
		System.out.println("Hello World");
	}
}
