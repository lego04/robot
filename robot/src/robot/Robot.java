package robot;

import java.beans.FeatureDescriptor;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;

/**
 * class representing robot
 * @author maik
 *
 */
public class Robot {
	
	// wheel properties
	
	public static final double wheelDiameter = 5; //TODO
	public static final double trackWidth = 10; //TODO
	
	
	

	private DifferentialPilot pilot;
	
	// Motors
	
	//TODO: Ports der Motoren anpassen
	private static final RegulatedMotor leftWheel = Motor.A;
	private static final RegulatedMotor rightWheel = Motor.B;
	
	//Sensors
	
	private Port colorSensorPort = SensorPort.S1; //TODO
	private Port irSensorPort = SensorPort.S4; //TODO
	private Port gyroSensorPort = SensorPort.S1; //TODO
	private Port touchSensor1Port = SensorPort.S1; //TODO
	private Port touchSensor2Port = SensorPort.S1; //TODO
	
	private EV3ColorSensor colorSensor;
	private EV3IRSensor irSensor;
	private EV3GyroSensor gyroSensor;
	private EV3TouchSensor touch1, touch2;
	
	
	public Robot() {
		//TODO: set variables
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel);
//		colorSensor = new EV3ColorSensor(colorSensorPort);
		irSensor = new EV3IRSensor(irSensorPort);
//		gyroSensor = new EV3GyroSensor(gyroSensorPort);
//		touch1 = new EV3TouchSensor(touchSensor1Port);
//		touch2 = new EV3TouchSensor(touchSensor2Port);
	}
	
	
	/**
	 * starts the robot
	 */
	public void start() {
 		//pilot.forward();
 		FeatureDetector detector = new RangeFeatureDetector(new RangeFinderAdapter(irSensor.getDistanceMode()), 40, 5000);
 		detector.addListener(new FeatureListener() {
			
			@Override
			public void featureDetected(Feature feature, FeatureDetector detector) {
				System.out.println(feature.getRangeReading().getRange());
			}
		});
 		
	}
}
