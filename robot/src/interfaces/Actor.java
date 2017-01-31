package interfaces;

import robot.Robot;
import util.TouchSensorID;

public interface Actor {

	public void act(TouchSensorID id);
	
	public Robot getRobot();
}
