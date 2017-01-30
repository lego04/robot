package robot;

import lejos.hardware.Button;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		robot.start();
		//System.out.println("Hello World");
		Button.waitForAnyPress();
	}

}
