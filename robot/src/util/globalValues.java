package util;

public final class globalValues {
	/*
	public static final float MINLIGHT = 0.05f;		//real Values 
	public static final float MAXLIGHT = 0.4f;
	*/
	public static final float MINLIGHT = 0.05f;		//Test Values (Floor) 
	public static final float MAXLIGHT = 0.3f;
	public static final float LEFT = 1f;
	public static final float RIGHT = -1f;
	
	//Wall Following
	public static int WALL_DIST_MIN = 12;
	public static int WALL_DIST_MAX = 25;
	public static int WALL_MID = (int) (WALL_DIST_MAX + WALL_DIST_MIN) / 2; 
	
	//Line Travel Speed
	public static final int LINETRAVELSPEED = 10; 
	
	//Umrechnungsfaktoren
	public static int floatToInt = 100;
	
	
}
