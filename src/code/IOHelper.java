package code;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author Adan Moran-MacDonald <12amm19 @ queensu.ca>
 * @since 24-02-2016
 *
 */
public class IOHelper 
{
	private enum DATA_TYPE {FLOAT, DOUBLE, LONG, INTEGER};
	private static Scanner scanner = new Scanner(System.in);
	private static double getRealValue(double low, String prompt, double high, DATA_TYPE type)
	{
		double userNum = 0;
		boolean inputOK = false;
		boolean inputInRange = false;
		String dump;
		
		while(!inputOK || !inputInRange)
		{
			System.out.print(prompt);
			try
			{
				switch(type)
				{
				case DOUBLE:
					userNum = scanner.nextDouble();
					break;
				case FLOAT:
					userNum = scanner.nextFloat();
					break;
				case LONG:
					userNum = scanner.nextLong();
					break;
				case INTEGER:
					userNum = scanner.nextInt();
					break;
				default:
					userNum = scanner.nextDouble();
					break;
				}
				dump = scanner.nextLine();
				inputOK = true;
			}
			catch(InputMismatchException e)
			{
				dump = scanner.nextLine();
				System.out.println("\"" + dump + "\" is not a correct " + type.toString().toLowerCase() + " value.");
			}
			
			if(inputOK)
			{
				if(userNum >= low && userNum <= high)
					inputInRange = true;
				else
					System.out.println("Value is out of range! It must be between " + low + " and " + high);
			}
		}	
		
		return userNum;
	}
	
	/**
	 * Prompt the user for a double using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param low minimum value allowed
	 * @param prompt the string with which to prompt for user input
	 * @param high the maximum value allowed
	 * @return the valid double inputed by the user
	 */
	public static double getDouble(double low, String prompt, double high)
	{
		return getRealValue(low,prompt,high, DATA_TYPE.DOUBLE);
	}
	/**
	 * Prompt the user for a double using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param low minimum value allowed
	 * @param prompt the string with which to prompt for user input
	 * @return the valid double inputed by the user
	 */
	public static double getDouble(double low, String prompt)
	{
		return getRealValue(low, prompt, Double.MAX_VALUE, DATA_TYPE.DOUBLE);
	}
	/**
	 * Prompt the user for a double using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param prompt the string with which to prompt for user input
	 * @param high the maximum value allowed
	 * @return the valid double inputed by the user
	 */
	public static double getDouble(String prompt, double high)
	{
		return getRealValue(-Double.MAX_VALUE,prompt,high, DATA_TYPE.DOUBLE);
	}
	/**
	 * Prompt the user for a double using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param prompt the string with which to prompt for user input
	 * @return the valid double inputed by the user
	 */
	public static double getDouble(String prompt)
	{
		return getRealValue(-Double.MAX_VALUE, prompt, Double.MAX_VALUE, DATA_TYPE.DOUBLE);
	}
	/**
	 * Prompt the user for a double using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @return the valid double inputed by the user
	 */
	public static double getDouble()
	{
		return getRealValue(-Double.MAX_VALUE, "Enter a double: ", Double.MAX_VALUE, DATA_TYPE.DOUBLE);
	}
	
	/**
	 * Prompt the user for a float using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param low minimum value allowed
	 * @param prompt the string with which to prompt for user input
	 * @param high the maximum value allowed
	 * @return the valid float inputed by the user
	 */
	public static float getFloat(float low, String prompt, float high)
	{
		return (float) getRealValue(low, prompt, high, DATA_TYPE.FLOAT);
	}
	/**
	 * Prompt the user for a float using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param low minimum value allowed
	 * @param prompt the string with which to prompt for user input
	 * @return the valid float inputed by the user
	 */
	public static float getFloat(float low, String prompt)
	{
		return (float) getRealValue(low, prompt, Float.MAX_VALUE, DATA_TYPE.FLOAT);
	}
	/**
	 * Prompt the user for a float using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param prompt the string with which to prompt for user input
	 * @param high the maximum value allowed
	 * @return the valid float inputed by the user
	 */
	public static float getFloat(String prompt, float high)
	{
		return (float) getRealValue(-Float.MAX_VALUE, prompt, high, DATA_TYPE.FLOAT);
	}
	/**
	 * Prompt the user for a float using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param prompt the string with which to prompt for user input
	 * @return the valid float inputed by the user
	 */
	public static float getFloat(String prompt)
	{
		return (float) getRealValue(-Float.MAX_VALUE, prompt, Float.MAX_VALUE, DATA_TYPE.FLOAT);
	}
	/**
	 * Prompt the user for a float using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @return the valid float inputed by the user
	 */
	public static float getFloat()
	{
		return (float) getRealValue(-Float.MAX_VALUE, "Enter a float: ", Float.MAX_VALUE, DATA_TYPE.FLOAT);
	}
	
