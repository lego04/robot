package robot;

import lejos.robotics.TouchAdapter;

public class Boss {
	
	private Robot robot;

	public Boss(Robot robot) {
		this.robot = robot;
		robot.getMovement().backwardDirection();
	}
	
	public void fightBossTillDeath() {
		TouchAdapter touch = new TouchAdapter(robot.getTouch1());
		WallFollower2 wf = new WallFollower2(robot);
		while (!touch.isPressed()) {
			wf.stayOnWall();
		} 
		robot.getMovement().stopAll();
		//bump with boss
		//step back and finish him
		robot.getMovement().goBackwardDist(15);
		robot.getMovement().setSpeed((int) robot.getLeftWheel().getMaxSpeed());
		robot.getMovement().goForward(); //destroy him
		while (!touch.isPressed()) {
			//do nothing
		}
		//TODO: it is dead, what now? WallFollowing?
		
	}
}
