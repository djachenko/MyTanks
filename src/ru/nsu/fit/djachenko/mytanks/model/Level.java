package ru.nsu.fit.djachenko.mytanks.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Level extends Field
{
	private Tank tank;

	public Level()
	{}

	public Level(String config) throws IOException, MapFormatException
	{
		init(config);
	}

	public void init(String config) throws IOException, MapFormatException
	{
		File mapFile = new File(config);

		super.init(mapFile);

		try (BufferedReader reader = new BufferedReader(new FileReader(mapFile)))
		{
			int skipCount = Integer.parseInt(reader.readLine().split(" ")[1]);

			for (int i = 0; i < skipCount; i++)
			{
				reader.readLine();
			}

			int tankCount = Integer.parseInt(reader.readLine());

			for (int i = 0; i < tankCount; i++)
			{
				String[] tankParams = reader.readLine().split(" ");

				if (tankParams.length != 3)
				{
					throw new MapFormatException("params");
				}

				int x = Integer.parseInt(tankParams[0]);
				int y = Integer.parseInt(tankParams[1]);
				Direction direction = Direction.valueOf(tankParams[2].toUpperCase());

				tank = new Tank(this, x, y, true, direction);
				drawTank(tank);
			}
		}
	}

	void moveTank(Direction direction) throws UnexpectedSituationException
	{
		tank.move(direction);
	}
}
