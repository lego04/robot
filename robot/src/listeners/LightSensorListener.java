package listeners;

import robot.LineFollower;

public class LightSensorListener implements Runnable {
	
	private LineFollower follower;
	
	public LightSensorListener(LineFollower follower) {
		this.follower = follower;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
