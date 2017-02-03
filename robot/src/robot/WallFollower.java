package robot;

import robot.Robot;
import sensorThreads.UltrasonicSensorThread;
import util.GlobalValues;
import util.Movement;
import util.TouchSensorID;

/** WallFollwer class is the generalisation of the Left- and RightWallFollower
 * @author Rashad Asgarbayli
 */
public class WallFollower implements interfaces.Actor {
	/** Reference to {@link Robot} */
	private Robot robot;
	/** Reference to {@link UltrasonicSensorThread} */
	private UltrasonicSensorThread distanceSensor;
	/** Current distance to the wall as <b>centimetres (cm)</b>, that read from {@link UltrasonicSensorThread}. */
	private int distanceToWall;
	
	private Movement movement;
	
	private TurnState currentState;
	
	private final float wallToFollow;
	
	/** Standard constructor of the calls. Needs reference to the {@link Robot} and {@link UltrasonicSensorThread}
	 * @param robot : {@link Robot}
	 * @param sensor : {@link UltrasonicSensorThread}
	 */

	public WallFollower(Robot robot, UltrasonicSensorThread sensor, float wallToFollow) {

		this.robot = robot;
		this.distanceSensor = sensor;
		this.distanceToWall = 22; // Just to be sure, that it was initialised.
		updateDistanceToWall();
		this.wallToFollow = wallToFollow;
		this.movement = new Movement(robot, GlobalValues.WALLFOLLOWSPEED);
		currentState = TurnState.STRAIGHT;
	}
	
	/** Robot follows the wall using it as an anchor point to find its way through the labyrinth.
	 * Robot stays in this state, until it decides, that it is out of the labyrinth.
	 */
	public void followTheWall() {
		//robot.getPilot().setTravelSpeed(GlobalValues.WALLFOLLOWSPEED);
		movement.backwardDirection();
		movement.goForwardSpeed(GlobalValues.WALLFOLLOWSPEED);
		robot.getPilot().setTravelSpeed(GlobalValues.WALLFOLLOWSPEED);
		while (isInLabyrinth()) {
			//controllTheDistanceToWall();
			//robot.getPilot().forward();
			//updateDistanceToWall();
			/*while (distanceToWall < GlobalValues.WALL_DIST_MAX && distanceToWall > GlobalValues.WALL_DIST_MIN) {
				updateDistanceToWall();
				waitComplete(200);
			}*/
			updateMovement();
			waitComplete(200);
			//movement.stopAll();
			//controllTheDistanceToWall();
			//waitComplete(500);
			//robot.getPilot().travel(10.0);
			//waitComplete(500);
		}
	}
	
	
	private void updateMovement() {
		TurnState state = getState();
		if (state != currentState) {
			switch (state) {
			case LEFT:
				movement.speedUpLeft();
				break;
			case RIGHT:
				movement.slowDownLeft();
				break;
			case STRAIGHT:	//Set the two speeds at one value to drive straight
				robot.getLeftWheel().setSpeed(robot.getRightWheel().getSpeed());
				break;
			case LEFT_TURN:
				turnLeft();
				state = TurnState.STRAIGHT;
				//System.out.println("left turn");
				break;
			default:
				throw new IllegalStateException("unknwon state");
			}
		}
		currentState = state;
	}
	
	private TurnState getState() {
		updateDistanceToWall();
		if (distanceToWall < GlobalValues.WALL_DIST_MIN) {
			System.out.println("LEFT");
			return TurnState.LEFT;
		} else if (distanceToWall > GlobalValues.WALL_DIST_MIN) {
			if (distanceToWall > GlobalValues.WALL_DIST_MAX) {
				System.out.println("LEFT_TURN");
				return TurnState.LEFT_TURN;
			} else {
				System.out.println("RIGHT");
				return TurnState.RIGHT;
			}
		} else {
			System.out.println("STRAIGHT");
			return TurnState.STRAIGHT;
		}
	}
	
