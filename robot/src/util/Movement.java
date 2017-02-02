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
		if (!reverse) {
			robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() + 10);
			robot.getRightWheel().forward();
		}
		else {
			robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() + 10);
			robot.getLeftWheel().backward();
		}
		
	}
	
	public void speedUpRight(int speedUp) {
		if (!reverse) {
			robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() + speedUp);
			robot.getRightWheel().forward();
		}
		else {
			robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() + speedUp);
			robot.getLeftWheel().backward();
		}
	}
	
	public void slowDownRight() {
		if (!reverse) {
			if (robot.getRightWheel().getSpeed() > 0) {
				robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() - 10);
				robot.getRightWheel().forward();
			}			
		}
		else {
			if (robot.getLeftWheel().getSpeed() > 0) {
				robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() - 10);
				robot.getLeftWheel().backward();
			}
		}
	}
	
	public void slowDownRight(int slowDown) {
		if (!reverse) {
			if (robot.getRightWheel().getSpeed() > 0 + slowDown) {
				robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() - slowDown);
				robot.getRightWheel().forward();
			}			
		}
		else {
			if (robot.getLeftWheel().getSpeed() > 0 + slowDown) {
				robot.getLeftWheel().setSpeed(robot.getRightWheel().getSpeed() - slowDown);
				robot.getLeftWheel().backward();
			}
		}
	}
	
	public void speedUpLeft() {
		if (!reverse) {
			robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() + 10);
			robot.getLeftWheel().forward();
		}
		else {
			robot.getRightWheel().setSpeed(robot.getLeftWheel().getSpeed() + 10);
			robot.getRightWheel().backward();
		}
	}
	
	public void speedUpLeft(int speedUp) {
		if (!reverse) {
			robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() + speedUp);
			robot.getLeftWheel().forward();
		}
		else {
			robot.getRightWheel().setSpeed(robot.getLeftWheel().getSpeed() + speedUp);
			robot.getRightWheel().backward();
		}
	}
	
	public void slowDownLeft() {
		if (!reverse) {
			if (robot.getLeftWheel().getSpeed() > 0) {
				robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() - 10);
				robot.getLeftWheel().forward();
			}			
		}
		else {
			if (robot.getRightWheel().getSpeed() > 0) {
				robot.getRightWheel().setSpeed(robot.getLeftWheel().getSpeed() - 10);
				robot.getRightWheel().backward();
			}
		}
	}
	
	public void slowDownLeft(int slowDown) {
		if (!reverse) {
			if (robot.getLeftWheel().getSpeed() > 0) {
				robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() - slowDown);
				robot.getLeftWheel().forward();
			}			
		}
		else {
			if (robot.getRightWheel().getSpeed() > 0) {
				robot.getRightWheel().setSpeed(robot.getLeftWheel().getSpeed() - slowDown);
				robot.getRightWheel().backward();
			}
		}
	}
	
	public void turnOnPointLeft() {
		if (!reverse) {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().forward();
			robot.getLeftWheel().backward();
			robot.getLeftWheel().endSynchronization();
		}
		else {
			robot.getLeftWheel().startSynchronization();
			robot.getLeftWheel().forward();
			robot.getRightWheel().backward();
			robot.getLeftWheel().endSynchronization();
		}
	}
	
	public void turnOnPointRight() {
		if (!reverse) {
			robot.getLeftWheel().startSynchronization();
			robot.getLeftWheel().forward();
			robot.getRightWheel().backward();
			robot.getLeftWheel().endSynchronization();
		}
		else {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().forward();
			robot.getLeftWheel().backward();
			robot.getLeftWheel().endSynchronization();
		}
	}
	
	public void stopAll() {
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().flt();
		robot.getRightWheel().flt();
		robot.getLeftWheel().endSynchronization();
	}
	
	public void backwardDirection() {
		reverse = true;
		robot.setUltraSonicBack();
	}
	
	public void forwardDirection() {
		reverse = false;
		robot.setUltraSonicFront();
	}
}
