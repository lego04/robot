package util;

import java.util.LinkedList;

import lejos.hardware.lcd.LCD;
import lejos.robotics.TouchAdapter;
import robot.Boss;
import robot.BridgeFollower;
import robot.FindLineFirst;
import robot.Robot;
import robot.RopeBridgeWallFollower;
import robot.WallFollower;
import robot.WallFollower2;
import sensorThreads.LightSensorThread;
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
		if (robot.isInterrupted().get()) {
			robot.getMovement().stopAll(); //stop when interrupted
			return;
		}
		indexOfcurrentState = getNextState();
		performState();
	}
	
	private void performState() {
		
		Station currentState = states.get(indexOfcurrentState);
		
		switch (currentState) {
			case START:
// TODO: schneller?
				debug("start");
				robot.getMovement().setSpeed(GlobalValues.WALLFOLLOWSPEED * 2);
				WallFollower2 wf = new WallFollower2(robot);
				wf.startFollowing();
				robot.getThreadPool().stopUltraSonic();
				//robot.getMovement().stopAll();
				break;
			case WALLFOLLOWING:
				debug("wallfollowing");
				WallFollower2 wf2 = new WallFollower2(robot);
				wf2.startFollowing();
				robot.getThreadPool().stopUltraSonic();
				break;
			case LINEFOLLOWING_BEFORE_BRIDGE:
				debug("linefollowing before brige");
				robot.getThreadPool().getGyroSensorThread().start();
				FindLineFirst flf = new FindLineFirst(robot);
				flf.findLineFirst();
				robot.getThreadPool().getGyroSensorThread().stop();
				//robot.getThreadPool().stopLightSensor();
				turn180();
				robot.getMovement().backwardDirection();
				robot.getMovement().goForward();
				
				LightSensorThread lst = robot.getThreadPool().getLightSensorThread();
				while (!lst.nextStateReady()) {
					//do nothing
				}
				robot.getMovement().stopAll();
				robot.getMovement().goForwardDist(GlobalValues.TRAVEL_DIST_LABYRINTH);
				break;
			case BRIDGE:
// TODO: wenden
				debug("bridge");
				robot.getThreadPool().getUltraSonicSensorThread().rotateSensor(90);
				//robot.getMovement().turnOnPointLeft(180);
				BridgeFollower bf = new BridgeFollower(robot);
				bf.start();
				robot.getThreadPool().stopUltraSonic();
				break;
			case LINEFOLLOWING_BEFORE_SEESAW:
// TODO: wenden
				debug("linefollowiing before seesaw");
				robot.getThreadPool().getGyroSensorThread().start();
				robot.getMovement().turnOnPointLeft(180);
				FindLineFirst flf2 = new FindLineFirst(robot);
				flf2.findLineAfterBridge();
				robot.getThreadPool().getGyroSensorThread().stop();
				//robot.getThreadPool().stopLightSensor();
				break;
			case SEESAW:
// TODO: langsamer?
				debug("seesaw");
				FindLineFirst flf3 = new FindLineFirst(robot);
				flf3.findStraightLine(true);
				//robot.getThreadPool().stopLightSensor();
				break;
			case LINEFOLLOWING_BEFORE_BOG:
				debug("linefollowing before bog");
				robot.getThreadPool().getGyroSensorThread().start();
				FindLineFirst flf4 = new FindLineFirst(robot);
				flf4.findStraightLine(false);
				robot.getThreadPool().getGyroSensorThread().stop();
				//robot.getThreadPool().stopLightSensor();
				break;
			case BOG:
// TODO: wenden
				robot.getMovement().turnOnPointRight(180);
				robot.getMovement().backwardDirection();
				TouchAdapter ta = new TouchAdapter(robot.getTouch1());
				robot.getMovement().setSpeed(GlobalValues.WALLFOLLOWSPEED * 2);
				robot.getMovement().goForward();
				while (!ta.isPressed() && !robot.isInterrupted().get()) {
					//do nothing
				}
				robot.getMovement().stopAll();
				robot.getMovement().turnOnPointLeft(90);
				debug("bug");
				break;
			case WALLFOLLOWING_TO_ROPE_BRIDGE:
				/*debug("wallfollowing to rope bridge");
				WallFollower wf3 = new WallFollower(robot);
				wf3.followTheWall();
				robot.getThreadPool().stopUltraSonic();
				break;*/
			case ROPE_BRIDGE:
				debug("rope bridge");
				RopeBridgeWallFollower rbfw = new RopeBridgeWallFollower(robot);
				rbfw.startFollowing();
				BridgeFollower bf2 = new BridgeFollower(robot);
				bf2.start();
				robot.getThreadPool().stopUltraSonic();
				break;
			case BOSS:
				debug("boss");
				Boss boss = new Boss(robot);
				boss.fightBossTillDeath();
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
	
	private void turn180() {
		robot.getMovement().turnOnPointRight(180);
	}
	
	public void stopAndReset() {
		robot.interrupt();
		ThreadPool pool = robot.getThreadPool();
		//pool.stopLightSensor();
		pool.stopUltraSonic();
		pool.stopGyro();
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
	
	private void debug(String msg) {
		LCD.drawString("currentState: " + msg, 0, 7);
	}
}
