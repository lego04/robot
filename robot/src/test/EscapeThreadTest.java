package test;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import robot.Robot;
import util.EscapeThread;

public class EscapeThreadTest {

	public static void main(String[] args) {
		
		EscapeThread escape = new EscapeThread();
		escape.startThread();
		Robot robot = new Robot();
		escape.setRobot(robot);
		
		LCD.drawString("robot is running...", 0, 0);
		
		//lässt das Programm laufen
		Button.DOWN.waitForPress();

	}

}
