package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.model.activities.MoveBulletTask;
import ru.nsu.fit.djachenko.mytanks.model.activities.MoveTankTask;
import ru.nsu.fit.djachenko.mytanks.model.activities.TaskPerformer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Level extends Field
{
	private Tank tank = null;
	private TaskPerformer performer;

	public Level(int width, int height)
	{
		super(width, height);

		performer = new TaskPerformer(this);
	}

	public Level(String config) throws IOException, MapFormatException
	{
		init(config);

		performer = new TaskPerformer(this);
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

				tank = new Tank(this, x, y, direction);
				draw(tank);
			}
		}
		catch (IllegalArgumentException e)
		{
			throw new MapFormatException("Unparseable parameters");
		}
	}

	public void moveTank(Direction direction) throws UnexpectedSituationException
	{
		//tank.move(direction);

		performer.enqueue(new MoveTankTask(tank, direction));
	}

	public void shoot()
	{
		tank.shoot();
	}

	public void setTank(Tank tank) throws MapFormatException
	{
		if (this.tank != null)
		{
			erase(this.tank);
		}

		this.tank = tank;
		draw(tank);
	}

	Tank getTank()
	{
		return tank;
	}

	public void spawnBullet(int x, int y, Direction direction)
	{
		Bullet bullet = new Bullet(this, x, y, direction);
		draw(bullet);
		performer.enqueue(new MoveBulletTask(bullet));
	}

	public boolean ableToHit(int x, int y)
	{
		return x >= 0 && x < width() && y >= 0 && y < height() && at(x, y).ableToHit() ||
				x == -1 || x == width() || y == -1 || y == height();
	}

	public void hit(int x, int y)
	{
		if (ableToHit(x, y) && (x != -1 && x != width() && (y != -1 || y != height())))
		{
			at(x, y).hit();
		}
	}
}
