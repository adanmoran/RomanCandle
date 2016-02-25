package code;

/**
 * 
 * @author Adan Moran-MacDonald <12amm19 @ queensu.ca>
 * @since 24-02-2016
 */
public class Star implements ODESystem
{
	//Star constants
	public static final double DRAG_COEFFICIENT = 0.4; //no units
	public static final double BURN_RATE = 0.0030; //kg/sec
	public static final double DEFAULT_MASS = 0.008; //kg
	public static final double STAR_DENSITY = 1900; //kg/m^3
	
	//Constants for instantiated objects
	private double STAR_MASS; //kg
	private double BURN_TIME; //seconds
	
	//Current positions
	private double[] positions = new double[2];
	private double[] velocities = new double[2]; //m/sec
	private double WIND_SPEED; //m/sec
	
	/**
	 * 
	 * @param initialPositions a 2-dimensional array consisting of the horizontal and vertical positions (in m).
	 * @param initialVelocities a 2-dimensional array consisting of the horizontal and vertical velocities (in m/s).
	 * @param initialMass a positive, non-zero mass (in kg).
	 * @param env an Environment object with a valid wind speed parameter (in m/s).
	 */
	public Star(double[] initialPositions, double[] initialVelocities, double initialMass, Environment env)
	{
		setMass(initialMass);
		setPosition(initialPositions);
		setVelocity(initialVelocities);
		
		this.WIND_SPEED = env.getWindSpeed();
		this.BURN_TIME = STAR_MASS / BURN_RATE;
	}
	
	/* Methods to set values */
	private void setMass(double mass) throws IllegalArgumentException
	{
		if(mass < 0)
			throw new IllegalArgumentException("Star mass must be positive.");
		this.STAR_MASS = mass;
	}
	private void setVelocity(double[] velocities) throws IllegalArgumentException
	{
		if(velocities == null)
			throw new IllegalArgumentException("Inputted velocity array is null.");
		if(velocities.length != 2)
			throw new IllegalArgumentException("Inputted velocity array must be 2D.");
		this.velocities = velocities.clone();
	}
	
	private void setPosition(double[] positions) throws IllegalArgumentException
	{
		if(positions == null)
			throw new IllegalArgumentException("Inputted position array is null.");
		if(positions.length != 2)
			throw new IllegalArgumentException("Inputted position array must be 2D.");
		this.positions = positions.clone();
	}
	
	/**
	 * Update the velocity and position of the star by using the Runge-Kutta numerical integrator.
	 * 
	 * @param time the time value associated with this Star's position
	 * @param deltaT the interval of the time step
	 */
	public void updateStar(double time, double deltaT)
	{
		double[] velocityChange = RungeKuttaSolver.rungeKutta(this, time, deltaT);
		for(int i = 0; i < getSystemSize(); i++)
		{
			velocities[i] = velocityChange[i] + velocities[i];
			positions[i] = positions[i] + velocities[i] * deltaT;
		}
	}

	/**
	 * @return the time it takes the star to reach zero mass (in seconds).
	 */
	public double getBurnTime()
	{
		return BURN_TIME;
	}
	
	/**
	 * 
	 * @return the 2-dimensional array containing the X and Y position of the star.
	 */
	public double[] getPositions()
	{
		return positions.clone();
	}
	
	/**
	 * @see ODESystem.getSystemSize()
	 * 
	 * @return the size of the position and velocity arrays of this object.
	 */
	public int getSystemSize()
	{
		return 2;
	}
	
	/**
	 * 
	 * @see ODESystem.getCurrentValues()
	 * 
	 * @return the array containing the apparent velocities of the star, taking into account wind speed (in m/s).
	 */
	public double[] getCurrentValues()
	{
		double[] apparentVelocities = {velocities[0] - WIND_SPEED, velocities[1]};
		return apparentVelocities;
	}
	
	/**
	 * 
	 * @see ODESystem.getCurrentValues()
	 * 
	 * @param time the current time
	 * @param values an array containing the apparent velocities of the star, taking into account wind speed (in m/s).
	 * 
	 * @return the value of the differential equation with respect to velocity
	 */
	public double[] getFunction(double time, double[] values)
	{
		//Apparent speed was passed into this function
		double vxa = values[0];
		double vy = values[1];
		double[] retVals = {xDE(time,vxa,vy), yDE(time,vxa,vy)};
		
		return retVals;
	}
	
	/* This is the x-component of the ODE */
	private double xDE(double time, double vxa, double vy)
	{
		double mass = getMass(time);
		double velocity = getVelocity(vxa, vy);
		double drag = getDragForce(time, vxa, vy);
		return -drag * vxa / (mass * velocity);
	}
		
	/* This is the y-component of the ODE */
	private double yDE(double time, double vxa, double vy)
	{
		double velocity = getVelocity(vxa, vy);
		double mass = getMass(time);
		double dragForce = getDragForce(time, vxa, vy);
		return -Environment.GRAVITY - dragForce * vy / (mass * velocity);
	}
	
	/* Methods to retrieve Star values for use in ODE equations */
	private double getVelocity(double vx, double vy)
	{
		return Math.sqrt(vx*vx + vy*vy);
	}
	
	/* Get the mass at a certain time */
	private double getMass(double time)
	{
		return STAR_MASS - BURN_RATE * time;
	}
	
	/* Get the radius at a certain time */
	private double getRadius(double time)
	{
		//Volume is linearly related to mass
		double volume = getMass(time) / STAR_DENSITY;
		//The radius decays cubically, so we use the equation for volume to get the new radius
		return Math.pow(3.0 * volume / (4.0 * Math.PI), 1.0/3.0);
	}
	
	/* Get the area at a certain time */
	private double getArea(double time)
	{
		double radius = getRadius(time);
		return Math.PI * radius * radius;
	}
	
	/* Get the drag force for the current time step */
	private double getDragForce(double time, double vxa, double vya)
	{
		double area = getArea(time);
		double velocity = getVelocity(vxa, vya);
		return Environment.AIR_DENSITY * DRAG_COEFFICIENT * area * velocity * velocity * 0.5;
	}
}
