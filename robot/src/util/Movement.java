package util;

import javax.crypto.spec.SecretKeySpec;

import lejos.robotics.RegulatedMotor;
import robot.Robot;

public class Movement {

	private Robot robot;
	private boolean reverse = false;
	private int travelSpeed = 150;

	public Movement(Robot robot, int speed) {
		this.robot = robot;
		RegulatedMotor motors[] = { robot.getRightWheel() };
		robot.getLeftWheel().synchronizeWith(motors);
		this.travelSpeed = speed;	
	}
	
	public void setSpeed(int speed) {
		travelSpeed = speed;
		robot.getLeftWheel().setSpeed(travelSpeed);
		robot.getRightWheel().setSpeed(travelSpeed);
	}

	public void goForward() {
		if (!reverse) {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().forward();
			robot.getLeftWheel().forward();
			robot.getLeftWheel().endSynchronization();
		} else {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().backward();
			robot.getLeftWheel().backward();
			robot.getLeftWheel().endSynchronization();
		}
	}

	public void goBackward() {
		if (!reverse) {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().backward();
			robot.getLeftWheel().backward();
			robot.getLeftWheel().endSynchronization();

		} else {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().forward();
			robot.getLeftWheel().forward();
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
	 *            time in Milliseconds
	 */
	public void goForwardDist(int dist) {
		robot.getLeftWheel().resetTachoCount();
		goForward();
		while (robot.getLeftWheel().getTachoCount() < dist * GlobalValues.DEGREE_TO_DIST) {
		}
		stopAll();
	}

	public void goBackwardDist(int dist) {
		robot.getLeftWheel().resetTachoCount();
		goBackward();
		while (robot.getLeftWheel().getTachoCount() > - (dist * GlobalValues.DEGREE_TO_DIST)) {
			//System.out.println("Dist: " + dist * GlobalValues.DEGREE_TO_DIST);
		}
		stopAll();
	}

	public void speedUpRight() {
		if (!reverse) {
			robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() + 10);
			robot.getRightWheel().forward();
		} else {
			robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() + 10);
			robot.getLeftWheel().backward();
		}

	}

	public void speedUpRight(int speedUp) {
		if (!reverse) {
			robot.getRightWheel().setSpeed(robot.getRightWheel().getSpeed() + speedUp);
			robot.getRightWheel().forward();
		} else {
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
		} else {
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
		} else {
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
		} else {
			robot.getRightWheel().setSpeed(robot.getLeftWheel().getSpeed() + 10);
			robot.getRightWheel().backward();
		}
	}

	public void speedUpLeft(int speedUp) {
		if (!reverse) {
			robot.getLeftWheel().setSpeed(robot.getLeftWheel().getSpeed() + speedUp);
			robot.getLeftWheel().forward();
		} else {
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
		} else {
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
		} else {
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
		} else {
			robot.getLeftWheel().startSynchronization();
			robot.getLeftWheel().forward();
			robot.getRightWheel().backward();
			robot.getLeftWheel().endSynchronization();
		}
	}

	public void turnOnPointLeft(int angle) {

		float dist = GlobalValues.DIST_PER_DEGREE * angle;
		int rotateAngle = (int) (dist * GlobalValues.DEGREE_TO_DIST);

		if (reverse) {
			robot.getLeftWheel().startSynchronization();
			robot.getLeftWheel().resetTachoCount();
			robot.getRightWheel().backward();
			robot.getLeftWheel().forward();
			robot.getLeftWheel().endSynchronization();
			while (robot.getLeftWheel().getTachoCount() < rotateAngle) {
			}
			//stopAll();
		} else {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().resetTachoCount();
			robot.getRightWheel().forward();
			robot.getLeftWheel().backward();
			robot.getLeftWheel().endSynchronization();
			while (robot.getRightWheel().getTachoCount() < rotateAngle) {
			}
			//stopAll();
		}
	}

	public void turnOnPointRight() {
		if (!reverse) {
			robot.getLeftWheel().startSynchronization();
			robot.getLeftWheel().forward();
			robot.getRightWheel().backward();
			robot.getLeftWheel().endSynchronization();
		} else {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().forward();
			robot.getLeftWheel().backward();
			robot.getLeftWheel().endSynchronization();
		}
	}

	public void turnOnPointRight(int angle) {

		float dist = GlobalValues.DIST_PER_DEGREE * angle;
		int rotateAngle = (int) (dist * GlobalValues.DEGREE_TO_DIST);
		
		if (!reverse) {
			robot.getLeftWheel().startSynchronization();
			robot.getLeftWheel().resetTachoCount();
			robot.getRightWheel().backward();
			robot.getLeftWheel().forward();
			robot.getLeftWheel().endSynchronization();
			while (robot.getLeftWheel().getTachoCount() < rotateAngle) {
			}
			stopAll();
		} else {
			robot.getLeftWheel().startSynchronization();
			robot.getRightWheel().resetTachoCount();
			robot.getRightWheel().forward();
			robot.getLeftWheel().backward();
			robot.getLeftWheel().endSynchronization();
			while (robot.getRightWheel().getTachoCount() < rotateAngle) {
			}
			stopAll();
		}
	}

	public void stopAll() {
		robot.getLeftWheel().startSynchronization();
		robot.getLeftWheel().flt();
		robot.getRightWheel().flt();
		robot.getLeftWheel().endSynchronization();
	}

	public void endSync() {
		robot.getLeftWheel().endSynchronization();
	}

	public void backwardDirection() {
		reverse = true;
		robot.setUltraSonicFront();
		;
	}

	public void forwardDirection() {
		reverse = false;
		robot.setUltraSonicBack();
	}
	
	public void updateWheelSpeeds(int angle) {
		if (!reverse) {
			return;
		} else {
			double percent = ((angle * 100.0) / 90.0) / 100.0;
			int speedChange = (int) Math.floor(GlobalValues.WALLFOLLOWSPEED * percent);
			robot.getLeftWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED + speedChange);
			robot.getRightWheel().setSpeed(GlobalValues.WALLFOLLOWSPEED - speedChange);
			robot.getLeftWheel().backward();
			robot.getRightWheel().backward();
		}
	}
}
