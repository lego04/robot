package util;

import robot.FindLineFirst;
import robot.Robot;

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
		//TODO: call methods
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
