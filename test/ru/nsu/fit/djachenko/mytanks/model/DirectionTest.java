package ru.nsu.fit.djachenko.mytanks.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DirectionTest
{
	@Test
	public void testOpposite()
	{
		for (Direction direction : Direction.values())
		{
			switch (direction)
			{
				case RIGHT:
					assertEquals("Opposite to Direction." + direction.name() + " is wrong", Direction.LEFT, direction.opposite());
					break;
				case UP:
					assertEquals("Opposite to Direction." + direction.name() + " is wrong", Direction.DOWN, direction.opposite());
					break;
				case LEFT:
					assertEquals("Opposite to Direction." + direction.name() + " is wrong", Direction.RIGHT, direction.opposite());
					break;
				case DOWN:
					assertEquals("Opposite to Direction." + direction.name() + " is wrong", Direction.UP, direction.opposite());
					break;
				default:
					fail("Direction." + direction.name() + " was not expected");
					break;
			}
		}
	}

	@Test
	public void testDx()
	{
		for (Direction direction : Direction.values())
		{
			switch (direction)
			{
				case RIGHT:
					assertEquals("Direction." + direction.name() + ".getDx() is wrong", 1, direction.getDx());
					break;
				case LEFT:
					assertEquals("Direction." + direction.name() + ".getDx() is wrong", -1, direction.getDx());
					break;
				case UP:
				case DOWN:
					assertEquals("Direction." + direction.name() + ".getDx() is wrong", 0, direction.getDx());
					break;
				default:
					fail("Direction." + direction.name() + " was not expected");
					break;
			}
		}
	}

	@Test
	public void testDy()
	{
		for (Direction direction : Direction.values())
		{
			switch (direction)
			{
				case UP:
					assertEquals("Direction." + direction.name() + ".getDy() is wrong", -1, direction.getDy());
					break;
				case DOWN:
					assertEquals("Direction." + direction.name() + ".getDy() is wrong", 1, direction.getDy());
					break;
				case LEFT:
				case RIGHT:
					assertEquals("Direction." + direction.name() + ".getDy() is wrong", 0, direction.getDy());
					break;
				default:
					fail("Direction." + direction.name() + " was not expected");
					break;
			}
		}
	}

	@Test
	public void testIsVertical()
	{
		for (Direction direction : Direction.values())
		{
			switch (direction)
			{
				case UP:
				case DOWN:
					assertEquals("Direction." + direction.name() + ".isVertical is wrong", true, direction.isVertical());
					break;
				case LEFT:
				case RIGHT:
					assertEquals("Direction." + direction.name() + ".isVertical is wrong", false, direction.isVertical());
					break;
				default:
					fail("Direction." + direction.name() + " was not expected");
					break;
			}
		}
	}

	@Test
	public void testIsHorisontal()
	{
		for (Direction direction : Direction.values())
		{
			switch (direction)
			{
				case UP:
				case DOWN:
					assertEquals("Direction." + direction.name() + ".isHorisontal is wrong", false, direction.isHorisontal());
					break;
				case LEFT:
				case RIGHT:
					assertEquals("Direction." + direction.name() + ".isHorisontal is wrong", true, direction.isHorisontal());
					break;
				default:
					fail("Direction." + direction.name() + " was not expected");
					break;
			}
		}
	}
}