	/** Decides, if the robot still in the labyrinth or not.
	 * @return <code>true</code>, if the robot still in the labyrinth, else <code>false</code>.
	 */
	private boolean isInLabyrinth() {
		// TODO: Decide to change state, if the robot out of the maze.
		return true;
	}
	
	private void turnLeft() {
		movement.stopAll();
		//synchronizeMotors(state);
		//robot.getPilot().travel(GlobalValues.TRAVEL_DIST_LABYRINTH);
		//movement.turnOnPointLeft();
		//robot.getPilot().rotate(GlobalValues.LEFT * 90);
		//robot.getPilot().travel(GlobalValues.TRAVEL_DIST_LABYRINTH);
		
		movement.goForwardDist(GlobalValues.TRAVEL_DIST_LABYRINTH);
		movement.turnOnPointLeft((int)(GlobalValues.LEFT) * 90);
		movement.goForwardDist(GlobalValues.TRAVEL_DIST_LABYRINTH);
		
		//evtl. mit robot.getPilot().travelArc() umsetzen, würde dann eine Kurve fahren
		
	}
	
	private void turnRight() {
		movement.stopAll();
		//synchronizeMotors(currentState);
		movement.goBackwardDist(5);
		movement.turnOnPointRight((int) (GlobalValues.RIGHT) * 90);
	}
	
	private void synchronizeMotors(TurnState state) {
		//reset left motor speed if necessary
				switch (state) {
				case LEFT:
					movement.slowDownLeft();
					break;
				case RIGHT:
					movement.speedUpLeft();
					break;
				case STRAIGHT: 
					//motors are already synchronized
					break;
				case LEFT_TURN:		
				default: 
					throw new IllegalStateException("invalid state: " + state);
				}			
				
	}
	

	
	/** Controller, that tries to keep the robot at the wall. */
	/*private void controllTheDistanceToWall() {
		updateDistanceToWall();
		if (distanceToWall > 40) {
			robot.getPilot().stop();
			robot.getPilot().travel(GlobalValues.TRAVEL_DIST_LABYRINTH);
			// turn left by 90 degree with only right Wheel moving
			robot.getPilot().steer(GlobalValues.LEFT * 100, 90);		
			robot.getPilot().travel(GlobalValues.TRAVEL_DIST_LABYRINTH);
			return;
		}
		//supdateDistanceToWall();
		int diff = GlobalValues.WALL_MID - distanceToWall;
		System.out.println("diff: " + diff);
		double turnRate = (double)  wallToFollow * distanceToTurnRate(diff);
		System.out.println("turnRate: " + turnRate);
		robot.getPilot().steer(turnRate);
		
	}
	*/
	
	// ist ein Einzeiler, keine eigene Methode n�tig
	/*
	private void turnLeft() {
		robot.getPilot().rotate(globalValues.LEFT * 90); 
	}
	*/
	
	
	/** Converts distance values read from {@link UltrasonicSensorThread} to 
	 * the <code>turnRate</code> values needed for <code>pilot.steer()</code> method.
	 * @param distance : <code>float</code>, value read from {@link UltrasonicSensor}.
	 * @return <code>double</code> value for <code>turnRate</code>.
	 */
	private double distanceToTurnRate(int distance) {
		// FIXME; Factor must be set right. 1.0 is wrong.
		return Math.min(200.0, Math.max(-200.0, 1.0 * distance));
	}
	
	/** Updates the <code>distanceToWall</code> - distance between the wall and the robot */
 	private void updateDistanceToWall() {
		this.distanceToWall = distanceSensor.getDistance();
	}

	@Override
	public void act(TouchSensorID id) {
		turnRight();
		followTheWall();
	}
	
	/** Stops the execution for the given time. 
	 * @param millis : <code>long</code>, is the milliseconds for the thread to sleep.
	 */
	private void waitComplete(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Robot getRobot() {
		return robot;
	}
	
	public enum TurnState {
		
		LEFT,
		RIGHT,
		STRAIGHT,
		LEFT_TURN;
	}
}
