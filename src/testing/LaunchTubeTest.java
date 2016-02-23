package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import code.Environment;
import code.IllegalAngleException;
import code.IllegalWindSpeedException;
import code.LaunchTube;

public class LaunchTubeTest {

	@Rule 
	public ExpectedException thrown = ExpectedException.none();
	private String message = "Illegal launch angle.";
	@Test
	public void testFunctionalLaunchCreation() throws IllegalAngleException {
			for(int i = -15; i <= 15; i++)
			{
				LaunchTube a = new LaunchTube(i);
			}
			new LaunchTube(0.005);
			new LaunchTube(10.123123);
	}
	
	@Test
	public void testFailureLaunchCreation() throws IllegalAngleException
	{
		
		thrown.expect(IllegalAngleException.class);
		thrown.expectMessage(message);
		LaunchTube e1 = new LaunchTube(2000);
		LaunchTube e2 = new LaunchTube(-15.000001);
		LaunchTube e3 = new LaunchTube(20.001213123123);
		LaunchTube e4 = new LaunchTube(-10293);
		LaunchTube e5 = new LaunchTube(15.0000001);
		LaunchTube e6 = new LaunchTube(21);
	}

	@Test
	public void testGenerateStar() throws IllegalWindSpeedException {
		Environment env = new Environment(0);
		fail("Not yet implemented"); // TODO
	}

}
