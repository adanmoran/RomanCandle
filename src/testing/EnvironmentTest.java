package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import code.Environment;
import code.IllegalWindSpeedException;

public class EnvironmentTest {

	@Rule 
	public ExpectedException thrown = ExpectedException.none();
	
	String message = "Wind Speed must be between -20 and 20 km/h";

	@Test
	public void testFunctionalEnvironmentCreation() throws IllegalWindSpeedException {
			for(int i = -20; i <= 20; i++)
			{
				Environment a = new Environment(i);
			}
			new Environment(0.005);
			new Environment(10.123123);
	}
	
	@Test
	public void testFailureEnvironmentCreation() throws IllegalWindSpeedException
	{
		
		thrown.expect(IllegalWindSpeedException.class);
		thrown.expectMessage(message);
		Environment e1 = new Environment(2000);
		Environment e2 = new Environment(-20.000001);
		Environment e3 = new Environment(20.001213123123);
		Environment e4 = new Environment(-10293);
	}

	@Test
	public void testGetWindSpeed() throws IllegalWindSpeedException {
		Environment env = new Environment(-10);
		assertEquals(-10, env.getWindSpeed()*3.6, 0.00001);
		 env = new Environment(-20);
		assertEquals(-20, env.getWindSpeed()*3.6, 0.00001);
		 env = new Environment(10);
		assertEquals(10, env.getWindSpeed()*3.6, 0.00001);
		 env = new Environment(20);
		assertEquals(20, env.getWindSpeed()*3.6, 0.00001);
		 env = new Environment(0.025);
		assertEquals(0.025, env.getWindSpeed()*3.6, 0.00001);
	}

}
