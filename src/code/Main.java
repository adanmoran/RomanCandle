package code;

public class Main {

	public static void main(String[] args)
	{
		double launchAngle = IOHelper.getDouble(-LaunchTube.MAX_ANGLE, "Launch Angle [-" + LaunchTube.MAX_ANGLE + "->" +
				LaunchTube.MAX_ANGLE + "] (deg): ", LaunchTube.MAX_ANGLE);
		double windSpeed = IOHelper.getDouble(-Environment.MAX_WIND_SPEED, "Wind Speed [-" + Environment.MAX_WIND_SPEED + "->" +
				Environment.MAX_WIND_SPEED + "] (km/h): ", Environment.MAX_WIND_SPEED);
		double deltaT = IOHelper.getDouble(0, "Delta Time [0->0.05] (s): ", 0.05);
		try
		{
			Environment earth = new Environment(windSpeed);
			LaunchTube tube = new LaunchTube(launchAngle);
			RomanCandle candle = new RomanCandle(earth, tube, deltaT);
			double[][] path = candle.generateStarPath();
			write(path);
		}
		catch(IllegalAngleException e)
		{
			e.printStackTrace();
		}
		catch(IllegalWindSpeedException e)
		{
			e.printStackTrace();
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalTimeIntervalException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(double[][] path)
	{
		for(int i = 0; i < path.length; i++)
		{
			for(int j = 0; j < path[i].length-1; j++)
			{
				System.out.print(path[i][j] + ", ");
			}
			System.out.println(path[i][path[i].length-1]);
		}
	}

}
