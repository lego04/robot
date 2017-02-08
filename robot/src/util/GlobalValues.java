package util;

public final class GlobalValues {
	/*
	public static final float MINLIGHT = 0.05f;		//real Values 
	public static final float MAXLIGHT = 0.4f;
	*/
	
	//Line Following
	public static final float MINLIGHT = 0.05f;		//Test Values (Floor) 
	public static final float MAXLIGHT = 0.25f;
	public static final float AVG_LIGHT = 0.1f;
	public static final float LEFT = 1f;
	public static final float RIGHT = -1f;
	public static final int LEFT_WHEEL_90_DEGREE = 600;
	public static final int GYRO_RIGHT = - 120;
	
	//Wall Following
	public static int WALL_DIST_CRITICAL = 70;
	public static int WALL_DIST_MIN = 100;
	public static int WALL_DIST_MAX = 150;
	public static int WALL_MID = (int) (WALL_DIST_MAX + WALL_DIST_MIN) / 2; 
	public static int TRAVEL_DIST_LABYRINTH = 35;
	public static int LAB_LEFT_CURVE = 250;
	
	//Wall Following Travel Speed
	public static int WALLFOLLOWSPEED = 270;
	
	//Line Travel Speed
	public static final int LINETRAVELSPEED = 150; 
	
	//Umrechnungsfaktoren
	public static int floatToInt = 1000;
	
	/**
	 *  degrees the wheels have to to move a distance of 1 cm.
	 *  So to drive x cm you need to rotate both motors x * DEGREETODIST degree
	 */
	public static float DEGREE_TO_DIST = 14f;
	public static float DIST_FOR_POINTROTATE = 42f;
	public static float DIST_PER_DEGREE = DIST_FOR_POINTROTATE / 360;
	
	
}
