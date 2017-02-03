package robot;

import java.time.*;

import javax.xml.bind.JAXBElement.GlobalScope;

import interfaces.Actor;
import util.GlobHelpMethods;
import util.TouchSensorID;
import util.GlobalValues;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.LightDetectorAdaptor;
import listeners.TouchSensorListener;
import sensorThreads.LightSensorThread;

public class LineFollower implements Actor {

	/**
	 * pointer to robot instance
	 */
	private Robot robot;

	/**
	 * interface for color / light sensor
	 */
	
	private LightSensorThread lst;

	public LineFollower(Robot robot, LightSensorThread lst) {
		this.robot = robot;
		this.lst = lst;

	}
	
	
	public void adjustLine() {
		while (true) {		//TODO: better implementation, now only for testing purpose
			if (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
				robot.getLeftWheel().resetTachoCount();
				robot.getMovement().stopAll();
				robot.getRightWheel().setSpeed(10);
				robot.getMovement().goForward();
				while (lst.getLastLightValue() < GlobalValues.MINLIGHT) {	
					if (robot.getLeftWheel().getTachoCount() > GlobalValues.LEFTWHEEL90DEGREE) {
						break;
					}
				}
				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 10);
			}
			else if (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				robot.getMovement().stopAll();
				robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 5);
				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 5);
				robot.getMovement().turnOnPointLeft();
				while (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				}
			}
			else {
				robot.getMovement().stopAll();
				robot.getMovement().goForwardSpeed(GlobalValues.LINETRAVELSPEED * 10);
				while (GlobalValues.MINLIGHT < lst.getLastLightValue() &&
						lst.getLastLightValue() < GlobalValues.MAXLIGHT) {
				}
			}
		}
	
	}

	/**
	 * shows current light value whenever enter is pressed
	 */
	public void debug() {
		robot.getPilot().forward();
		Button.ENTER.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(Key k) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(Key k) {
				if (k.getId() == Button.ID_ENTER) {
					float light = lst.getDetector().getLightValue();
					System.out.println(light);
				}

			}
		});
	}

	@Override
	public void act(TouchSensorID id) {
		robot.getPilot().stop();
		
	}

	@Override
	public Robot getRobot() {
		return robot;
	}
}
