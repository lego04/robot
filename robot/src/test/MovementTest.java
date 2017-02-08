package test;

import robot.Robot;
import util.EscapeThread;
import util.Movement;

public class MovementTest {
	
	public static void main(String args[]) throws InterruptedException {
		new EscapeThread().startThread();
		Robot robot = new Robot();
		Movement mov = robot.getMovement();
		System.out.println("Start Movement Test");
		
		mov.backwardDirection();
		mov.setSpeed(500);
		mov.goForward();
		
		/*
		mov.turnOnPointLeft(90);
		System.out.println("Turn Left");
		mov.stopAll();
		mov.turnOnPointRight(90);
		System.out.println("Turn Right");
		mov.stopAll();
		*/
		
		/*
		System.out.println("Move");
		mov.goForwardDist(50);
		System.out.println("Forward");
		mov.speedUpLeft(200);
		mov.goForwardDist(50);
		mov.slowDownLeft(200);
		mov.goBackward();
		System.out.println("End");
		*/
	}
	
}
