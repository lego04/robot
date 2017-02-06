package util;

import robot.BridgeFollower;
import robot.FindLineFirst;
import robot.Robot;
import robot.WallFollower;

public class States {
	
	private Robot robot;
	private Station currentState;
	
	public States (Robot robot) {
		this.robot = robot;
		currentState = Station.START;
	}
	
	public void start() {
		performState();
	}
	
	public void nextState() {
		currentState = getNextState();
		performState();
	}
	
	private void performState() {
		
		
		
		switch (currentState) {
			case START:
// TODO: schneller?
				WallFollower wf = new WallFollower(robot);
				wf.followTheWall();
				break;
			case WALLFOLLOWING:
				WallFollower wf2 = new WallFollower(robot);
				wf2.followTheWall();
				break;
			case LINEFOLLOWING_BEFORE_BRIDGE:
				FindLineFirst flf = new FindLineFirst(robot);
				flf.findLineFirst();
				break;
			case BRIDGE:
// TODO: wenden
				BridgeFollower bf = new BridgeFollower(robot);
				bf.start();
				break;
			case LINEFOLLOWING_BEFORE_SEESAW:
// TODO: wenden
				FindLineFirst flf2 = new FindLineFirst(robot);
				flf2.findLineFirst();
				break;
			case SEESAW:
// TODO: langsamer?
				FindLineFirst flf3 = new FindLineFirst(robot);
				flf3.findLineFirst();
				break;
			case LINEFOLLOWING_BEFORE_BOG:
				FindLineFirst flf4 = new FindLineFirst(robot);
				flf4.findLineFirst();
				break;
			case BOG:
// TODO: wenden
// TODO: implement
				break;
			case WALLFOLLOWING_TO_ROPE_BRIDGE:
				WallFollower wf3 = new WallFollower(robot);
				wf3.followTheWall();
				break;
			case ROPE_BRIDGE:
				BridgeFollower bf2 = new BridgeFollower(robot);
				bf2.start();
				break;
			case BOSS:
// TODO: wenden?
// TODO: implement
				break;
			default: throw new IllegalStateException("unknown station");
		}
		nextState();
	}
	
	public void startLineFindAndFollow() {
		new FindLineFirst(robot).findLineFirst();
	}
	
	private Station getNextState() {
		switch (currentState) {
		case START:
			return Station.WALLFOLLOWING;
		case WALLFOLLOWING:
			return Station.LINEFOLLOWING_BEFORE_BRIDGE;
		case LINEFOLLOWING_BEFORE_BRIDGE:
			return Station.BRIDGE;
		case BRIDGE:
			return Station.LINEFOLLOWING_BEFORE_SEESAW;
		case LINEFOLLOWING_BEFORE_SEESAW:
			return Station.SEESAW;
		case SEESAW:
			return Station.LINEFOLLOWING_BEFORE_BOG;
		case LINEFOLLOWING_BEFORE_BOG:
			return Station.BOG;
		case BOG:
			return Station.WALLFOLLOWING_TO_ROPE_BRIDGE;
		case WALLFOLLOWING_TO_ROPE_BRIDGE:
			return Station.ROPE_BRIDGE;
		case ROPE_BRIDGE:
			return Station.BOSS;
		case BOSS:
			return Station.BOSS; //stay in Boss forever
		default: throw new IllegalStateException("unknown station");
		}
	}
}
