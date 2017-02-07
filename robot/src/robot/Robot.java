package robot;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FusorDetector;
import sensorThreads.LightSensorThread;
import util.Movement;
import util.States;

/**
 * class representing robot
 * 
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

	// TODO: Ports der Motoren anpassen
	private static final RegulatedMotor leftWheel = Motor.A;
	private static final RegulatedMotor rightWheel = Motor.B;
	public static final RegulatedMotor ultrasonicMotor = Motor.C;

	// Sensors

	private Port colorSensorPort = SensorPort.S3;
	private Port irSensorPort = SensorPort.S4;
	private Port gyroSensorPort = SensorPort.S2;
	private Port touchSensor = SensorPort.S1;
	// private Port touchSensor2Port = SensorPort.S2;

	private EV3ColorSensor colorSensor;
	private EV3UltrasonicSensor usSensor;
	private EV3GyroSensor gyroSensor;
	private EV3TouchSensor touch1;

	// Range detectors
	private FeatureDetector ultraSonicDetector;
	private FusorDetector touchDetector;

	private LightSensorThread lightSensorThread;

	/**
	 * state machine defining behavior of robot
	 */
	private States states;

	public Robot() {
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel);

		usSensor = new EV3UltrasonicSensor(irSensorPort);

		colorSensor = new EV3ColorSensor(colorSensorPort);

		gyroSensor = new EV3GyroSensor(gyroSensorPort);

		touch1 = new EV3TouchSensor(touchSensor);
		// touchDetector = new FusorDetector()
		pilot.setTravelSpeed(10);
		mov = new Movement(this, 150);

		states = new States(this);

		lightSensorThread = new LightSensorThread(this);

	}

	/**
	 * starts the roboo
	 * @param raceMode if true robot starts the program but will wait for press of Button.Up to begin
	 */
	public void start(boolean raceMode) {
		// pilot.forward();
		// pilot.steer(100);

		// TODO: Light Sensor Thread starten?

		// new LineFollower(this, lst).adjustLine();
		if (raceMode) {
			Button.UP.waitForPress();
		}
		states.start();
	}

	// TODO: l�schen? wird nicht mehr gebraucht?
	/**
	 * ultrasonic sensor is in front of robot. Set pilot to move backwards in
	 * order to get forwards.
	 */
	public void setUltraSonicFront() {
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel, true);
	}

	// TODO: l�schen? wird nicht mehr gebraucht?
	/**
	 * ultra sonic sensor is behind of robot. Set pilot to move forward in order
	 * to get forwards.
	 */
	public void setUltraSonicBack() {
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel);
	}

	/**
	 * sets robot to next state according to {@link #states}
	 */
	public void nextState() {
		states.nextState();
	}

	public boolean isNextStateReady() {
		return lightSensorThread.nextStateReady();
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

	public States getStates() {
		return states;
	}
}
