package util;

import javax.crypto.spec.SecretKeySpec;

import lejos.robotics.RegulatedMotor;
import robot.Robot;

public class Movement {
	
	private Robot robot;
	private boolean reverse = false;
	
	public Movement(Robot robot) {
		this.robot = robot;
		RegulatedMotor motors[] = {robot.getRightWheel()};
		robot.getLeftWheel().synchronizeWith(motors);
	}
	
	public void goForward() {
		if (!reverse) {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().forward();
			robot.getLeftWheel().forward();
			robot.getLeftWheel().endSynchronization();			
		}
		else {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().backward();
			robot.getLeftWheel().backward();
			robot.getLeftWheel().endSynchronization();
		}
	}
	
	public void goForwardSpeed(int speed) {
		robot.getLeftWheel().setSpeed(speed);
		robot.getRightWheel().setSpeed(speed);
		goForward();
	}
	
	/**
	 * Method to go forward a given Time
	 * 
	 * @param time 
	 * 			time in Milliseconds
	 */
	public void goForwardTime(int time) {
		robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 15);
		robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 15);
		goForward();
		try {
			Thread.sleep(time);
		}
		catch (Exception e) { }
		stopAll();
	}
	
	public void speedUpRight() {
		robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() + 10);
		robot.getRightWheel().forward();
	}
	
	public void speedUpRight(int speedUp) {
		robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() + speedUp);
		robot.getRightWheel().forward();
	}
	
	public void slowDownRight() {
		if (robot.getRightWheel().getSpeed() > 0) {
			robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() - 10);
			robot.getRightWheel().forward();
		}
	}
	
	public void slowDownRight(int slowDown) {
		if (robot.getRightWheel().getSpeed() > 0 + slowDown) {
			robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() - slowDown);
			robot.getRightWheel().forward();
		}
	}
	
	public void speedUpLeft() {
		robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() + 10);
		robot.getLeftWheel().forward();
	}
	
	public void speedUpLeft(int speedUp) {
		robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() + speedUp);
		robot.getLeftWheel().forward();
	}
	
	public void slowDownLeft() {
		if (robot.getLeftWheel().getSpeed() > 0) {
			robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() - 10);
			robot.getLeftWheel().forward();
		}
	}
	
	public void slowDownLeft(int slowDown) {
		if (robot.getLeftWheel().getSpeed() > 0 + slowDown) {
			robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() - slowDown);
			robot.getLeftWheel().forward();
		}
	}
	
	public void turnOnPointLeft() {
		robot.getLeftWheel().startSynchronization();
		robot.getRightWheel().forward();
		robot.getLeftWheel().backward();
		robot.getLeftWheel().endSynchronization();
	}
	
	public void turnOnPointRight() {
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().forward();
		robot.getRightWheel().backward();
		robot.getLeftWheel().endSynchronization();
	}
	
	public void stopAll() {
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().stop();
		robot.getRightWheel().stop();
		robot.getLeftWheel().endSynchronization();
	}
	
	public void reverseDirection() {
		reverse = !reverse;
		robot.setUltraSonicBack();
	}
}
