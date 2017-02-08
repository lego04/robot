package util;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import robot.Robot;

public class EscapeThread implements Runnable {

	private Thread t;
	private Robot robot;

	/**
	 * creates thread with function to escape only, i.e. reseting or changing
	 * state during the run of the program is not possible. Useful in tests
	 * where this things are not needed.
	 */
	public EscapeThread() {
		t = null;
	}

	public EscapeThread(Robot robot) {
		t = null;
		this.robot = robot;
	}

	public void startThread() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		} else {
			interruptThread();
			t = new Thread(this);
			t.start();
		}
	}

	public void interruptThread() {
		if (!t.isInterrupted()) {
			t.interrupt();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}
			if (Button.ENTER.isDown() && (robot != null)) {
				// System.out.println("Button enter pressed!");
				// LCD.clearDisplay();
				// LCD.drawString("lcd output", 0, 0);
				runStateSelection();
			}
		}
	}

	private void runStateSelection() {
		States states = robot.getStates();
		stopAndResetInOwnThread();
		LCD.clear();
		Station state = robot.getStates().getCurrentState();
		printStates(state);
		boolean leftUp, rightUp, enterUp, escapeUp;
		while (true) {
			do {
				leftUp = Button.LEFT.isUp();
				rightUp = Button.RIGHT.isUp();
				enterUp = Button.ENTER.isUp();
				escapeUp = Button.ESCAPE.isUp();
			} while (leftUp && rightUp && enterUp && escapeUp);
			if (!leftUp) {
				state = states.getPredecessorOf(state);
				LCD.clear();
				printStates(state);
			} else if (!rightUp) {
				state = states.getSuccessorOf(state);
				LCD.clear();
				printStates(state);
			} else if (!escapeUp) {
				System.exit(0);
			} else {
				// enter is pressed
				LCD.clear();
				break;
			}

			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// LCD.clear();
		// LCD.drawString("starting: " + state, 0, 0);
		startFromStationInOwnThread(states, state);
	}

	private void printStates(Station current) {
		States states = robot.getStates();
		Station pred = states.getPredecessorOf(current);
		Station succ = states.getSuccessorOf(current);
		LCD.drawString(current.toString(), 0, 0);
		LCD.drawString("<- " + pred.toString(), 0, 1);
		LCD.drawString("-> " + succ.toString(), 0, 2);
	}

	private void stopAndResetInOwnThread() {
		Thread stopThread = new Thread(new Runnable() {

			@Override
			public void run() {
				robot.getStates().stopAndReset();

			}
		});

		stopThread.start();
	}

	private void startFromStationInOwnThread(final States states, final Station start) {
		Thread startThread = new Thread(new Runnable() {

			@Override
			public void run() {
				states.startFromState(start);
				//robot = new Robot();
				//robot.getStates().startFromState(start);
			}
		});

		robot.setInterruptedFalse();
		//LCD.drawString(robot.isInterrupted().toString(), 0, 7);
		startThread.start();
	}

	/**
	 * set robot so navigating through states will be possible
	 * 
	 * @param robot
	 */
	public void setRobot(Robot robot) {
		this.robot = robot;
	}

}
