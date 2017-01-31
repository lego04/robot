package test;

import interfaces.Actor;
import lejos.hardware.Button;
import listeners.TouchSensorListener;
import robot.Robot;
import util.TouchSensorID;

public class TouchSensorTest {

	public static void main(String[] args) {
		final Robot robot = new Robot();
		System.out.println("Waiting for touch sensor...");
		new TouchSensorListener(new Actor() {
			
			@Override
			public Robot getRobot() {
				return robot;
			}
			
			@Override
			public void act(TouchSensorID id) {
				switch (id) {
				case ONE:
					System.out.println("Sensor 1 pressed");
					break;
				case TWO:
					System.out.println("Sensor 2 pressed");
					break;
				case BOTH:
					System.out.println("Both sensors pressed");
				}
			}
		});
		
		Button.ESCAPE.waitForPress();
	}

}
