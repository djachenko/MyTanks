package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.model.activities.*;

import java.io.IOException;
import java.util.*;

public class Level extends Field
{
	private TaskPerformer performer;
	private Map<Integer, Tank> tankMap = new HashMap<>();
	private List<Bullet> bullets = new LinkedList<>();

	private List<MessageChannel<MessageToView>> channelsToView;
	private List<Player> players;

	private Map<Integer, Integer> resolveIdMap = new HashMap<>();

	public Level(String config, List<MessageChannel<MessageToView>> channels, List<Player> players) throws IOException
	{
		super(config);

		performer = new TaskPerformer();

		this.channelsToView = channels;
		this.players = players;
	}

	private void moveTank(int id, Direction direction)
	{
		Tank tank = getTank(id);

		if (tank != null)
		{
			performer.enqueue(new MoveTankTask(tank, direction));
		}
	}

	private void shoot(int id)
	{
		performer.enqueue(new ShootTask(getTank(id)));
	}

	public void add(Tank tank)
	{
		tankMap.put(tank.getId(), tank);
		draw(tank);
		send(new DrawTankMessage(tank));
	}

	public void add(Tank tank, int playerId)
	{
		int tankId = tank.getId();

		tankMap.put(tankId, tank);
		resolveIdMap.put(playerId, tankId);

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

	void remove(Tank tank)
	{
		tankMap.remove(tank.getId());
		performer.enqueue(new RemoveTankTask(tank, this));
	}

	void remove(Bullet bullet)
	{
		bullets.remove(bullet);
		performer.enqueue(new RemoveBulletTask(bullet, this));
	}

	private Tank getTank(int id)
	{
		return tankMap.get(id);
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

	private void spawnTank(int playerId)
	{
		Tank tank = new Tank(this, -1, -1, null);

		tankMap.put(tank.getId(), tank);

		send(new DrawTankMessage(tank));
	    resolveIdMap.put(playerId, tank.getId());
	}

	public Iterable<Bullet> getBullets()
	{
		return bullets;
	}

	public Iterable<Tank> getTanks()
	{
		return tankMap.values();
	}

	private int resolveTankId(int id)
	{
		Integer result = resolveIdMap.get(id);

		if (result == null)
		{
			return -1;
		}
		else
		{
			return result;
		}
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
		//throw
	}

	public void accept(MoveTankMessage message)
	{
		//int tankId = resolveTankId(message.getPlayerId());
		//moveTank(tankId, message.getDirection());//moveTank(resolveTankId(message.getPlayerId()), message.getDirection());

		moveTank(message.getPlayerId(), message.getDirection());
	}

	public void accept(ShootMessage message)
	{
		shoot(message.getId());
	}
}
