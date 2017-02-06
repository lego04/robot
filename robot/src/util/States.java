package util;

import robot.BridgeFollower;
import robot.FindLineFirst;
import robot.Robot;
import robot.WallFollower;
import sensorThreads.UltrasonicSensorThread;

public class States {
	
	private Robot robot;
	private Station currentState;
	
	public States (Robot robot) {
		this.robot = robot;
		currentState = Station.START;
	}
	
	public void start() {
		//TODO: implement
	}
	
	public void nextState() {
		currentState = getNextState();
		performState();
	}
	
	private void performState() {
		
		
		
		switch (currentState) {
			case START:
// TODO: schneller?
				UltrasonicSensorThread ust = new UltrasonicSensorThread(robot);
				WallFollower wf = new WallFollower(robot, ust);
				wf.followTheWall();
				return;
			case WALLFOLLOWING:
				UltrasonicSensorThread ust2 = new UltrasonicSensorThread(robot);
				WallFollower wf2 = new WallFollower(robot, ust2);
				wf2.followTheWall();
				return;
			case LINEFOLLOWING_BEFORE_BRIDGE:
				FindLineFirst flf = new FindLineFirst(robot);
				flf.findLineFirst();
				return;
			case BRIDGE:
				BridgeFollower bf = new BridgeFollower(robot);
				bf.start();
				return;
			case LINEFOLLOWING_BEFORE_SEESAW:
				FindLineFirst flf2 = new FindLineFirst(robot);
				flf2.findLineFirst();
			case SEESAW:
// TODO: langsamer?
				FindLineFirst flf3 = new FindLineFirst(robot);
				flf3.findLineFirst();
			case LINEFOLLOWING_BEFORE_BOG:
				FindLineFirst flf4 = new FindLineFirst(robot);
				flf4.findLineFirst();
			case BOG:
// TODO: implement
				return;
			case WALLFOLLOWING_TO_ROPE_BRIDGE:
				UltrasonicSensorThread ust3 = new UltrasonicSensorThread(robot);
				WallFollower wf3 = new WallFollower(robot, ust3);
				wf3.followTheWall();
				return;
			case ROPE_BRIDGE:
				BridgeFollower bf2 = new BridgeFollower(robot);
				bf2.start();
				return;
			case BOSS:
// TODO: implement
				return;
			default: throw new IllegalStateException("unknown station");
		}
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
