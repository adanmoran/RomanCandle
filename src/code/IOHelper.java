package code;
import java.util.InputMismatchException;
import java.util.Scanner;

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
	
	
	/* Methods for double type */
	public static double getDouble(double low, String prompt, double high)
	{
		return getRealValue(low,prompt,high, DATA_TYPE.DOUBLE);
	}
	
	public static double getDouble(double low, String prompt)
	{
		return getRealValue(low, prompt, Double.MAX_VALUE, DATA_TYPE.DOUBLE);
	}
	
	public static double getDouble(String prompt, double high)
	{
		return getRealValue(-Double.MAX_VALUE,prompt,high, DATA_TYPE.DOUBLE);
	}
	
	public static double getDouble(String prompt)
	{
		return getRealValue(-Double.MAX_VALUE, prompt, Double.MAX_VALUE, DATA_TYPE.DOUBLE);
	}
	
	public static double getDouble()
	{
		return getRealValue(-Double.MAX_VALUE, "Enter a double: ", Double.MAX_VALUE, DATA_TYPE.DOUBLE);
	}
	
	
	/* Methods for float type */
	public static float getFloat(float low, String prompt, float high)
	{
		return (float) getRealValue(low, prompt, high, DATA_TYPE.FLOAT);
	}
	
	public static float getFloat(float low, String prompt)
	{
		return (float) getRealValue(low, prompt, Float.MAX_VALUE, DATA_TYPE.FLOAT);
	}
	
	public static float getFloat(String prompt, float high)
	{
		return (float) getRealValue(-Float.MAX_VALUE, prompt, high, DATA_TYPE.FLOAT);
	}
	
	public static float getFloat(String prompt)
	{
		return (float) getRealValue(-Float.MAX_VALUE, prompt, Float.MAX_VALUE, DATA_TYPE.FLOAT);
	}
	
	public static float getFloat()
	{
		return (float) getRealValue(-Float.MAX_VALUE, "Enter a float: ", Float.MAX_VALUE, DATA_TYPE.FLOAT);
	}
	
	
	/* Methods for long type */
	public static long getLong(long low, String prompt, long high)
	{
		return (long) getRealValue(low, prompt, high, DATA_TYPE.LONG);
	}
	
	public static long getLong(long low, String prompt)
	{
		return (long) getRealValue(low, prompt, Long.MAX_VALUE, DATA_TYPE.LONG);
	}
	
	public static long getLong(String prompt, long high)
	{
		return (long) getRealValue(Long.MIN_VALUE, prompt, high, DATA_TYPE.LONG);
	}
	
	public static long getLong(String prompt)
	{
		return (long) getRealValue(Long.MIN_VALUE, prompt, Long.MAX_VALUE, DATA_TYPE.LONG);
	}

	public static long getLong()
	{
		return (long) getRealValue(Long.MIN_VALUE, "Enter a long: ", Long.MAX_VALUE, DATA_TYPE.LONG);
	}
	
	
	/* Methods for integer type */
	public static int getInt(int low, String prompt, int high)
	{
		return (int) getRealValue(low, prompt, high, DATA_TYPE.INTEGER);
	}

	public static int getInt(int low, String prompt)
	{
		return (int) getRealValue(low, prompt, Integer.MAX_VALUE, DATA_TYPE.INTEGER);
	}
	
	public static int getInt(String prompt, int high)
	{
		return (int) getRealValue(Integer.MIN_VALUE, prompt, high, DATA_TYPE.INTEGER);
	}
	
	public static int getInt(String prompt)
	{
		return (int) getRealValue(Integer.MIN_VALUE, prompt, Integer.MAX_VALUE, DATA_TYPE.INTEGER);
	}
	
	public static int getInt()
	{
		return (int) getRealValue(Integer.MIN_VALUE, "Enter an integer: ", Integer.MAX_VALUE, DATA_TYPE.INTEGER);
	}
	
	/* Method for getting strings */
	public static String getString()
	{
		String userString = scanner.nextLine();
		return userString;
	}
}
