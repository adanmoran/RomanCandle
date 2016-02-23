package code;

public class RomanCandle 
{
	private double deltaT;
	private Environment environment;
	private LaunchTube tube;
	
	public RomanCandle(Environment environment, LaunchTube launchTube, double deltaT) throws IllegalTimeIntervalException
	{
		this.environment = environment;
		this.tube = launchTube;
		setDeltaT(deltaT);
	}
	
	private void setDeltaT(double deltaT) throws IllegalTimeIntervalException
	{
		if(deltaT < 0)
			throw new IllegalTimeIntervalException("DeltaT must be positive.");
		else if(deltaT > 0.05)
			throw new IllegalTimeIntervalException("DeltaT must be under 0.05.");
		this.deltaT = deltaT;
	}
	
	public double getDeltaT()
	{
		return deltaT;
	}
	
	public double[][] generateStarPath() throws IllegalArgumentException
	{
		System.out.print("Generating Star.");
		Star star = tube.generateStar(environment);
		double time = 0;
		int iterations = (int) (star.getBurnTime() / deltaT);
		System.out.println("Running " + iterations + " iterations:");
		//starPath contains (iteration) number of rows with columns equal to time, x, and y.
		double[][] starPath = new double[iterations][3];
		int count = 0;
		double[] positions;
		while(count < iterations)
		{
			positions = star.getPositions();
			System.out.printf("%d, %f, %f\n", count, positions[0], positions[1]);
			starPath[count][0] = time;
			starPath[count][1] = positions[0];
			starPath[count][2] = positions[1];
			
			count++;
			time += deltaT;
			star.updateStar(time, deltaT);
			
		}
		return starPath;
		
	}
}
