package robot;

import java.time.*;

import javax.xml.bind.JAXBElement.GlobalScope;

import interfaces.Actor;
import util.GlobHelpMethods;
import util.TouchSensorID;
import util.GlobalValues;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
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
	
	public void findLine() {
		int count = 0;
		robot.getPilot().stop();
		robot.getPilot().rotate(GlobalValues.RIGHT * 65);
		
		while (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
			/*
			robot.getPilot().forward();	
			while (lst.getLastLightValue() < globalValues.MINLIGHT) {
				try {
					Thread.sleep(50);
				} catch (Exception e) {}
				count++;
				if (count > globalValues.LINETRAVELSPEED * 2) {
					robot.getPilot().rotate(globalValues.RIGHT * 20);
					count = 0;
				}
			}
			*/
		}
		robot.getPilot().forward();
		try {
			Thread.sleep(400);
		}
		catch (Exception e) { }
		robot.getPilot().steer(GlobalValues.LEFT * 200);
		while (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
		}
		robot.getPilot().stop();
	}
	
	public void adjustLine() {
		while (true) {		//for testing purpose
			robot.getMovement().goForwardSpeed(GlobalValues.LINETRAVELSPEED * 10);
			if (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
				//robot.getPilot().steer(globalValues.RIGHT * 30);
				//robot.getMovement().stopAll();
				//robot.getMovement().goForwardSpeed(GlobalValues.LINETRAVELSPEED * 10);
				robot.getRightWheel().stop();
				while (lst.getLastLightValue() < GlobalValues.MINLIGHT) {
					/*
					try {
						Thread.sleep(50);
					}
					catch (Exception e) {
					}
					System.out.println("Right: " + lst.getLastLightValue());
					count++;
					robot.getMovement().slowDownRight();
					if (count > globalValues.LINETRAVELSPEED) {
						//findLine();
						
						count = 0;
					}
					*/
					
					
				}
				LCD.drawString("Debug01", 0, 0);
				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 10);
				robot.getRightWheel().forward();
				//robot.getPilot().stop();
			}
			else if (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
				//robot.getLeftWheel().setSpeed(globalValues.LINETRAVELSPEED * 5);
				//robot.getRightWheel().setSpeed(globalValues.LINETRAVELSPEED * 20);
				robot.getMovement().stopAll();
				LCD.drawString("Debug02", 0, 0);
				
				/*
				try {
					Thread.sleep(300);
				}
				catch (Exception e) { }
				*/
				/*
				robot.getLeftWheel().setSpeed(150);
				robot.getRightWheel().setSpeed(100);
				robot.getLeftWheel().forward();
				robot.getRightWheel().forward();
				*/
				robot.getLeftWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 6);
				robot.getRightWheel().setSpeed(GlobalValues.LINETRAVELSPEED * 6);
				robot.getMovement().turnOnPointLeft();
				
				while (lst.getLastLightValue() > GlobalValues.MAXLIGHT) {
					LCD.drawString("Left: " + lst.getLastLightValue(), 0, 20);
					/*
					try {
						Thread.sleep(100);
					}
					catch (Exception e) {
					}
					*/
				}
				//robot.getMovement().stopAll();
//				robot.getMovement().goForwardSpeed(GlobalValues.LINETRAVELSPEED * 10);
			
				//robot.getPilot().stop();
			}
			else {
				robot.getMovement().stopAll();
				robot.getMovement().goForwardSpeed(GlobalValues.LINETRAVELSPEED * 10);
				while (GlobalValues.MINLIGHT < lst.getLastLightValue() &&
						lst.getLastLightValue() < GlobalValues.MAXLIGHT) {
					
					LCD.drawString("Go: " + lst.getLastLightValue(), 0, 0);
				}
				robot.getMovement().stopAll();
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
