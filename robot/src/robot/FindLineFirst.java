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
		
		detector = new LightDetectorAdaptor(robot.getColorSensor());
		detector.setLow(0);
		detector.setHigh(1);
		detector.setReflected(true);
		new TouchSensorListener(this);
		lst = new LightSensorThread(robot);
		lst.startThread();
	}
	
	public void findLineFirst() {		// wird einmal zum Start aufgerufen
		robot.getPilot().rotate(globalValues.RIGHT * 45);	// um 45 Grad nach rechts drehen
		robot.getPilot().forward();
		while (lst.getLastLightValue() < globalValues.MINLIGHT) {
		}
		robot.getPilot().rotate(globalValues.LEFT * 45);
		new LineFollower(robot, lst).adjustLine();
	}
	
	
	
	@Override
	public void act(TouchSensorID id) {
		robot.getPilot().stop();
		robot.getPilot().travel(-10);
		robot.getPilot().rotate(globalValues.LEFT * 90);
		robot.getPilot().forward();
		while (lst.getLastLightValue() < globalValues.MINLIGHT) {
		}
		robot.getPilot().stop();
	}

	@Override
	public Robot getRobot() {
		// TODO Auto-generated method stub
		return robot;
	}
	
}
