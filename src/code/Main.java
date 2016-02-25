package code;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 
 * @author Adan Moran-MacDonald <12amm19 @ queensu.ca>
 * @since 24-02-2016
 *
 */
public class Main {
	private static String file = "Non-Newtonian.csv";

	/**
	 * Generate a RomanCandle object and create a star, which will be saved in the file given by the string "file".
	 * Necessary user input will be collected using the IOHelper class.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		double launchAngle = IOHelper.getDouble(-LaunchTube.MAX_ANGLE, "Launch Angle [-" + LaunchTube.MAX_ANGLE + "->" +
				LaunchTube.MAX_ANGLE + "] (deg): ", LaunchTube.MAX_ANGLE);
		double windSpeed = IOHelper.getDouble(-Environment.MAX_WIND_SPEED, "Wind Speed [-" + Environment.MAX_WIND_SPEED + "->" +
				Environment.MAX_WIND_SPEED + "] (km/h): ", Environment.MAX_WIND_SPEED);
		double deltaT = IOHelper.getDouble(0, "Delta Time (0->0.05] (s): ", 0.05);
		try
		{
			
			// Generate a roman candle
			Environment earth = new Environment(windSpeed);
			LaunchTube tube = new LaunchTube(launchAngle);
			RomanCandle candle = new RomanCandle(earth, tube, deltaT);
			
			//Find the path for the star, and save it to the file
			double[][] path = candle.generateStarPath();
			initializeRandomAccessFile(file,path[0][0] + "," + path[0][1] + "," + path[0][2] + "\r\n");
			for(int i = 1; i < path.length; i++)
			{
				appendTextRandomAccess(file, path[i][0] + "," + path[i][1] + "," + path[i][2] + "\r\n");
			}
		}
		catch(IllegalAngleException e)
		{
			e.printStackTrace();
		}
		catch(IllegalWindSpeedException e)
		{
			e.printStackTrace();
		} 
		catch (IllegalTimeIntervalException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create (or re-initialize) a file with the given data.
	 * 
	 * @param outputFile the file in which to save the data
	 * @param data a string containing the information to be written
	 */
	public static void initializeRandomAccessFile(String outputFile, String data)
	{
		Path path = Paths.get(outputFile);
		try 
		{
			//We want to reinitialize the file - must delete it to do that.
			Files.deleteIfExists(path);
		} catch (IOException e)
		{
			System.err.println("IOException: " + e.getMessage());
		}
		//As seen in exercise, this enables us to write to any random location.
		ByteBuffer buff = ByteBuffer.allocate(8 * data.length());
		CharBuffer dBuff = buff.asCharBuffer();
		for(char c : data.toCharArray())
			dBuff.put(c);
		long bytesWritten;
		
		try(FileChannel fc = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE))
		{
			fc.position(0);
			bytesWritten = fc.write(buff);
		} catch (IOException e) 
		{
			System.err.println("IOException: " + e.getMessage());
		}
	}
	
	/**
	 * Open a file and write data at the end, immediately after the current existing data.
	 * 
	 * @param outputFile the file to which the data will be appended
	 * @param data the string containing the information to append
	 */
	public static void appendTextRandomAccess(String outputFile, String data)
	{
		Path path = Paths.get(outputFile);
		ByteBuffer buff = ByteBuffer.allocate(8 * data.length());
		CharBuffer dBuff = buff.asCharBuffer();
		for(char c : data.toCharArray())
			dBuff.put(c);
		long bytesWritten;
		
		try(FileChannel fc = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
		{
			bytesWritten = fc.write(buff);
		} catch (IOException e) 
		{
			System.err.println("IOException: " + e.getMessage());
		}
	}

}
