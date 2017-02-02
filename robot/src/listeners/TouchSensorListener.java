package listeners;

import interfaces.Actor;
import lejos.robotics.TouchAdapter;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.TouchFeatureDetector;
import robot.Robot;
import util.TouchSensorID;

public class TouchSensorListener implements FeatureListener {
	
	private Actor actor;
	
	private TouchFeatureDetector touch1Detector;
//	private TouchFeatureDetector touch2Detector;

	public TouchSensorListener(Actor a) {
		this.actor = a;
		Robot robot = a.getRobot();
		touch1Detector = new TouchFeatureDetector(new TouchAdapter(robot.getTouch1()));
//		touch2Detector = new TouchFeatureDetector(new TouchAdapter(robot.getTouch2()));
		touch1Detector.addListener(this);
//		touch2Detector.addListener(this);
	}

	@Override
	public void featureDetected(Feature feature, FeatureDetector detector) {
		if (detector.equals(touch1Detector)) {
			//check whether touch2 is also pressed
//			if (touch2Detector.scan() != null) {
//				actor.act(TouchSensorID.BOTH);
//			} else {
				actor.act(TouchSensorID.ONE);
//			}
		}
//		else {
//			//check whether touch1 is pressed
//			if (touch1Detector.scan() != null) {
//				actor.act(TouchSensorID.BOTH);
//			} else {
//				actor.act(TouchSensorID.TWO);
//			}
//		}
	}
}
