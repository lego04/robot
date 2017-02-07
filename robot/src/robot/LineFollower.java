package robot;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import sensorThreads.LightSensorThread;
import util.GlobalValues;

public class LineFollower {

	/**
	 * pointer to robot instance
	 */
	private Robot robot;

	/**
	 * interface for color / light sensor
	 */
	
	private LightSensorThread lst;
	
	private boolean endOfLine = false;

	public LineFollower(Robot robot, LightSensorThread lst) {
		this.robot = robot;
		this.lst = lst;

	}
	
	
	public void adjustLine()  {
		while (!endOfLine) {
			if (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
				
				robot.getRightWheel().resetTachoCount();
				robot.getLeftWheel().resetTachoCount();
				/*
				robot.getMovement().stopAll();
				robot.getRightWheel().setSpeed(1);
				robot.getMovement().goForward();
				*/
				robot.getMovement().stopAll();
				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED / 2);
				robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED);
				robot.getMovement().turnOnPointRight();
				while (lst.getLastLightValue() < GlobalValues.AVG_LIGHT) {
					if (robot.getLeftWheel().getTachoCount() > GlobalValues.LEFT_WHEEL_90_DEGREE) {
						robot.getMovement().stopAll();
						System.out.println("Left: " + robot.getLeftWheel().getTachoCount());
						robot.getLeftWheel().resetTachoCount();
						while (robot.getLeftWheel().getTachoCount() > - GlobalValues.LEFT_WHEEL_90_DEGREE) {
							robot.getMovement().turnOnPointLeft();
						}
						robot.getMovement().stopAll();
						System.out.println("Minus Left: " + robot.getLeftWheel().getTachoCount());
						endOfLine = true;
					}
				}
				System.out.println("Notify: " + lst.getLastLightValue());
				//System.out.println("Left: " + robot.getLeftWheel().getTachoCount());
				//System.out.println("Right: " + robot.getRightWheel().getTachoCount());
				if (!endOfLine) robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED);
			}
			else if (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				robot.getMovement().stopAll();
				robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED / 2);
				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED);
				robot.getMovement().turnOnPointLeft();
				while (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				}
			}
			else {
				robot.getMovement().stopAll();
				robot.getMovement().goForwardSpeed(GlobalValues.LINETRAVELSPEED);
				while (GlobalValues.MINLIGHT < lst.getLastLightValue() &&
						lst.getLastLightValue() < GlobalValues.MAXLIGHT) {
				}
			}
		}
		System.out.println("End of Line reached!");
		robot.getMovement().stopAll();
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

}
