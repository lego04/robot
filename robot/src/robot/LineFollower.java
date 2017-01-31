package robot;

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

	public LineFollower(Robot robot) {
		this.robot = robot;
		detector = new LightDetectorAdaptor(robot.getColorSensor());
		detector.setLow(0);
		detector.setHigh(100);
		detector.setReflected(true);

	}

	public void followLine() {

	}

	public void update() {

	}
	
	private float getCurrentNormalizedLightValue() {
		float lv = detector.getNormalizedLightValue();
		System.out.println("norm light value: " + lv);
		return lv;
	}

	/**
	 * shows current light value whenever enter is pressed
	 */
	public void debug() {
		Button.ENTER.addKeyListener(new KeyListener() {

			// private DebugMessages debug = new DebugMessages();

			@Override
			public void keyReleased(Key k) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(Key k) {
				if (k.getId() == Button.ID_DOWN) {
					float light = detector.getNormalizedLightValue();
					System.out.println(light);
				}

			}
		});
	}
}
