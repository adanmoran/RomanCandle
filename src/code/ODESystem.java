package code;

/**
 * 
 * @author Adan Moran-MacDonald <12amm19 @ queensu.ca>
 * @since 24-02-2016
 *
 */
public interface ODESystem 
{
	/**
	 * 
	 * @return the number of ordinary differential equations.
	 */
	int getSystemSize();
	/**
	 * 
	 * @return the current value of the solved ODE.
	 */
	double[] getCurrentValues();
	/**
	 * @param time - the current time (in seconds).
	 * @param values - the non-time inputs to the ODE. Should be of size getSystemSize().
	 * @return - the value of the ODE evaluated at the inputs.
	 */
	double[] getFunction(double time, double[] values);
}
