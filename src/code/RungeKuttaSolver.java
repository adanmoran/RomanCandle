package code;

public class RungeKuttaSolver 
{
	public static double[] rungeKutta(ODESystem ode, double time, double deltaT)
	{
		int size = ode.getSystemSize();
		double[] q1 = new double[size], q1f = new double[size];
		double[] q2 = new double[size], q2f = new double[size];
		double[] q3 = new double[size], q3f = new double[size];
		double[] q4 = new double[size];
		double[] valueChange = new double[size];
		//Current values should return APPARENT velocitiess
		double[] currentValues = ode.getCurrentValues();
		
		//Calculate q1 using the ODE
		q1 = ode.getFunction(time, currentValues);
		//Calculate the input function for the q2
		for(int i = 0; i < size; i++)
		{
			q1f[i] = currentValues[i] + 0.5 * deltaT * q1[i];
		}
		//Now plug it in to get q2
		q2 = ode.getFunction(time + deltaT/2.0, q1f);
		//Calculate the input function for q3
		for(int i = 0; i < size; i++)
		{
			q2f[i] = currentValues[i] + 0.5 * deltaT * q2[i];
		}
		//Plug it in to get q3
		q3 = ode.getFunction(time + deltaT/2.0, q2f);
		//Calculate the input function for q4
		for(int i = 0; i < size; i++)
		{
			q3f[i] = currentValues[i] + deltaT * q3[i];
		}
		q4 = ode.getFunction(time + deltaT, q3f);
		//Calculate the return values
		for(int i = 0; i < size; i++)
		{
			valueChange[i] = deltaT*(q1[i] + 2*q2[i] + 2*q3[i] + q4[i])/6.0;
		}
		//Notice that we are NOT returning the full "next velocity"...
		//The return values must be added to the "current values".
		return valueChange;
	}
}
