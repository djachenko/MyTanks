package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.model.activities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Level extends Field
{
	private Tank activeTank = null;
	private TaskPerformer performer;
	private List<Tank> tanks = new ArrayList<>();
	private List<Bullet> bullets = new LinkedList<>();
	private Map<Integer, Tank> tankMap = new HashMap<>();

	private List<MessageChannel<MessageToView>> channelsToView;
	private List<Player> players;

	public Level(int width, int height)
	{
	}

	public Level(String config)
	{
	}

	public Level(String config, List<MessageChannel<MessageToView>> channels, List<Player> players) throws IOException
	{
		init(config);

		performer = new TaskPerformer();

		this.channelsToView = channels;
		this.players = players;
	}

	public void init(String config) throws IOException
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
				//throw new MapFormatException("No tank info present");
			}

			int tankCount = Integer.parseInt(tanks);

			for (int i = 0; i < tankCount; i++)
			{
				String tankString = reader.readLine();

				if (tankString == null)
				{
					//throw new MapFormatException("Wrong tank count");
				}

				String[] tankParams = tankString.split(" ");

				if (tankParams.length != 3)
				{
					//throw new MapFormatException("Wrong tank description");
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
			//throw new MapFormatException("Unparseable parameters");
		}
	}

	public void moveTank(Direction direction)
	{
		performer.enqueue(new MoveTankTask(activeTank, direction));
	}

	private void moveTank(int id, Direction direction)
	{
		performer.enqueue(new MoveTankTask(getTank(id), direction));
	}

	private void shoot(int id)
	{
		performer.enqueue(new ShootTask(getTank(id)));
	}

	public void setActiveTank(int index)
	{
		setActiveTank(getTank(index));
	}

	public void setActiveTank(Tank tank)
	{
		this.activeTank = tank;
	}

	public void addTank(Tank tank)
	{
		tanks.add(tank);
		draw(tank);
		send(new DrawTankMessage(tank));
	}

	public void removeTank(Tank tank)
	{
		tanks.remove(tank);
		performer.enqueue(new RemoveTankTask(tank, this));
	}

	private Tank getTank(int id)
	{
		return tanks.get(id);
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
			bullets.add(bullet);
			draw(bullet);
			performer.enqueue(new MoveBulletTask(bullet));
			send(new DrawBulletMessage(bullet));
		}
	}

	public void removeBullet(Bullet bullet)
	{
		bullets.remove(bullet);
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

	public Iterable<Bullet> getBullets()
	{
		return bullets;
	}

	public Iterable<Tank> getTanks()
	{
		return tanks;
	}

	private void send(MessageToView message)
	{
		for (MessageChannel<MessageToView> channel : channelsToView)
		{
			channel.set(message);
		}
	}

	public void accept(MessageToModel message)
	{
	}

	public void accept(MoveTankMessage message)
	{
		moveTank(message.getPlayerId(), message.getDirection());
	}

	public void accept(ShootMessage message)
	{
		shoot(message.getId());
	}
}
