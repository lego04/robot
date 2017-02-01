package robot;

import java.time.*;

import interfaces.Actor;
import util.TouchSensorID;
import util.globalValues;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.robotics.LightDetectorAdaptor;
import listeners.TouchSensorListener;
import sensorThreads.LightSensorThread;

public class LineFollower implements Actor {

	/**
	 * pointer to robot instance
	 */
	private Robot robot;

	/**
	 * interface for color / light sensor
	 */
	
	private LightSensorThread lst;

	public LineFollower(Robot robot, LightSensorThread lst) {
		this.robot = robot;
		this.lst = lst;
		
		//should be handled by LightSensorThread, remove this lines if it works
		/*
		detector = new LightDetectorAdaptor(robot.getColorSensor());
		detector.setLow(0);
		detector.setHigh(1);
		detector.setReflected(true);
		*/

	}
	
	//now in FindFirstLine Class
	
	/*
	public void findLineFirst() {		//wird einmal zum Start aufgerufen
		robot.getPilot().rotate(45);	// um 45 Grad nach rechts drehen
		robot.getPilot().forward();
		while (getCurrentLightValue() < globalValues.MINLIGHT
				) {
			robot.getPilot().stop();
		}
		//if (tastsensor.isActive()) {
			robot.getPilot().travel(-10);
			robot.getPilot().rotate(-90);
			robot.getPilot().forward();
			while (getCurrentLightValue() < globalValues.MINLIGHT) {
			}
			robot.getPilot().stop();
			leftEdge = false;
		//}
		//else {
			leftEdge = true;
			robot.getPilot().rotate(-45);
		//}
		adjustLine(leftEdge);
	}
	*/
	
	public void followLine() {
		
	}
	
	public void adjustLine() {
		
		for (int i = 0; i < 20; i++) {		//for testing purpose
			if (lst.getLastLightValue() < globalValues.MINLIGHT) {
				robot.getPilot().steer(globalValues.RIGHT * 45);
				while (lst.getLastLightValue() < globalValues.MINLIGHT) {
					/*
					try {
						Thread.sleep(100);
					}
					catch (Exception e) {
					}
					System.out.println("Right: " + lst.getLastLightValue());
					*/
				}
				//robot.getPilot().stop();
			}
			else if (lst.getLastLightValue() > globalValues.MAXLIGHT) {
				robot.getPilot().steer(globalValues.LEFT * 45);
				while (lst.getLastLightValue() > globalValues.MAXLIGHT) {
					/*
					try {
						Thread.sleep(100);
					}
					catch (Exception e) {
					}
					System.out.println("Left: " + lst.getLastLightValue());
					*/
				}
				//robot.getPilot().stop();
			}
			else {
				robot.getPilot().forward();
				while (globalValues.MINLIGHT < lst.getLastLightValue() &&
						lst.getLastLightValue() < globalValues.MAXLIGHT) {
					/*
					try {
						Thread.sleep(100);
					}
					catch (Exception e) {
					}
					System.out.println("Go: " + lst.getLastLightValue());
					*/
				}
				//robot.getPilot().stop();
			}
		}
	
	}
	
	/*
	public void lineHandle(float lightValue) {
		if (lightValue < 0.1) {		//Pseudo Werte, TODO
			//findLine();
		}
		else if (lightValue > 0.3) {	//Pseudo Werte, TODO
			adjustLine();
		}
		else {
			followLine();
		}
	}
	*/
	
	// now in own Thread 
	/*
	private float getCurrentLightValue() {
		float lv = lightSensorThread.getLastLightValue();
		System.out.println(lv);
		return lv;
	}
	*/

	/**
	 * shows current light value whenever enter is pressed
	 */
	public void debug() {
		robot.getPilot().forward();
		Button.ENTER.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(Key k) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(Key k) {
				if (k.getId() == Button.ID_ENTER) {
					float light = lst.getDetector().getLightValue();
					System.out.println(light);
				}

			}
		});
	}

	@Override
	public void act(TouchSensorID id) {
		robot.getPilot().stop();
		
	}

	@Override
	public Robot getRobot() {
		return robot;
	}
}
