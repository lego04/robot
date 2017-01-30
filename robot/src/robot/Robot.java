package robot;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3SensorConstants;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.internal.ev3.EV3Port;
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
	
	// wheel properties
	
	public static final double diameter = 0; //TODO
	
	
	

	private MovePilot pilot;
	
	// Motors
	
	//TODO: Ports der Motoren anpassen
	private static final RegulatedMotor leftWheel = Motor.A;
	private static final RegulatedMotor rightWheel = Motor.B;
	
	//Sensors
	
	private Port colorSensorPort = SensorPort.S1; //TODO
	private Port irSensorPort = SensorPort.S1; //TODO
	private Port gyroSensorPort = SensorPort.S1; //TODO
	private Port touchSensor1Port = SensorPort.S1; //TODO
	private Port touchSensor2Port = SensorPort.S1; //TODO
	
	private EV3ColorSensor colorSensor;
	private EV3IRSensor irSensor;
	private EV3GyroSensor gyroSensor;
	private EV3TouchSensor touch1, touch2;
	
	
	public Robot() {
		//TODO: set variables
		Modeler left = new Modeler(leftWheel, diameter);
		Modeler right = new Modeler(rightWheel, diameter);
		Wheel[] wheels = new Wheel[] { left, right };
		WheeledChassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
//		colorSensor = new EV3ColorSensor(colorSensorPort);
//		irSensor = new EV3IRSensor(irSensorPort);
//		gyroSensor = new EV3GyroSensor(gyroSensorPort);
//		touch1 = new EV3TouchSensor(touchSensor1Port);
//		touch2 = new EV3TouchSensor(touchSensor2Port);
	}
	
	
	/**
	 * starts the robot
	 */
	public void start() {
		//pilot.forward();
		System.out.println("Hello World");
	}
}
