package ru.nsu.fit.djachenko.mytanks.model;

import org.junit.Test;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;

import java.io.IOException;

import static org.junit.Assert.*;

public class FieldTest
{
	public static final String PATH = "resources/test/Field/";

	@Test
	public void testEmptyFieldConstructor()
	{
		for (int width = 1; width < 10; width++)
		{
			for (int height = 1; height < 10; height++)
			{
				Field field = new Field(width, height);

				assertEquals("Wrong width in empty field constructor with (" + width + ", " + height + ").", height, field.height());
				assertEquals("Wrong width in empty field constructor with (" + width + ", " + height + ").", width, field.width());

				for (int y = 0; y < field.height(); y++)
				{
					for (int x = 0; x < field.width(); x++)
					{
						assertNotNull("Null cell at (" + y + ';' + x + ") ", field.at(x, y));
						assertEquals("Wrong cell type at (" + x + ';' + y + ").", Cell.Type.GROUND, field.at(x, y).type);
					}
				}
			}
		}
	}

	@Test
	public void testInitWithRegularMap()
	{
		try
		{
			Field field = new Field(PATH + "FieldInitMap1.tnk");

			int width = field.width();

			for (int y = 0; y < field.height(); y++)
			{
				for (int x = 0; x < field.width(); x++)
				{
					if ((y * width + x + 1) % 3 == 0)
					{
						assertNotNull("Null cell at (" + y + ';' + x + ") ", field.at(x, y));
						assertEquals("Wrong cell type at (" + x + ';' + y + ").", Cell.Type.WALL, field.at(x, y).type);
					}
					else
					{
						assertNotNull("Null cell at (" + y + ';' + x + ") ", field.at(x, y));
						assertEquals("Wrong cell type at (" + x + ';' + y + ").", Cell.Type.GROUND, field.at(x, y).type);
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (MapFormatException e)
		{
			e.printStackTrace();
			fail("Exception while initialization on correct map");
		}
	}

	@Test
	public void testInitWithWrongMaps()
	{
		Field field = new Field();

		try
		{
			field.init(PATH + "FieldInitMap2.tnk");

			fail("No exception with long line.");
		}
		catch (MapFormatException e)
		{}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			field.init(PATH + "FieldInitMap3.tnk");

			fail("No exception with short line.");
		}
		catch (MapFormatException e)
		{}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			field.init(PATH + "FieldInitMap4.tnk");

			fail("No exception with missed line.");
		}
		catch (MapFormatException e)
		{}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testDrawTank()
	{
		int width = 5;
		int height = 5;

		for(int tankX = 1; tankX < width - 1; tankX++)
		{
			for (int tankY = 1; tankY < height - 1; tankY++)
			{
				for (Direction tankDirection : Direction.values())
				{
					Level level = new Level(width, height);

					Tank tank = new Tank(level, tankX, tankY, tankDirection);

					try
					{
						level.draw(tank);
					}
					catch (MapFormatException e)
					{
						fail("Exception in draw.");
					}

					for (int y = 0; y < level.height(); y++)
					{
						for (int x = 0; x < level.width(); x++)
						{
							int dx = x - tankX;
							int dy = y - tankY;

							if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1 &&
									!(tankDirection.isVertical() && dx != 0 && dy == tankDirection.dy ||
									tankDirection.isHorisontal() && dx == tankDirection.dx && dy != 0))
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
	}

	@Test
	public void testEraseTank()
	{
		int width = 5;
		int height = 5;

		for(int tankX = 1; tankX < width - 1; tankX++)
		{
			for (int tankY = 1; tankY < height - 1; tankY++)
			{
				for (Direction tankDirection : Direction.values())
				{
					Level level = new Level(width, height);

					Tank tank = new Tank(level, tankX, tankY, tankDirection);

					try
					{
						level.draw(tank);
					}
					catch (MapFormatException e)
					{
						fail("Exception in draw.");
					}

					for (int y = 0; y < level.height(); y++)
					{
						for (int x = 0; x < level.width(); x++)
						{
							int dx = x - tankX;
							int dy = y - tankY;

							if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1 &&
									!(tankDirection.isVertical() && dx != 0 && dy == tankDirection.dy ||
											tankDirection.isHorisontal() && dx == tankDirection.dx && dy != 0))
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

					level.erase(tank);

					for (int y = 0; y < level.height(); y++)
					{
						for (int x = 0; x < level.width(); x++)
						{
							assertNotNull("Null cell at (" + y + ';' + x + ") ", level.at(x, y));
							assertEquals("Wrong cell type at (" + x + ';' + y + ").", Cell.Type.GROUND, level.at(x, y).type);
						}
					}
				}
			}
		}
	}

	@Test
	public void testHeight()
	{
		for (int h = 0; h < 100; h++)
		{
			Field field = new Field(1, h);

			assertEquals("Wrong height.", h, field.height());
		}
	}

	@Test
	public void testWidth()
	{
		for (int w = 0; w < 100; w++)
		{
			Field field = new Field(w, 0);

			assertEquals("Wrong width.", 0, field.width());
		}

		for (int w = 0; w < 100; w++)
		{
			Field field = new Field(w, 1);

			assertEquals("Wrong width.", w, field.width());
		}
	}

	@Test
	public void testCorrectAt()
	{
		Field field = new Field(2, 100);

		for (int y = 0; y < field.height(); y++)
		{
			for (int x = 0; x < field.width(); x++)
			{
				Cell result = null;

				try
				{
					result = field.at(x, y);
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					fail("Exception while correct Field.at()");
				}

				assertNotNull("Null result at (" + y + ';' + x + ") ", result);
				/*assertEquals("Wrong cell x at (" + x + ';' + y + ").", x, result.getX());
				assertEquals("Wrong cell y at (" + x + ';' + y + ").", y, result.getY());*///signature change
			}
		}
	}

	@Test
	public void testIncorrectAt()
	{
		Field field = new Field(2, 100);

		for (int y = -1; y <= field.height(); y++)
		{
			try
			{
				field.at(-1, y);

				fail("No exception while incorrect Field.at()");
			}
			catch (ArrayIndexOutOfBoundsException e)
			{}

			try
			{
				field.at(field.width(), y);

				fail("No exception while incorrect Field.at()");
			}
			catch (ArrayIndexOutOfBoundsException e)
			{}
		}

		for (int x = -1; x <= field.width(); x++)
		{
			try
			{
				field.at(x, -1);

				fail("No exception while incorrect Field.at()");
			}
			catch (ArrayIndexOutOfBoundsException e)
			{}

			try
			{
				field.at(x, field.height());

				fail("No exception while incorrect Field.at()");
			}
			catch (ArrayIndexOutOfBoundsException e)
			{}
		}
	}

	@Test
	public void testReplace()
	{
		Field field = new Field(5, 5);


		Cell cell1 = field.at(0, 0);
		Cell cell2 = field.at(4, 3);

		field.move(0, 0, 4, 3);

		assertNotSame("Wasn't moved.", cell2, field.at(4, 3));
		assertSame("Wasn't moved.", cell1, field.at(4, 3));
		assertNotSame("Wasn't moved.", cell1, field.at(0, 0));
		assertNotNull(field.at(0, 0));
		assertEquals("New cell is not ground.", Cell.Type.GROUND, field.at(0, 0).type);
	}
}




















