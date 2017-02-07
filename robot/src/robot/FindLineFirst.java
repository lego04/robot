package robot;

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
		robot.getMovement().setSpeed(GlobalValues.LINETRAVELSPEED);
		robot.getMovement().turnOnPointRight(30);	// um 45 Grad nach rechts drehen
		robot.getMovement().goForward();
		while (lst.getLastLightValue() < GlobalValues.AVG_LIGHT) {
		}
		robot.getPilot().rotate(GlobalValues.LEFT * 45);
		new LineFollower(robot).adjustLine();
	}
	
	public void findStraightLine() {
		robot.getMovement().setSpeed(GlobalValues.LINETRAVELSPEED);
		robot.getMovement().goForwardDist(15);
		
		robot.getLeftWheel().getTachoCount();
		robot.getMovement().turnOnPointLeft();
	}
	
	
}
