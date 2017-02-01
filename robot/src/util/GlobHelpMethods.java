package util;

import lejos.robotics.LightDetectorAdaptor;

public class GlobHelpMethods {
	
	public static float getCurrentLightValue(LightDetectorAdaptor detector) {
		float lv = detector.getLightValue();
		System.out.println(lv);
		return lv;
	}
}
