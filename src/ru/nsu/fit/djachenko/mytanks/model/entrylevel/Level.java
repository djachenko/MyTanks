package ru.nsu.fit.djachenko.mytanks.model.entrylevel;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MoveTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.ShootMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.SpawnTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.*;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.model.activities.*;
import ru.nsu.fit.djachenko.mytanks.model.entrylevel.celllevel.Field;
import ru.nsu.fit.djachenko.mytanks.model.entrylevel.celllevel.SpawnPoint;

import java.io.IOException;
import java.util.*;

public class Level extends Field
{
	private final Game game;

	private TaskPerformer performer;
	private final Map<Integer, Tank> tankMap = new HashMap<>();

	private final Map<Integer, Integer> playerToTank = new HashMap<>();
	private final Map<Integer, Integer> tankToPlayer = new HashMap<>();

	private List<SpawnPoint> spawnPoints;
	private final Random random = new Random();

	public Level(String config, Game game) throws IOException
	{
		super(config);

		this.game = game;

		performer = new TaskPerformer();

		spawnPoints = scanForSpawnPoints();
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

	private void add(Bullet bullet)
	{
		draw(bullet);
		performer.enqueue(new MoveBulletTask(bullet));
		send(new DrawBulletMessage(bullet));
	}

	public void remove(Tank tank)
	{
		tankMap.remove(tank.getId());
		erase(tank);
		send(new TankRemovedMessage(tank.getId()));

		int playerId = resolvePlayerByTank(tank.getId());

		tankToPlayer.remove(tank.getId());
		playerToTank.remove(playerId);

		game.notifyTankHit(playerId);
	}

	void remove(Bullet bullet)
	{
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
			if (ableToHit(x, y))
			{
				hit(x, y);
			}
			else
			{
				add(new Bullet(this, x, y, direction));
			}
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

		send(new DrawTankMessage(tank, playerId));
		game.notifyTankSpawned(playerId, tank.getX(), tank.getY(), tank.getDirection());
	}

	private int resolvePlayerByTank(int tankId)
	{
		Integer result = tankToPlayer.get(tankId);

		if (result == null)
		{
			return -1;
		}
		else
		{
			return result;
		}
	}

	private int resolveTankByPlayer(int playerId)
	{
		Integer result = playerToTank.get(playerId);

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
		moveTank(resolveTankByPlayer(message.getPlayerId()), message.getDirection());
	}

	public void accept(ShootMessage message)
	{
		shoot(resolveTankByPlayer(message.getId()));
	}

	public void accept(SpawnTankMessage message)
	{
		spawnTank(message.getId());
	}
}