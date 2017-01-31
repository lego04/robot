package robot;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FusorDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;

/**
 * class representing robot
 * @author maik
 *
 */
public class Robot {
	
	// wheel properties
	
	public static final double wheelDiameter = 5; //TODO
	public static final double trackWidth = 10; //TODO
	
	public static final float ultraSonicMaxDistanceCM = 40;
	public static final int ultraSonicDelayMS = 250;
	
	public static final double ultraSonicStopDistanceM = 0.2;
	
	

	private DifferentialPilot pilot;
	
	// Motors
	
	//TODO: Ports der Motoren anpassen
	private static final RegulatedMotor leftWheel = Motor.A;
	private static final RegulatedMotor rightWheel = Motor.B;
	private static final RegulatedMotor ultrasonicMotor = Motor.C;
	
	//Sensors
	
	private Port colorSensorPort = SensorPort.S3; //TODO
	private Port irSensorPort = SensorPort.S4; //TODO
//	private Port gyroSensorPort = SensorPort.S1; //TODO
	private Port touchSensor1Port = SensorPort.S1; //TODO
	private Port touchSensor2Port = SensorPort.S2; //TODO
	
	
	private EV3ColorSensor colorSensor;
	//private EV3UltrasonicSensor irSensor;
	private EV3GyroSensor gyroSensor;
	private EV3TouchSensor touch1, touch2;
	
	
	//Range detectors
	private FeatureDetector ultraSonicDetector;
	private FusorDetector touchDetector;
	
	
	public Robot() {
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel);
		/*EV3UltrasonicSensor us = new EV3UltrasonicSensor(irSensorPort);
		ultraSonicDetector = new RangeFeatureDetector(new RangeFinderAdapter(us), ultraSonicMaxDistanceCM, ultraSonicDelayMS);
		ultraSonicDetector.addListener(new UltraSonicDistanceListener(pilot, ultraSonicStopDistanceM));
		ultraSonicDetector.enableDetection(false);*/
//		colorSensor = new EV3ColorSensor(colorSensorPort);
//		gyroSensor = new EV3GyroSensor(gyroSensorPort);
		touch1 = new EV3TouchSensor(touchSensor1Port);
		touch2 = new EV3TouchSensor(touchSensor2Port);
		//touchDetector = new FusorDetector()
		pilot.setTravelSpeed(10);
		
	}
	
	
	
	/**
	 * starts the robot
	 */
	public void start() {
 		//pilot.forward();
		//pilot.steer(100);
 		new LineFollower(this).adjustLine(true);
	}
	
	//
	// Getter
	//
	
	public EV3ColorSensor getColorSensor() {
		return colorSensor;
	}
	
	public DifferentialPilot getPilot() {
		return pilot;
	}
	
	public EV3TouchSensor getTouch1() {
		return touch1;
	}
	
	public EV3TouchSensor getTouch2() {
		return touch2;
	}
	
}
