package code;

public class RungeKuttaSolver 
{
	//The runge Kutta solver should work for ANY SIZE of ode system - not just 2!
	//Also, the ODESystem cannot provide apparentValues - this should be changed.
	public static double[] rungeKutta(double time, double delta_t, ODESystem ode)
	{
		double q1x, q2x, q3x, q4x, q1y, q2y, q3y, q4y, vx, vxa, vy, vya;
		double[] currentValues = ode.getCurrentValues();
		double[] apparentValues = ode.getApparentValues();
		double[] nextValues = new double[2];
		vx = currentValues[0];
		vxa = apparentValues[0];
		vy = currentValues[1];
		vya = apparentValues[1];
		
		//Calculate the q1 values
		double[] f1Input = {vxa, vya};
		double[] f1 = ode.getFunction(time, f1Input);
		q1x = f1[0];
		q1y = f1[1];
		
		//Calculate the q2 values
		double[] f2Input = {vxa + q1x * delta_t / 2, vya + q1y * delta_t / 2};
		double[] f2 = ode.getFunction(time + delta_t / 2, f2Input);
		q2x = f2[0];
		q2y = f2[1];
		
		//Calculate the q3 values
		double[] f3Input = {vxa + delta_t * q2x / 2, vya + delta_t * q2y / 2};
		double[] f3 = ode.getFunction(time + delta_t / 2, f3Input);
		q3x = f3[0];
		q3y = f3[1];
		
		//Calculate the q4 values
		double[] f4Input = {vxa + delta_t * q3x, vya + delta_t * q3y};
		double[] f4 = ode.getFunction(time + delta_t, f4Input);
		q4x = f4[0];
		q4y = f4[1];
		
		//Use the regular, non-apparent velocity in the calculation of the next values
		nextValues[0] = vx + delta_t * (q1x + 2 * q2x + 2 * q3x + q4x) / 6;
		nextValues[1] = vy + delta_t * (q1y + 2 * q2y + 2 * q3y * q4y) / 6;
		
		return nextValues;
	}
}
