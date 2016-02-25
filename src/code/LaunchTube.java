package code;

/**
 * 
 * @author Adan Moran-MacDonald <12amm19 @ queensu.ca>
 * @since 24-02-2016
 *
 */
public class LaunchTube 
{
	public static final double MAX_ANGLE = 15; //deg
	public static final double LAUNCH_SPEED = 22; //m/s
	private double launchAngle; //rad
	
	/**
	 * Create a launch tube to fire stars at a certain angle, as measured from the vertical axis.
	 * 
	 * @param launchAngle an angle between -MAX_ANGLE and MAX_ANGLE (in degrees).
	 * @throws IllegalAngleException
	 */
	public LaunchTube(double launchAngle) throws IllegalAngleException
	{
		setLaunchAngle(launchAngle);
	}
	
	/* Make sure the angle is correct */
	private void setLaunchAngle(double launchAngle)throws IllegalAngleException
	{
		if(launchAngle < -MAX_ANGLE || launchAngle > MAX_ANGLE)
			throw new IllegalAngleException("Illegal launch angle.");
		this.launchAngle = Math.toRadians(launchAngle);
	}
	
	/**
	 * <p>
	 * Create a Star object.
	 * 
	 *  The star will be generated with initial position [0,0] and initial velocities
	 *  as given by the angle of launch.
	 *  
	 *  The stars will be fired at 22 m/s.
	 *  </p>
	 * 
	 * @param environment an environment object that has a valid wind speed parameter.
	 * @return Star
	 * @throws IllegalArgumentException
	 */
	public Star generateStar(Environment environment) throws IllegalArgumentException
	{
		double[] initialPositions = {0, 0};
		double[] initialVelocities = {Math.sin(launchAngle)*LAUNCH_SPEED, Math.cos(launchAngle)*LAUNCH_SPEED};
		Star star = new Star(initialPositions, initialVelocities, Star.DEFAULT_MASS, environment);
		return star;
	}
}
