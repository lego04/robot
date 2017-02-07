package util;

import java.util.LinkedList;

import robot.BridgeFollower;
import robot.FindLineFirst;
import robot.Robot;
import robot.WallFollower;

public class States {
	
	private Robot robot;
	private int indexOfcurrentState;
	private LinkedList<Station> states;
	
	public States (Robot robot) {
		this.robot = robot;
		indexOfcurrentState = 0; //should be START
		states = new LinkedList<>();
		initializeStates();
	}
	
	public void start() {
		performState();
	}
	
	public void nextState() {
		indexOfcurrentState = getNextState();
		performState();
	}
	
	private void performState() {
		
		Station currentState = states.get(indexOfcurrentState);
		
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
	
	private int getNextState() {
		if (indexOfcurrentState >= states.size()) {
			//if we reached the last station (e.g. BOSS) stay there forever
			return indexOfcurrentState;
		} else {
			return indexOfcurrentState + 1;
		}
	}
	
	/**
	 * defines sequence of stations
	 */
	private void initializeStates() {
		states.add(Station.START);
		states.add(Station.WALLFOLLOWING);
		states.add(Station.LINEFOLLOWING_BEFORE_BRIDGE);
		states.add(Station.BRIDGE);
		states.add(Station.LINEFOLLOWING_BEFORE_SEESAW);
		states.add(Station.SEESAW);
		states.add(Station.LINEFOLLOWING_BEFORE_BOG);
		states.add(Station.BOG);
		states.add(Station.WALLFOLLOWING_TO_ROPE_BRIDGE);
		states.add(Station.ROPE_BRIDGE);
		states.add(Station.BOSS);
	}
}
