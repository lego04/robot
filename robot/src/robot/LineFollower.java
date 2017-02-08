package robot;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import sensorThreads.GyroSensorThread;
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
	private GyroSensorThread gst;
	
	private boolean limited = false;
	
	private int travelSpeed;
	
	private boolean endOfLine = false;

	public LineFollower(Robot robot) {
		this.robot = robot;
		this.lst = robot.getThreadPool().getLightSensorThread();
		this.gst = robot.getThreadPool().getGyroSensorThread();
		this.travelSpeed = GlobalValues.LINETRAVELSPEED;
	}
	
	public void adjustLine(boolean limited) {
		this.limited = limited;
		adjustLine();
		
	}
	
	public void adjustLine()  {
		int i = 0;
		while (!endOfLine && i < 20) {
			if (limited) {
				System.out.println("i: " + i);
				i++;
			}
			if (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
				/*
				robot.getRightWheel().resetTachoCount();
				robot.getLeftWheel().resetTachoCount();
				*/
				robot.getMovement().stopAll();
				int offset = gst.getAngle();
				
				//robot.getMovement().stopAll();
				robot.getRightWheel().setSpeed(1);
				robot.getMovement().goForward();
				//robot.getMovement().stopAll();
				while (lst.getLastLightValue() < GlobalValues.AVG_LIGHT) {
					System.out.println("Gyro: " + (offset - gst.getAngle()));
					if ((offset - gst.getAngle()) > GlobalValues.GYRO_RIGHT) {
						robot.getMovement().stopAll();
						System.out.println("Back: " + (offset - gst.getAngle()));
						//robot.getLeftWheel().resetTachoCount();
						robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED / 2);
						robot.getMovement().goBackward();
						while ((offset - gst.getAngle()) > 0) {
						}
						robot.getMovement().stopAll();
						//System.out.println("Minus Left: " + robot.getLeftWheel().getTachoCount());
						endOfLine = true;
					}
				}
				System.out.println("Notify: " + lst.getLastLightValue());
				//System.out.println("Left: " + robot.getLeftWheel().getTachoCount());
				//System.out.println("Right: " + robot.getRightWheel().getTachoCount());
				if (!endOfLine) robot.getRightWheel().setSpeed(travelSpeed);
			}
			else if (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				robot.getMovement().stopAll();
				robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED / 2);
				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED / 2);
				robot.getMovement().turnOnPointLeft();
				while (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				}
			}
			else {
				robot.getMovement().stopAll();
				robot.getMovement().goForwardSpeed(travelSpeed);
				while (GlobalValues.MINLIGHT < lst.getLastLightValue() &&
						lst.getLastLightValue() < GlobalValues.MAXLIGHT) {
				}
			}
		}
		System.out.println("End of Line reached!");
		robot.getMovement().stopAll();
	}
	
	public void adjustLineSeesaw() {
		int i = 0;
		while (i < 110) {
			if (lst.getLastLightValue() < GlobalValues.AVG_LIGHT) {
				robot.getLeftWheel().setSpeed(robot.getRightWheel().getSpeed() + 30);
				robot.getMovement().goForward();
			}
			else if (lst.getLastLightValue() > GlobalValues.AVG_LIGHT) {
				robot.getLeftWheel().setSpeed(robot.getRightWheel().getSpeed() - 100);
				robot.getMovement().goForward();
			}
			i++;
			System.out.println(i);
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
	
	public void setTravelSpeed(int travelSpeed) {
		this.travelSpeed = travelSpeed;
	}
}
