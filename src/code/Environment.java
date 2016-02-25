package code;

/**
 * 
 * @author Adan Moran-MacDonald <12amm19 @ queensu.ca>
 * @since 24-02-2016
 *
 */
public class Environment
{
	public static final double MAX_WIND_SPEED = 20; //km/h
	public static final double GRAVITY = 9.807;
	public static final double AIR_DENSITY = 1.2;
	private double windSpeed;
	
	/**
	 * Create an earth environment with horizontal wind, where wind is positive in the x-axis.
	 * @param windSpeed a wind speed between -MAX_WIND_SPEED and MAX_WIND_SPEED (in m/s).
	 * @throws IllegalWindSpeedException
	 */
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
	
	/**
	 * @return the wind speed of this environment (in m/s).
	 */
	public double getWindSpeed()
	{
		return windSpeed;
	}
}
