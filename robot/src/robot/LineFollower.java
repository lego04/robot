package robot;

import java.time.*;
import util.globalValues;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.robotics.LightDetectorAdaptor;

public class LineFollower {

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
		while (getCurrentNormalizedLightValue() < globalValues.MINLIGHT
				// und die Stoßsensoren nicht aktiv sind
				) {
			robot.getPilot().stop();
		}
		//if (tastsensor.isActive()) {
			robot.getPilot().travel(-10);
			robot.getPilot().rotate(-90);
			robot.getPilot().forward();
			while (getCurrentNormalizedLightValue() < globalValues.MINLIGHT) {
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
			while (true) {		//CAREFULL!
				if (getCurrentNormalizedLightValue() < globalValues.MINLIGHT) {
					robot.getPilot().steer(-10);
					while (getCurrentNormalizedLightValue() < globalValues.MINLIGHT) {
						try {
							Thread.sleep(500);
						}
						catch (Exception e) {
						}
						System.out.println(getCurrentNormalizedLightValue());
					}
				}
				else if (getCurrentNormalizedLightValue() > globalValues.MAXLIGHT) {
					robot.getPilot().steer(10);
					while (getCurrentNormalizedLightValue() > globalValues.MAXLIGHT) {
						try {
							Thread.sleep(500);
						}
						catch (Exception e) {
						}
						System.out.println(getCurrentNormalizedLightValue());
					}
				}
				else {
					robot.getPilot().forward();
					while (globalValues.MINLIGHT < getCurrentNormalizedLightValue() &&
							getCurrentNormalizedLightValue() < globalValues.MAXLIGHT) {
						try {
							Thread.sleep(500);
						}
						catch (Exception e) {
						}
						System.out.println(getCurrentNormalizedLightValue());
					}
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
	
	private float getCurrentNormalizedLightValue() {
		float lv = detector.getLightValue();
		System.out.println("norm light value: " + lv);
		return lv;
	}

	/**
	 * shows current light value whenever enter is pressed
	 */
	public void debug() {
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
}
