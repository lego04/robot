package robot;

import lejos.robotics.geometry.Line;
import sensorThreads.LightSensorThread;
import util.GlobalValues;

public class FindLineFirst {

	private Robot robot;
	private LightSensorThread lst;
	//private boolean seesaw;
	
	public FindLineFirst(Robot robot) {
		this.robot = robot;
		lst = robot.getThreadPool().getLightSensorThread();
	}
	

	public void findLineFirst() {		// wird einmal zum Start aufgerufen
		//lst.startThread();
		robot.getMovement().backwardDirection();
		robot.getMovement().setSpeed(GlobalValues.LINETRAVELSPEED);
		robot.getMovement().turnOnPointRight(30);	// um 45 Grad nach rechts drehen
		robot.getMovement().goForward();
		while (lst.getLastLightValue() < GlobalValues.AVG_LIGHT) {
		}
		robot.getMovement().turnOnPointRight();
		while (lst.getLastLightValue() < GlobalValues.MAXLIGHT) {
		}
		robot.getMovement().forwardDirection();
		new LineFollower(robot).adjustLine();
	}
	
	public void findLineAfterBridge() {
		robot.getMovement().setSpeed(GlobalValues.LINETRAVELSPEED);
		robot.getMovement().turnOnPointRight(110);
		robot.getMovement().forwardDirection();
		robot.getMovement().goForward();
		while (lst.getLastLightValue() < GlobalValues.AVG_LIGHT) { }
		robot.getMovement().turnOnPointLeft(60);
		new LineFollower(robot).adjustLine();
	}
	
	
}
