package robot;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.robotics.LightDetectorAdaptor;
import lejos.utility.DebugMessages;

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
		
	}
	
	public void followLine() {
		
	}
	
	/**
	 * shows current light value whenever enter is pressed
	 */
	public void debug() {
Button.DOWN.addKeyListener(new KeyListener() {
			
			private DebugMessages debug = new DebugMessages();
			
			@Override
			public void keyReleased(Key k) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(Key k) {
				if (k.getId() == Button.ID_DOWN) {
					float light = detector.getLightValue();
					debug.echo((int) light); 
				}
				
			}
		});
	}
}
