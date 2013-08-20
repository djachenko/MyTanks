package ru.nsu.fit.djachenko.mytanks.model;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LevelTest
{
	public static final String PATH = "resources/test/Level/";

	@Test
	public void testNoTankMap()
	{
		try
		{
			Level level = new Level(PATH + "LevelInitMap1.tnk");

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
			Level level = new Level(PATH + "LevelInitMap2.tnk");

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
			Level level = new Level(PATH + "LevelInitMap3.tnk");

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
			Level level = new Level(PATH + "LevelInitMap4.tnk");

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
			Level level = new Level(PATH + "LevelInitMap5.tnk");

			fail("No exception while initialization with map with with unparseable tank parameters");
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
}
