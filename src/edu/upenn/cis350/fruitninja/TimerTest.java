package edu.upenn.cis350.fruitninja;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TimerTest {
	
	Timer t;

	@Before
	public void setUp() throws Exception {
		// initialize a new Timer object before EVERY test
		t = new Timer();
	}

	@Test
	public void testStartTime() {
		long actual = t.getStartTime();
		long expected = 0;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testTotalTime() {
		long actual = t.getTotalTime();
		long expected = t.getEndTime() - t.getStartTime();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testElapsedTime() {
		long actual = (long)(t.getElapsedTime());
		long expected = (System.nanoTime() - t.getStartTime())/1000000000;
		assertEquals(expected, actual);
	}

}
