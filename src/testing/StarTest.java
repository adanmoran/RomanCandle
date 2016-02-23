package testing;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import code.Environment;
import code.Star;

public class StarTest {

	Star star;
	private double[] velocity = new double[] {22*Math.sin(Math.toRadians(10)),22*Math.cos(Math.toRadians(10))};
	@Before
	public void setUp() throws Exception {
		double[] position = {0,0};
		Environment env = new Environment(0);
		star = new Star(position, velocity, Star.DEFAULT_MASS, env);
	}

	@Test
	public void testGetSystemSize() {
		assertEquals(2, star.getSystemSize());
	}

	@Test
	public void testGetCurrentValues() {
		assertTrue(Arrays.equals(velocity, star.getCurrentValues()));
		star.updateStar(1, 0.05);
		System.out.println(star.getCurrentValues()[0] + "," + star.getCurrentValues()[1]);
		System.out.println(star.getPositions()[0] + "," + star.getPositions()[1]);
	}

	@Test
	public void testGetFunction() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetInitialXY() {
		fail("Not yet implemented"); // TODO
	}

}
