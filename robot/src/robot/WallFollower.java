package robot;

import robot.Robot;
import sensorThreads.UltrasonicSensorThread;
import util.GlobalValues;
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
	}
	
	/** Robot follows the wall using it as an anchor point to find its way through the labyrinth.
	 * Robot stays in this state, until it decides, that it is out of the labyrinth.
	 */
	public void followTheWall() {
		while (isInLabyrinth()) {
			controllTheDistanceToWall();
			robot.getPilot().forward();
			updateDistanceToWall();
			while (distanceToWall < GlobalValues.WALL_DIST_MAX && distanceToWall > GlobalValues.WALL_DIST_MIN) {
				updateDistanceToWall();
				waitComplete(200);
			}
			robot.getPilot().stop();
			controllTheDistanceToWall();
			//waitComplete(500);
			//robot.getPilot().travel(10.0);
			//waitComplete(500);
		}
	}
	
	/** Decides, if the robot still in the labyrinth or not.
	 * @return <code>true</code>, if the robot still in the labyrinth, else <code>false</code>.
	 */
	private boolean isInLabyrinth() {
		// TODO: Decide to change state, if the robot out of the maze.
		return true;
	}
	

	
	/** Controller, that tries to keep the robot at the wall. */
	private void controllTheDistanceToWall() {
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
	
	// ist ein Einzeiler, keine eigene Methode nötig
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
		this.distanceToWall = distanceSensor.getLeftDistance();
	}

	@Override
	public void act(TouchSensorID id) {
		robot.getPilot().stop();
		robot.getPilot().travel(-10.0);
		//waitComplete(500);
		robot.getPilot().rotate(wallToFollow * 90.0);
		//waitComplete(500);
		robot.getPilot().travel(10.0);
		//waitComplete(500);
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
}
