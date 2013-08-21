package ru.nsu.fit.djachenko.mytanks.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Level extends Field
{
	private Tank tank = null;

	public Level(int width, int height)
	{
		super(width, height);
	}

	public Level(String config) throws IOException, MapFormatException
	{
		init(config);
	}

	public void init(String config) throws IOException, MapFormatException
	{
		super.init(config);

		try (BufferedReader reader = new BufferedReader(new FileReader(config)))
		{
			int skipCount = Integer.parseInt(reader.readLine().split(" ")[1]);//it's ok, because no such in init

			for (int i = 0; i < skipCount; i++)
			{
				reader.readLine();
			}

			String tanks = reader.readLine();

			if (tanks == null)
			{
				throw new MapFormatException("No tank info present");
			}

			int tankCount = Integer.parseInt(tanks);

			for (int i = 0; i < tankCount; i++)
			{
				String tankString = reader.readLine();

				if (tankString == null)
				{
					throw new MapFormatException("Wrong tank count");
				}

				String[] tankParams = tankString.split(" ");

				if (tankParams.length != 3)
				{
					throw new MapFormatException("Wrong tank description");
				}

				int x = Integer.parseInt(tankParams[0]);
				int y = Integer.parseInt(tankParams[1]);
				Direction direction = Direction.valueOf(tankParams[2].toUpperCase());

				tank = new Tank(this, x, y, true, direction);
				drawTank(tank);
			}
		}
		catch (IllegalArgumentException e)
		{
			throw new MapFormatException("Unparseable parameters");
		}
	}

	public void moveTank(Direction direction) throws UnexpectedSituationException
	{
		tank.move(direction);
	}

	public void setTank(Tank tank) throws MapFormatException
	{
		if (this.tank != null)
		{
			eraseTank(this.tank);
		}

		this.tank = tank;
		drawTank(tank);
	}

	Tank getTank()
	{
		return tank;
	}
}
