package code;

//TODO: The Environment class uses a constructor for windSpeed. Thus, the windSpeed should be passed into this star.
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
	
	public Star(double[] initialPositions, double[] initialVelocities, double initialMass, Environment env) throws IllegalArgumentException
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
	
	/* Update the velocities */
	public void updateStar(double time, double deltaT)
	{
		double[] velocityChange = RungeKuttaSolver.rungeKutta(this, time, deltaT);
		for(int i = 0; i < getSystemSize(); i++)
		{
			velocities[i] = velocityChange[i] + velocities[i];
			positions[i] = positions[i] + velocities[i] * deltaT;
		}
	}
	
	/* Used to calculate star path */
	public double getBurnTime()
	{
		return BURN_TIME;
	}
	
	public double[] getPositions()
	{
		return positions.clone();
	}
	
	/* Methods relating to the ODESystem interface*/
	public int getSystemSize()
	{
		return 2;
	}
	
	//returns the current velocities of the star in an array [x,y]
	// TODO: The environment windSpeed is not static - this needs to be changed.
	public double[] getCurrentValues()
	{
		double[] apparentVelocities = {velocities[0] - WIND_SPEED, velocities[1]};
		return apparentVelocities;
	}
	
	//Returns the values of the differential equations fx and fy, which depend on the parameters "time" and "values"
	//This will be used by the RungeKuttaSolver
	public double[] getFunction(double time, double[] values)
	{
		//Apparent speed was passed into this function
		double vxa = values[0];
		double vy = values[1];
		double[] retVals = {xDE(time,vxa,vy), yDE(time,vxa,vy)};
		
		return retVals;
	}
	
	private double xDE(double time, double vxa, double vy)
	{
		double mass = getMass(time);
		double velocity = getVelocity(vxa, vy);
		double drag = getDragForce(time, vxa, vy);
		return -drag * vxa / (mass * velocity);
	}
		
	//Gravity is not necessarily constant, as it could be part of a different environment variable. This should be changed.
	private double yDE(double time, double vxa, double vy)
	{
		double velocity = getVelocity(vxa, vy);
		double mass = getMass(time);
		double dragForce = getDragForce(time, vxa, vy);
		return -Environment.getGravity() - dragForce * vy / (mass * velocity);
	}
	
	/* Methods to retrieve Star values */
	
	private double getVelocity(double vx, double vy)
	{
		return Math.sqrt(vx*vx + vy*vy);
	}
	
	private double getMass(double time)
	{
		return STAR_MASS - BURN_RATE * time;
	}
	
	private double getRadius(double time)
	{
		//Volume is linearly related to mass
		double volume = getMass(time) / STAR_DENSITY;
		//The radius decays cubically, so we use the equation for volume to get the new radius
		return Math.pow(3.0 * volume / (4.0 * Math.PI), 1.0/3.0);
	}
	
	private double getArea(double time)
	{
		double radius = getRadius(time);
		return Math.PI * radius * radius;
	}
	
	//TODO: The environment air density is not necessarily static or constant. This should be changed.
	private double getDragForce(double time, double vxa, double vya)
	{
		double area = getArea(time);
		double velocity = getVelocity(vxa, vya);
		return Environment.getAirDensity() * DRAG_COEFFICIENT * area * velocity * velocity * 0.5;
	}
}
