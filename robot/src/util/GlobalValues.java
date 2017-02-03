package util;

public final class GlobalValues {
	/*
	public static final float MINLIGHT = 0.05f;		//real Values 
	public static final float MAXLIGHT = 0.4f;
	*/
	
	//Line Following
	public static final float MINLIGHT = 0.04f;		//Test Values (Floor) 
	public static final float MAXLIGHT = 0.25f;
	public static final float LEFT = 1f;
	public static final float RIGHT = -1f;
	public static final int LEFTWHEEL90DEGREE = 10;
	
	//Wall Following
	public static int WALL_DIST_MIN = 12;
	public static int WALL_DIST_MAX = 25;
	public static int WALL_MID = (int) (WALL_DIST_MAX + WALL_DIST_MIN) / 2; 
	public static int TRAVEL_DIST_LABYRINTH = 35;
	
	//Wall Following Travel Speed
	public static int WALLFOLLOWSPEED = 150;
	
	//Line Travel Speed
	public static final int LINETRAVELSPEED = 15; 
	
	//Umrechnungsfaktoren
	public static int floatToInt = 100;
	
	public static float DEGREETODIST = 14f;
	public static float DISTFORPOINTROTATE = 37.7f;
	
	
}
