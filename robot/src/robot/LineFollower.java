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

public class LineFollower implements Actor {

	/**
	 * pointer to robot instance
	 */
	private Robot robot;

	/**
	 * interface for color / light sensor
	 */
	private LightDetectorAdaptor detector;
	private boolean leftEdge;

	public LineFollower(Robot robot) {
		this.robot = robot;
		
		detector = new LightDetectorAdaptor(robot.getColorSensor());
		detector.setLow(0);
		detector.setHigh(1);
		detector.setReflected(true);

	}
	
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
	
	public void followLine() {
		
	}
	
	public void adjustLine(boolean leftEdge) {
		if (leftEdge) {
			for (int i = 0; i < 20; i++) {		//for testing purpose
				if (getCurrentLightValue() < globalValues.MINLIGHT) {
					robot.getPilot().steer(globalValues.RIGHT * 15);
					while (getCurrentLightValue() < globalValues.MINLIGHT) {
						try {
							Thread.sleep(200);
						}
						catch (Exception e) {
						}
						System.out.println("Right: " + getCurrentLightValue());
					}
					//robot.getPilot().stop();
				}
				else if (getCurrentLightValue() > globalValues.MAXLIGHT) {
					robot.getPilot().steer(globalValues.LEFT * 15);
					while (getCurrentLightValue() > globalValues.MAXLIGHT) {
						try {
							Thread.sleep(100);
						}
						catch (Exception e) {
						}
						System.out.println("Left: " + getCurrentLightValue());
					}
					//robot.getPilot().stop();
				}
				else {
					robot.getPilot().forward();
					while (globalValues.MINLIGHT < getCurrentLightValue() &&
							getCurrentLightValue() < globalValues.MAXLIGHT) {
						try {
							Thread.sleep(200);
						}
						catch (Exception e) {
						}
						System.out.println("Go: " + getCurrentLightValue());
					}
					//robot.getPilot().stop();
				}
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
	
	private float getCurrentLightValue() {
		float lv = detector.getLightValue();
		System.out.println(lv);
		return lv;
	}

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
					float light = detector.getLightValue();
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
