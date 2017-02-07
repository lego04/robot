package test;

import robot.Boss;
import robot.Robot;
import util.EscapeThread;

public class BossTest {

	public static void main(String[] args) {
		
		EscapeThread escape = new EscapeThread();
		escape.startThread();
		Robot robot = new Robot();
		
		Boss boss = new Boss(robot);
		
		//give him hard
		boss.fightBossTillDeath();

	}

}
