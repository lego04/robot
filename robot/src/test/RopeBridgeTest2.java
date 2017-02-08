package test;

import lejos.utility.Delay;
import robot.BridgeFollower;
import robot.Robot;
import robot.RopeBridgeWallFollower;
import sensorThreads.GyroSensorThread;
import sensorThreads.LightSensorThread;
import sensorThreads.UltrasonicSensorThread;
import sensorThreads.UltrasonicSensorThread.Modes;
import util.EscapeThread;
import util.GlobalValues;

public class RopeBridgeTest2 {
	
public static void main(String[] args) {
		
		new EscapeThread().startThread();
		Robot robot = new Robot();
		GyroSensorThread gst = robot.getThreadPool().getGyroSensorThread();
		gst.start();
		UltrasonicSensorThread ust = robot.getThreadPool().getUltraSonicSensorThread();
		ust.start(Modes.Left);
		LightSensorThread lst = robot.getThreadPool().getLightSensorThread();
		lst.startThread();
		
		int dist = ust.getDistance();
		Delay.msDelay(1000);
		dist = ust.getDistance();
		robot.getMovement().setSpeed(GlobalValues.WALLFOLLOWSPEED / 3);
		robot.getMovement().backwardDirection();
		robot.getMovement().goForwardDist(25);
		Delay.msDelay(500);
		int dist2 = ust.getDistance();
		int diff = (dist - dist2) + 3;
		System.out.println("Diff: " + (diff - 3));
		System.out.println("Diff: " + (diff - 3));
		System.out.println("Diff: " + (diff - 3));
		float flDiff = (float) diff / 6.5f;
		int angle = (int) Math.floor(flDiff);
		System.out.println("Angle: " + angle);
		System.out.println("Angle: " + angle);
		System.out.println("Angle: " + angle);
		System.out.println("Angle: " + angle);
		Delay.msDelay(5000);
		robot.getMovement().setSpeed(GlobalValues.WALLFOLLOWSPEED / 3);
	//	while (Math.abs(gst.getAngle()) < Math.abs(angle) - 1 || Math.abs(gst.getAngle()) > Math.abs(angle) + 1) {
		while (lst.getLastLightValue() < GlobalValues.AVG_LIGHT) {
			if (angle < 0) {
				if (gst.getAngle() + angle < 0) {
					System.out.println("Left: " + gst.getAngle());
					robot.getLeftWheel().setSpeed((GlobalValues.WALLFOLLOWSPEED / 3) + 15);
					robot.getMovement().goForward();
				}
				else {
					System.out.println("Right: " + gst.getAngle());
					robot.getLeftWheel().setSpeed((GlobalValues.WALLFOLLOWSPEED / 3) - 15);
					robot.getMovement().goForward();
				}
			}
			else {
				if (gst.getAngle() + angle > 0) {
					System.out.println("Left: " + gst.getAngle());
					robot.getLeftWheel().setSpeed((GlobalValues.WALLFOLLOWSPEED / 3) + 15);
					robot.getMovement().goForward();
				}
				else {
					System.out.println("Right: " + gst.getAngle());
					robot.getLeftWheel().setSpeed((GlobalValues.WALLFOLLOWSPEED / 3) - 15);
					robot.getMovement().goForward();
				}
			}
		}
		robot.getMovement().stopAll();
		robot.getMovement().setSpeed(GlobalValues.WALLFOLLOWSPEED / 3);
		robot.getMovement().turnOnPointLeft();
		while (gst.getAngle() + (angle + 22) > 0) {
		}
		robot.getMovement().stopAll();
		robot.getMovement().goForwardDist(15);
		
		while (lst.getLastLightValue() < GlobalValues.AVG_LIGHT) {
			if (gst.getAngle() + (angle + 22) < 0) {
				System.out.println("Left2: " + gst.getAngle());
				robot.getLeftWheel().setSpeed((GlobalValues.WALLFOLLOWSPEED / 3) + 15);
				robot.getMovement().goForward();
			}
			else {
				System.out.println("Right2: " + gst.getAngle());
				robot.getLeftWheel().setSpeed((GlobalValues.WALLFOLLOWSPEED / 3) - 15);
				robot.getMovement().goForward();
			}
		}
		
		robot.getMovement().stopAll();
		
//		robot.getMovement().setSpeed(GlobalValues.WALLFOLLOWSPEED / 3);
//		robot.getMovement().goForward();
		
	}
}
