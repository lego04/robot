package robot;

import interfaces.Actor;
import lejos.robotics.LightDetectorAdaptor;
import listeners.TouchSensorListener;
import sensorThreads.LightSensorThread;
import util.*;

public class FindLineFirst implements Actor {

	private Robot robot;
	private LightDetectorAdaptor detector;
	private LightSensorThread lst;
	
	public FindLineFirst(Robot robot) {
		this.robot = robot;
		new TouchSensorListener(this);
		lst = new LightSensorThread(robot);
	}
	
	public void findLineFirst() {		// wird einmal zum Start aufgerufen
		lst.startThread();
		robot.getPilot().setTravelSpeed(GlobalValues.LINETRAVELSPEED);
		robot.getPilot().rotate(GlobalValues.RIGHT * 45);	// um 45 Grad nach rechts drehen
		robot.getPilot().forward();
		while (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
		}
		robot.getPilot().rotate(GlobalValues.LEFT * 45);
		new LineFollower(robot, lst).adjustLine();
	}
	
	
	
	@Override
	public void act(TouchSensorID id) {
		robot.getPilot().stop();
		robot.getPilot().travel(-10);
		robot.getPilot().rotate(GlobalValues.LEFT * 90);
		robot.getPilot().forward();
		while (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
		}
		robot.getPilot().travel(GlobalValues.TRAVEL_DIST_LABYRINTH / 2);
		new LineFollower(robot, lst).adjustLine();
	}

	@Override
	public Robot getRobot() {
		// TODO Auto-generated method stub
		return robot;
	}
	
}
