package ru.nsu.fit.djachenko.mytanks.model;

import org.junit.Before;
import org.junit.Test;
import ru.nsu.fit.djachenko.mytanks.model.entries.Level;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

import java.io.IOException;

import static org.junit.Assert.*;

public class TankTest
{
	public static final String PATH = "resources/test/Tank/";
	private Level level;

	@Before
	public void prepareLevel()
	{
		try
		{
			level = new Level(PATH + "TankTestMap.tnk");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testAbleToMove()
	{
		for (int x = 3; x <= 5; x++)
		{
			Tank tank = new Tank(level, x, 7 + Math.abs(x - 4), Direction.DOWN);

			level.setActiveTank(tank);

			assertTrue("Tank cannot move from (" + tank.getX() + ';' + tank.getY() + ") down.", tank.ableToMove(Direction.DOWN));

			tank = new Tank(level, x, 8 + Math.abs(x - 4), Direction.DOWN);

			level.setActiveTank(tank);

			assertFalse("Tank can move from (" + tank.getX() + ';' + tank.getY() + ") down.", tank.ableToMove(Direction.DOWN));

			tank = new Tank(level, x, 13 - Math.abs(x - 4), Direction.UP);

			level.setActiveTank(tank);

			assertTrue("Tank cannot move from (" + tank.getX() + ';' + tank.getY() + ") up.", tank.ableToMove(Direction.UP));

			tank = new Tank(level, x, 12 - Math.abs(x - 4), Direction.UP);

			level.setActiveTank(tank);

			assertFalse("Tank can move from (" + tank.getX() + ';' + tank.getY() + ") up.", tank.ableToMove(Direction.UP));
		}
	}
}
