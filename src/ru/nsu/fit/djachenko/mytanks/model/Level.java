package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.model.activities.*;

import java.io.IOException;
import java.util.*;

public class Level extends Field
{
	private TaskPerformer performer;
	private List<Tank> tanks = new ArrayList<>();
	private List<Bullet> bullets = new LinkedList<>();
	private Map<Integer, Tank> tankMap = new HashMap<>();

	private List<MessageChannel<MessageToView>> channelsToView;
	private List<Player> players;

	public Level(String config, List<MessageChannel<MessageToView>> channels, List<Player> players) throws IOException
	{
		super(config);

		performer = new TaskPerformer();

		this.channelsToView = channels;
		this.players = players;
	}

	private void moveTank(int id, Direction direction)
	{
		performer.enqueue(new MoveTankTask(getTank(id), direction));
		print();
	}

	private void shoot(int id)
	{
		performer.enqueue(new ShootTask(getTank(id)));
	}

	public void add(Tank tank)
	{
		tanks.add(tank);
		draw(tank);
		send(new DrawTankMessage(tank));
	}

	private void add(Bullet bullet)
	{
		bullets.add(bullet);
		draw(bullet);
		performer.enqueue(new MoveBulletTask(bullet));
		send(new DrawBulletMessage(bullet));
	}

	private void add(Tank tank, int id)
	{
		tankMap.put(id, tank);
	}

	void remove(Tank tank)
	{
		tanks.remove(tank);
		performer.enqueue(new RemoveTankTask(tank, this));
	}

	void remove(Bullet bullet)
	{
		bullets.remove(bullet);
		performer.enqueue(new RemoveBulletTask(bullet, this));
	}

	private void removeTank(int id)
	{
		tankMap.remove(id);
	}

	private Tank getTank(int id)
	{
		return tanks.get(id);
	}

	boolean ableToSpawnBullet(int x, int y)
	{
		return x >= 0 && x < width() && y >= 0 && y < height() && !at(x, y).hasToBeWaited();
	}

	void spawnBullet(int x, int y, Direction direction)
	{
		if (x >= 0 && x < width() && y >= 0 && y < height())
		{
			add(new Bullet(this, x, y, direction));
		}
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
