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
	private double BURN_TIME; //seconds
	
	//Current positions
	private double X, Y;
	private double VX, VY;
	 
	public Star()
	{
		new Star(0,0,STARTING_MASS);
	}
	
	public Star(double initialX, double initialY)
	{
		new Star(initialX, initialY, STARTING_MASS);
	}
	
	public Star(double initialX, double initialY, double mass)
	{
		this.X = initialX;
		this.Y = initialY;
		this.STAR_MASS = mass;
		setBurnTime(mass);
	}
	
	private void setBurnTime(double mass)
	{
		BURN_TIME = mass / BURN_RATE;
	}
	
	public int getSystemSize()
	{
		return 2;
	}
	
	//returns the current velocities of the star in an array [x,y]
	public double[] getCurrentValues()
	{
		
	}
	
	//Returns the values of the differential equations fx and fy, which depend on the parameters "time" and "values"
	//This will be used by the RungeKuttaSolver
	public double[] getFunction(double time, double[] values)
	{
		double vxa = values[0]; // - windSpeed;
		double vy = values[1];

		double[] retVals = {xDE(time,vxa,vy), yDE(time,vxa,vy)};
		return retVals;
	}
	
	public double[] getInitialXY()
	{
		double[] positions = {INITIAL_X, INITIAL_Y};
		return positions;
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
	
	private double getVelocity(double vx, double vy)
	{
		return Math.sqrt(vx*vx + vy*vy);
	}
	
	private double getMass(double time)
	{
		return STAR_MASS - BURN_RATE * time;
	}
	
	private double apparentXSpeed(double xSpeed, double windSpeed)
	{
		//The speed relative to the wind
		return xSpeed - windSpeed;
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
