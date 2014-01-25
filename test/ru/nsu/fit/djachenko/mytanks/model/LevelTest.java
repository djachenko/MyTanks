package ru.nsu.fit.djachenko.mytanks.model;

import org.junit.Test;
import ru.nsu.fit.djachenko.mytanks.model.cellls.Cell;
import ru.nsu.fit.djachenko.mytanks.model.entries.Level;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

import java.io.IOException;

import static org.junit.Assert.*;

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

	@Test
	public void testMoveTank()
	{
		Level level = new Level(5, 5);

		for(Direction tankDirection : Direction.values())
		{
			for (Direction moveDirection : Direction.values())
			{
        		int tankX = 2;
				int tankY = 2;

				Tank tank = new Tank(level, tankX, tankY, tankDirection);

				try
				{
					level.setActiveTank(tank);
				}
				catch (MapFormatException e)
				{
					e.printStackTrace();
				}

				for (int y = 0; y < level.height(); y++)
				{
					for (int x = 0; x < level.width(); x++)
					{
						int dx = x - tankX;
						int dy = y - tankY;

						if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1 &&
								!(tankDirection.isVertical() && dx != 0 && dy == tankDirection.getDy() ||
										tankDirection.isHorisontal() && dx == tankDirection.getDx() && dy != 0))
						{
							assertNotNull("Null cell at (" + y + ';' + x + ") ", level.at(x, y));
							assertEquals("Wrong cell type at (" + x + ';' + y + ").", Cell.Type.TANK, level.at(x, y).type);
						}
						else
						{
							assertEquals("Wrong cell type at (" + x + ';' + y + ").", Cell.Type.GROUND, level.at(x, y).type);
						}
					}
				}

				level.moveTank(moveDirection);

				if (moveDirection == tankDirection)
				{
					assertEquals("Tank middle x wasn't moved. " + tank.getY() + tankDirection.name(), tankX + moveDirection.getDx(), tank.getX());
					assertEquals("Tank middle y wasn't moved.", tankY + moveDirection.getDy(), tank.getY());

					tankX += moveDirection.getDx();
					tankY += moveDirection.getDy();
				}
				else
				{
					assertEquals("Tank middle x was moved, but tank has only to be turned.", tankX, tank.getX());
					assertEquals("Tank middle y was moved, but tank has only to be turned.", tankY, tank.getY());
				}

				for (int y = 0; y < level.height(); y++)
				{
					for (int x = 0; x < level.width(); x++)
					{
						int dx = x - tankX;
						int dy = y - tankY;


						if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1 &&
								!(moveDirection.isVertical() && dx != 0 && dy == moveDirection.getDy() ||
										moveDirection.isHorisontal() && dx == moveDirection.getDx() && dy != 0))
						{
							assertNotNull("Null cell at (" + y + ';' + x + ") ", level.at(x, y));
							assertEquals("Wrong cell type at (" + x + ';' + y + ").", Cell.Type.TANK, level.at(x, y).type);
						}
						else
						{
							assertEquals("Wrong cell type at (" + x + ';' + y + ").", Cell.Type.GROUND, level.at(x, y).type);
						}
					}
				}
			}
		}
	}

	@Test
	public void testSetTankWithoutPreviousTank()
	{
		Level level = new Level(5, 5);

		assertNull("Not null tank before initialization", level.getActiveTank());

		Tank tank = new Tank(level, 2, 2, Direction.DOWN);

		try
		{
			level.setActiveTank(tank);
		}
		catch (MapFormatException e)
		{
			e.printStackTrace();
		}

		assertNotNull("Null tank after initialization", level.getActiveTank());
	}
}
