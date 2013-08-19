package ru.nsu.fit.djachenko.mytanks.model;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LevelTest
{
	@Test
	public void testNoTankMap()
	{
		try
		{
			Level level = new Level("testInitMap.tnk");

			fail("No exception while initialization with map without tanks");
		}
		catch (MapFormatException e)
		{
			assertEquals("Wrong exception message", "No tank info present", e.getLocalizedMessage());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testNotEnoughTanksMap()
	{
		try
		{
			Level level = new Level("secondLevelInitMap.tnk");

			fail("No exception while initialization with map with not enough tanks");
		}
		catch (MapFormatException e)
		{
			assertEquals("Wrong exception message", "Wrong tank count", e.getLocalizedMessage());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testMapWithWrongField()
	{
		try
		{
			Level level = new Level("thirdLevelInitMap.tnk");

			fail("No exception while initialization with map with with wrong field");
		}
		catch (MapFormatException e)
		{
			assertEquals("Wrong exception message", "Unparseable parameters", e.getLocalizedMessage());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testMapWithWrongTankDescription()
	{
		try
		{
			Level level = new Level("fourthLevelInitMap.tnk");

			fail("No exception while initialization with map with wrong tank description");
		}
		catch (MapFormatException e)
		{
			assertEquals("Wrong exception message", "Wrong tank description", e.getLocalizedMessage());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testMapWithUnparseableTankParameters()
	{
		try
		{
			Level level = new Level("fourthLevelInitMap.tnk");

			fail("No exception while initialization with map with with unparseable tank parameters");
		}
		catch (MapFormatException e)
		{
			assertEquals("Wrong exception message", "Wrong tank description", e.getLocalizedMessage());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
