package util;

import java.util.LinkedList;

import robot.BridgeFollower;
import robot.FindLineFirst;
import robot.Robot;
import robot.RopeBridgeWallFollower;
import robot.WallFollower;
import sensorThreads.ThreadPool;

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
				robot.getThreadPool().stopUltraSonic();
				break;
			case WALLFOLLOWING:
				WallFollower wf2 = new WallFollower(robot);
				wf2.followTheWall();
				robot.getThreadPool().stopUltraSonic();
				break;
			case LINEFOLLOWING_BEFORE_BRIDGE:
				FindLineFirst flf = new FindLineFirst(robot);
				flf.findLineFirst(false);
				//robot.getThreadPool().stopLightSensor();
				break;
			case BRIDGE:
// TODO: wenden
				robot.getMovement().turnOnPointLeft(180);
				BridgeFollower bf = new BridgeFollower(robot);
				bf.start();
				robot.getThreadPool().stopUltraSonic();
				break;
			case LINEFOLLOWING_BEFORE_SEESAW:
// TODO: wenden
				robot.getMovement().turnOnPointLeft(180);
				FindLineFirst flf2 = new FindLineFirst(robot);
				flf2.findLineFirst(false);
				//robot.getThreadPool().stopLightSensor();
				break;
			case SEESAW:
// TODO: langsamer?
				FindLineFirst flf3 = new FindLineFirst(robot);
				flf3.findLineFirst(true);
				//robot.getThreadPool().stopLightSensor();
				break;
			case LINEFOLLOWING_BEFORE_BOG:
				FindLineFirst flf4 = new FindLineFirst(robot);
				flf4.findLineFirst(false);
				//robot.getThreadPool().stopLightSensor();
				break;
			case BOG:
// TODO: wenden
// TODO: implement
				break;
			case WALLFOLLOWING_TO_ROPE_BRIDGE:
				WallFollower wf3 = new WallFollower(robot);
				wf3.followTheWall();
				robot.getThreadPool().stopUltraSonic();
				break;
			case ROPE_BRIDGE:
				RopeBridgeWallFollower rbfw = new RopeBridgeWallFollower(robot);
				rbfw.startFollowing();
				BridgeFollower bf2 = new BridgeFollower(robot);
				bf2.start();
				robot.getThreadPool().stopUltraSonic();
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
		new FindLineFirst(robot).findLineFirst(false);
	}
	
	private int getNextState() {
		if (indexOfcurrentState >= states.size()) {
			//if we reached the last station (e.g. BOSS) stay there forever
			return indexOfcurrentState;
		} else {
			return indexOfcurrentState + 1;
		}
	}
	
	public void stopAndReset() {
		robot.getMovement().stopAll();
		ThreadPool pool = robot.getThreadPool();
		pool.stopLightSensor();
		pool.stopUltraSonic();
		pool.stopLightSensor();
	}
	
	/**
	 * resets robot to given state. Robot will behave like it started from given staten and behaves as defined.
	 * @param state
	 */
	public void startFromState(Station state) {
		indexOfcurrentState = states.indexOf(state);
		performState();
	}
	
	public Station getCurrentState() {
		return states.get(indexOfcurrentState);
	}
	
	/**
	 * get station perceeding given state
	 * @param state
	 * @return
	 */
	public Station getPredecessorOf(Station state) {
		if (state.equals(Station.START)) {
			return state;
		}
		int index = states.indexOf(state);
		return states.get(index - 1);
	}
	
	/**
	 * get station following given state
	 * @param state
	 * @return
	 */
	public Station getSuccessorOf(Station state) {
		if (state.equals(Station.BOSS)) {
			return state;
		}
		int index = states.indexOf(state);
		return states.get(index + 1);
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