	/**
	 * Prompt the user for a long using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param low minimum value allowed
	 * @param prompt the string with which to prompt for user input
	 * @param high the maximum value allowed
	 * @return the valid long inputed by the user
	 */
	public static long getLong(long low, String prompt, long high)
	{
		return (long) getRealValue(low, prompt, high, DATA_TYPE.LONG);
	}
	/**
	 * Prompt the user for a long using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param low minimum value allowed
	 * @param prompt the string with which to prompt for user input
	 * @return the valid long inputed by the user
	 */
	public static long getLong(long low, String prompt)
	{
		return (long) getRealValue(low, prompt, Long.MAX_VALUE, DATA_TYPE.LONG);
	}
	/**
	 * Prompt the user for a long using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param prompt the string with which to prompt for user input
	 * @param high the maximum value allowed
	 * @return the valid long inputed by the user
	 */
	public static long getLong(String prompt, long high)
	{
		return (long) getRealValue(Long.MIN_VALUE, prompt, high, DATA_TYPE.LONG);
	}
	/**
	 * Prompt the user for a long using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param prompt the string with which to prompt for user input
	 * @return the valid long inputed by the user
	 */
	public static long getLong(String prompt)
	{
		return (long) getRealValue(Long.MIN_VALUE, prompt, Long.MAX_VALUE, DATA_TYPE.LONG);
	}
	/**
	 * Prompt the user for a long using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @return the valid long inputed by the user
	 */
	public static long getLong()
	{
		return (long) getRealValue(Long.MIN_VALUE, "Enter a long: ", Long.MAX_VALUE, DATA_TYPE.LONG);
	}
	
	/**
	 * Prompt the user for an integer using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param low minimum value allowed
	 * @param prompt the string with which to prompt for user input
	 * @param high the maximum value allowed
	 * @return the valid integer inputed by the user
	 */
	public static int getInt(int low, String prompt, int high)
	{
		return (int) getRealValue(low, prompt, high, DATA_TYPE.INTEGER);
	}
	/**
	 * Prompt the user for an integer using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param low minimum value allowed
	 * @param prompt the string with which to prompt for user input
	 * @return the valid integer inputed by the user
	 */
	public static int getInt(int low, String prompt)
	{
		return (int) getRealValue(low, prompt, Integer.MAX_VALUE, DATA_TYPE.INTEGER);
	}
	/**
	 * Prompt the user for an integer using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param prompt the string with which to prompt for user input
	 * @param high the maximum value allowed
	 * @return the valid integer inputed by the user
	 */
	public static int getInt(String prompt, int high)
	{
		return (int) getRealValue(Integer.MIN_VALUE, prompt, high, DATA_TYPE.INTEGER);
	}
	/**
	 * Prompt the user for an integer using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @param prompt the string with which to prompt for user input
	 * @return the valid integer inputed by the user
	 */
	public static int getInt(String prompt)
	{
		return (int) getRealValue(Integer.MIN_VALUE, prompt, Integer.MAX_VALUE, DATA_TYPE.INTEGER);
	}
	/**
	 * Prompt the user for an integer using the console.
	 * 
	 * If a bad value is inputed, the method will request it again.
	 * 
	 * @return the valid integer inputed by the user
	 */
	public static int getInt()
	{
		return (int) getRealValue(Integer.MIN_VALUE, "Enter an integer: ", Integer.MAX_VALUE, DATA_TYPE.INTEGER);
	}
	
	/**
	 * Prompt the user for a string using the console.
	 * 
	 * Any input will be considered valid.
	 * 
	 * @return the valid string inputed by the user
	 */
	public static String getString()
	{
		String userString = scanner.nextLine();
		return userString;
	}
}
