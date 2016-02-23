package code;

public class Environment
{
	public static final double MAX_WIND_SPEED = 20; //km/h
	//TODO: The parameters for the environment are not necessarily static, nor constant. Perhaps use an interface to show what we need to have?
	private static final double GRAVITY = 9.807;
	private static final double AIR_DENSITY = 1.2;
	private double windSpeed;
	
	public Environment(double windSpeed) throws IllegalWindSpeedException
	{
		setWindSpeed(windSpeed);
	}
	
	private void setWindSpeed(double windSpeed) throws IllegalWindSpeedException
	{
		if(windSpeed < -MAX_WIND_SPEED || windSpeed > MAX_WIND_SPEED)
			throw new IllegalWindSpeedException("Wind Speed must be between -20 and 20 km/h");
		//Convert to m/s
		this.windSpeed = windSpeed * 1 / 3.6; 
	}
	
	public static double getGravity()
	{
		return GRAVITY;
	}
	public static double getAirDensity()
	{
		return AIR_DENSITY;
	}
	public double getWindSpeed()
	{
		return windSpeed;
	}
}
