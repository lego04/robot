package util;

import javax.crypto.spec.SecretKeySpec;

import robot.Robot;

public class Movement {
	
	private Robot robot;
	
	public Movement(Robot robot) {
		this.robot = robot;
	}
	
	public void goForward() {
		robot.getRightWheel().forward();
		robot.getLeftWheel().forward();
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
		robot.getLeftWheel().forward();
		robot.getRightWheel().forward();
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
	
	public void stopAll() {
		robot.getLeftWheel().stop();
		robot.getRightWheel().stop();
	}
	
}
