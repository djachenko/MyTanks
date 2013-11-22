package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.model.activities.*;
import ru.nsu.fit.djachenko.mytanks.communication.BulletRemovedMessage;
import ru.nsu.fit.djachenko.mytanks.communication.TankRemovedMessage;

import java.io.IOException;
import java.util.*;

public class Level extends Field
{
	private final Game game;

	private TaskPerformer performer;
	private Map<Integer, Tank> tankMap = new HashMap<>();
	private List<Bullet> bullets = new LinkedList<>();

	private Map<Integer, Integer> playerToTank = new HashMap<>();
	private Map<Integer, Integer> tankToPlayer = new HashMap<>();

	private List<SpawnPoint> spawnPoints;
	Random random = new Random();

	public Level(String config, Game game) throws IOException
	{
		super(config);

		this.game = game;

		performer = new TaskPerformer();

		spawnPoints = scanForSpawnPoints();

		for (SpawnPoint spawnPoint : spawnPoints)
		{
			System.out.println("(" + spawnPoint.getX() + ';' + spawnPoint.getY() + ')');
		}
	}

	private void moveTank(int id, Direction direction)
	{
		Tank tank = getTank(id);

		if (tank != null)
		{
			performer.enqueue(new MoveTankTask(tank, direction));
		}
	}

	public void hitTank(Tank tank)
	{
		performer.enqueue(new RemoveTankTask(tank, this));
	}

	private void shoot(int id)
	{
		Tank tank = getTank(id);

		if (tank != null)
		{
			performer.enqueue(new ShootTask(tank));
		}
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
		playerToTank.put(playerId, tankId);
		tankToPlayer.put(tankId, playerId);

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

	public void remove(Tank tank)
	{
		tankMap.remove(tank.getId());
		erase(tank);
		send(new TankRemovedMessage(tank.getId()));

		int playerId = tankToPlayer.get(tank.getId());

		tankToPlayer.remove(tank.getId());
		playerToTank.remove(playerId);

		performer.enqueue(new SpawnTankTask(this, random.nextDouble() % 15, playerId));
	}

	void remove(Bullet bullet)
	{
		bullets.remove(bullet);
		erase(bullet);
		send(new BulletRemovedMessage(bullet.getId()));
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

	public void spawnTank(int playerId)
	{
		SpawnPoint point;

		do
		{
			point = spawnPoints.get(random.nextInt(spawnPoints.size()));
		}
		while (!check(point));

		Direction direction = Direction.values()[random.nextInt(Direction.values().length)];

		Tank tank = new Tank(this, point.getX(), point.getY(), direction);

		tankMap.put(tank.getId(), tank);
		playerToTank.put(playerId, tank.getId());
		tankToPlayer.put(tank.getId(), playerId);

		draw(tank);

		send(new DrawTankMessage(tank));
	}

	private int resolveTankId(int id)
	{
		Integer result = playerToTank.get(id);

		if (result == null)
		{
			return -1;
		}
		else
		{
			return result;
		}
	}

	void send(MessageToView message)
	{
		game.send(message);
	}

	public void accept(MessageToModel message)
	{
		//throw
	}

	public void accept(MoveTankMessage message)
	{
		moveTank(resolveTankId(message.getPlayerId()), message.getDirection());
	}

	public void accept(ShootMessage message)
	{
		shoot(message.getId());
	}
}
