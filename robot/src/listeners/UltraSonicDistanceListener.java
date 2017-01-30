package listeners;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;

public class UltraSonicDistanceListener implements FeatureListener {
	
	private DifferentialPilot pilot;
	private double limit;
	
	/**
	 * 
	 * @param pilot pilot controlling the robot
	 * @param limit robot will stop if there is an object with distance less than limit in front of him
	 */
	public UltraSonicDistanceListener(DifferentialPilot pilot, double limit) {
		this.pilot = pilot;
		this.limit = limit;
	}

	@Override
	public void featureDetected(Feature feature, FeatureDetector detector) {
		if (feature.getRangeReading().getRange() < limit) {
			pilot.stop();
		}
	}

}
