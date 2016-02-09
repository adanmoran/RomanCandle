
public class Star implements ODESystem
{
	public Star()
	{}
	
	public int getSystemSize()
	{
		return 2;
	}
	
	//returns the current velocities of the star in an array [x,y]
	public double[] getCurrentValues()
	{}
	
	//Returns the values of the differential equations fx and fy, which depend on the parameters "time" and "values"
	//This will be used by the RungeKuttaSolver
	public double[] getFunction(double time, double[] values)
	{}
}
