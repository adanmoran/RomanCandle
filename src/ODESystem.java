
public interface ODESystem 
{
	int getSystemSize();
	double[] getCurrentValues();
	double[] getFunction(double time, double[] values);
}
