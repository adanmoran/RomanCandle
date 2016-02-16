package code;

public class Environment 
{
	public static final double G = 9.807;
	public static final double AIR_DENSITY = 1.2;
	public static final double DELTA_T = 0.05;
	public static double WIND_SPEED = 0;
	
	public static void changeWindSpeed(double newWindSpeed)
	{
		WIND_SPEED = newWindSpeed;
	}
}
