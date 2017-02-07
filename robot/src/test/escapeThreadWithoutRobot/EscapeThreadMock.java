package test.escapeThreadWithoutRobot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import util.States;
import util.Station;

public class EscapeThreadMock {

	public void simulateRun() throws IOException {

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String input = null;
		States states = new States(null);
		Station currentState = states.getCurrentState();
		
		do {
			
			System.out.println("current state: " + currentState);
			System.out.println("left: " + states.getPredecessorOf(currentState));
			System.out.println("right: " + states.getSuccessorOf(currentState));
			input = br.readLine();
			switch (input) {
			case "l":
				currentState = states.getPredecessorOf(currentState);
				break;
			case "r":
				currentState = states.getSuccessorOf(currentState);
				break;
			case "q":
				System.exit(0);
				break;
			default: System.out.println("unknown input");
			}

		} while (!input.equals("q"));
	}

}
