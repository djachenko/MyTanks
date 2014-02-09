package ru.nsu.fit.djachenko.mytanks.model.entries;

import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MoveTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.ShootMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.SpawnTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToViewFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;
import ru.nsu.fit.djachenko.mytanks.model.entries.activities.*;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.cells.SpawnPoint;

import java.io.IOException;
import java.util.*;

public class Level extends Field implements MessageAcceptor
{
	private final Game game;

	private TaskPerformer performer;
	private final Map<Integer, Tank> tankMap = new HashMap<>();

	private final Map<Integer, Integer> playerToTank = new HashMap<>();
	private final Map<Integer, Integer> tankToPlayer = new HashMap<>();

	private Set<Integer> players = new HashSet<>();

	private List<SpawnPoint> spawnPoints;
	private final Random random = new Random();

	private MessageToViewFactory factory = MessageToViewFactory.getInstance();

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

		accept(factory.getDrawBulletMessage(bullet.getX(), bullet.getY(), bullet.getDirection(), bullet.getId()));
	}

	public void remove(Tank tank)
	{
		tankMap.remove(tank.getId());
		erase(tank);
		accept(factory.getTankRemovedMessage(tank.getId()));

		int playerId = resolvePlayerByTank(tank.getId());

		tankToPlayer.remove(tank.getId());
		playerToTank.remove(playerId);

		game.notifyTankHit(playerId);
	}

	void remove(Bullet bullet)
	{
		erase(bullet);
		accept(factory.getBulletRemovedMessage(bullet.getId()));
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

		accept(factory.getDrawTankMessage(tank.getX(), tank.getY(), tank.getDirection(), tank.getId(), playerId));
		game.notifyTankSpawned(playerId, tank.getState());
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

	@Override
	public void accept(MessageToView message)
	{
		message.handle(game);
	}

	@Override
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
		if (!players.contains(message.getId()))
		{
			spawnTank(message.getId());
			players.add(message.getId());
		}
		else
		{
			performer.enqueue(new SpawnTankTask(this, message.getId()));
		}
	}
}