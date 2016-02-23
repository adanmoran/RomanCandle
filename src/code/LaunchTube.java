package code;

public class LaunchTube 
{
	public static final double MAX_ANGLE = 15; //deg
	public static final double LAUNCH_SPEED = 22; //m/s
	private double launchAngle; //rad
	
	public LaunchTube(double launchAngle) throws IllegalAngleException
	{
		setLaunchAngle(launchAngle);
	}
	
	private void setLaunchAngle(double launchAngle)throws IllegalAngleException
	{
		if(launchAngle < -MAX_ANGLE || launchAngle > MAX_ANGLE)
			throw new IllegalAngleException("Illegal launch angle.");
		this.launchAngle = Math.toRadians(launchAngle);
	}
	
	public Star generateStar(Environment environment) throws IllegalArgumentException
	{
		double[] initialPositions = {0, 0};
		double[] initialVelocities = {Math.sin(launchAngle)*LAUNCH_SPEED, Math.cos(launchAngle)*LAUNCH_SPEED};
		Star star = new Star(initialPositions, initialVelocities, Star.DEFAULT_MASS, environment);
		return star;
	}
}
