package robot;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FusorDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;
import sensorThreads.LightSensorThread;
import util.Movement;

/**
 * class representing robot
 * @author maik
 *
 */
public class Robot {
	
	// wheel properties
	
	public static final double wheelDiameter = 8.16;
	public static final double trackWidth = 12;
	
	public static final float ultraSonicMaxDistanceCM = 40;
	public static final int ultraSonicDelayMS = 250;
	
	public static final double ultraSonicStopDistanceM = 0.2;
	
	private Movement mov;

	private DifferentialPilot pilot;
	
	// Motors
	
	//TODO: Ports der Motoren anpassen
	private static final RegulatedMotor leftWheel = Motor.A;
	private static final RegulatedMotor rightWheel = Motor.B;
	public static final RegulatedMotor ultrasonicMotor = Motor.C;
	
	//Sensors
	
	private Port colorSensorPort = SensorPort.S3; 
	private Port irSensorPort = SensorPort.S4; 
	private Port gyroSensorPort = SensorPort.S2;
	private Port touchSensor = SensorPort.S1; 
//	private Port touchSensor2Port = SensorPort.S2; 
	
	
	private EV3ColorSensor colorSensor;
	private EV3UltrasonicSensor usSensor;
	private EV3GyroSensor gyroSensor;
	private EV3TouchSensor touch1;	
	
	//Range detectors
	private FeatureDetector ultraSonicDetector;
	private FusorDetector touchDetector;
	
	
	public Robot() {
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel);
		
		usSensor = new EV3UltrasonicSensor(irSensorPort);
		
		/*ultraSonicDetector = new RangeFeatureDetector(new RangeFinderAdapter(us), ultraSonicMaxDistanceCM, ultraSonicDelayMS);
		ultraSonicDetector.addListener(new UltraSonicDistanceListener(pilot, ultraSonicStopDistanceM));
		ultraSonicDetector.enableDetection(false);*/
		
		colorSensor = new EV3ColorSensor(colorSensorPort);

//		SusSensor = new EV3UltrasonicSensor(irSensorPort);

		gyroSensor = new EV3GyroSensor(gyroSensorPort);


		touch1 = new EV3TouchSensor(touchSensor);
//		touch2 = new EV3TouchSensor(touchSensor2Port);
		//touchDetector = new FusorDetector()
		pilot.setTravelSpeed(10);
		mov = new Movement(this, 150);
		
	}
	
	
	
	/**
	 * starts the robot
	 */
	public void start() {
 		//pilot.forward();
		//pilot.steer(100);
		LightSensorThread lst = new LightSensorThread(this);
 		new LineFollower(this, lst).adjustLine();
	}
	
	// TODO: l�schen? wird nicht mehr gebraucht?
	/**
	 * ultrasonic sensor is in front of robot. Set pilot to move backwards in order to get forwards.
	 */
	public void setUltraSonicFront() {
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel, true);
	}
	
	// TODO: l�schen? wird nicht mehr gebraucht?
	/**
	 * ultra sonic sensor is behind of robot. Set pilot to move forward in order to get forwards.
	 */
	public void setUltraSonicBack() {
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel);
	}
	
	//
	// Getter
	//
	
	public Movement getMovement() {
		return mov;
	}
	
	public RegulatedMotor getLeftWheel() {
		return leftWheel;
	}
	
	public RegulatedMotor getRightWheel() {
		return rightWheel;
	}
	public EV3ColorSensor getColorSensor() {
		return colorSensor;
	}
	
	public EV3UltrasonicSensor getUSSensor() {
		return usSensor;
	}
	
	public EV3GyroSensor getGyroSensor() {
		return gyroSensor;
	}
	
	public DifferentialPilot getPilot() {
		return pilot;
	}
	
	public EV3TouchSensor getTouch1() {
		return touch1;
	}
	/*
	public EV3TouchSensor getTouch2() {
		return touch2;
	}
	*/
}

