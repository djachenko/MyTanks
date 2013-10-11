package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.model.activities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Level extends Field
{
	private Tank activeTank = null;
	private TaskPerformer performer;
	private Game game;
	private List<Tank> tanks = new LinkedList<>();

	public Level(int width, int height)
	{
		super(width, height);

		performer = new TaskPerformer();
	}

	public Level(String config) throws IOException, MapFormatException
	{
		this(config, null);
	}

	public Level(String config, Game game) throws IOException, MapFormatException
	{
		init(config);

		performer = new TaskPerformer();

		this.game = game;
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

				activeTank = new Tank(this, x, y, direction);
				draw(activeTank);
			}
		}
		catch (IllegalArgumentException e)
		{
			throw new MapFormatException("Unparseable parameters");
		}
	}

	public void moveTank(Direction direction)
	{
		performer.enqueue(new MoveTankTask(activeTank, direction));
	}

	public void shoot()
	{
		performer.enqueue(new ShootTask(activeTank));
	}

	public void setActiveTank(int index) throws MapFormatException
	{
		setActiveTank(tanks.get(index));
	}

	public void setActiveTank(Tank tank) throws MapFormatException
	{
		this.activeTank = tank;
	}

	public void addTank(Tank tank) throws MapFormatException
	{
		tanks.add(tank);
		draw(tank);
		game.addTank(tank);
	}

	public void removeTank(Tank tank)
	{
		tanks.remove(tank);
		performer.enqueue(new RemoveTankTask(tank, this));
	}

	public Tank getTank(int i)
	{
		return tanks.get(i);
	}

	public Tank getActiveTank()
	{
		return activeTank;
	}

	public boolean ableToSpawnBullet(int x, int y)
	{
		return x >= 0 && x < width() && y >= 0 && y < height() && !at(x, y).hasToBeWaited();
	}

	public void addBullet(int x, int y, Direction direction)
	{
		if (x >= 0 && x < width() && y >= 0 && y < height())
		{
			Bullet bullet = new Bullet(this, x, y, direction);
			draw(bullet);
			performer.enqueue(new MoveBulletTask(bullet));
			game.addBullet(bullet);
		}
	}

	public void removeBullet(Bullet bullet)
	{
		performer.enqueue(new RemoveBulletTask(bullet, this));
	}

	public boolean ableToHit(int x, int y)
	{
		return x >= 0 && x < width() && y >= 0 && y < height() && at(x, y).ableToHit() ||
				x == -1 || x == width() || y == -1 || y == height();
	}

	public void hit(int x, int y)
	{
		if (ableToHit(x, y) && (x != -1 && x != width() && (y != -1 && y != height())))
		{
			at(x, y).hit();
		}
	}
}
