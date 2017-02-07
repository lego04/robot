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
	
	public void findLineFirst(boolean seesaw) {		// wird einmal zum Start aufgerufen
		//lst.startThread();
		robot.getMovement().setSpeed(GlobalValues.LINETRAVELSPEED);
		robot.getMovement().turnOnPointLeft();	// um 45 Grad nach rechts drehen
		robot.getPilot().forward();
		while (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
		}
		robot.getPilot().rotate(GlobalValues.LEFT * 45);
		new LineFollower(robot, lst).adjustLine();
	}
	
	
}
