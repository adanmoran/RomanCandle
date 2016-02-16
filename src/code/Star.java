package code;

public class Star implements ODESystem
{
	//Star constants
	public static final double DRAG_COEFFICIENT = 0.4; //no units
	public static final double BURN_RATE = 0.0030; //kg/sec
	public static final double STARTING_MASS = 0.008; //kg
	public static final double STAR_DENSITY = 1900; //kg/m^3
	
	//Constants for instantiated objects
	private double STAR_MASS; //kg
	
	//Current positions
	private double X, Y;
	private double VX, VY;
	 
	/* Various constructors */
	public Star()
	{
		new Star(0,0,0,0,STARTING_MASS);
	}
	
	public Star(double initialX, double initialY)
	{
		new Star(initialX, initialY, 0,0,STARTING_MASS);
	}
	
	public Star(double initialX, double initialY, double initialVX, double initialVY)
	{
		new Star(initialX, initialY, initialVX, initialVY, STARTING_MASS);
	}
	
	public Star(double initialX, double initialY, double mass)
	{
		new Star(initialX, initialY, 0, 0, mass);
	}
	
	public Star(double initialX, double initialY, double initialVX, double initialVY, double initialMass)
	{
		setPosition(initialX, initialY);
		setVelocity(initialVX, initialVY);
		this.STAR_MASS = initialMass;
	}
	
	/* Methods to set location values */
	public void setVelocity(double vX, double vY)
	{
		this.VX = vX;
		this.VY = vY;
	}
	
	public void setPosition(double x, double y)
	{
		this.X = x;
		this.Y = y;
	}
	
	/* Methods relating to the ODESystem interface*/
	public int getSystemSize()
	{
		return 2;
	}
	
	//returns the current velocities of the star in an array [x,y]
	public double[] getCurrentValues()
	{
		double[] velocities = {VX, VY};
		return velocities;
	}
	
	public double[] getApparentValues()
	{
		double[] apparentVelocities = {VX - Environment.WIND_SPEED, VY};
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
		
	private double yDE(double time, double vxa, double vy)
	{
		double velocity = getVelocity(vxa, vy);
		double mass = getMass(time);
		double dragForce = getDragForce(time, vxa, vy);
		return -Environment.G - dragForce * vy / (mass * velocity);
	}
	
	/* Methods to retrieve Star values */
	public double[] getPosition()
	{
		double[] positions = {X, Y};
		return positions;
	}
	
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
	
	private double getDragForce(double time, double apparentXSpeed, double apparentYSpeed)
	{
		double area = getArea(time);
		double velocity = getVelocity(apparentXSpeed, apparentYSpeed);
		return Environment.AIR_DENSITY * DRAG_COEFFICIENT * area * velocity * velocity * 0.5;
	}
}
