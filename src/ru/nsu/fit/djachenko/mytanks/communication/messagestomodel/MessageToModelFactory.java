package ru.nsu.fit.djachenko.mytanks.communication.messagestomodel;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.management.GameMode;

import java.util.HashMap;
import java.util.Map;

public class MessageToModelFactory
{
	private static MessageToModelFactory instance = new MessageToModelFactory();

	private Map<Integer, MessageToModel> shootMessages = new HashMap<>();
	private Map<Integer, MessageToModel> spawnTankMessages = new HashMap<>();
	private Map<Integer, Map<Direction, MessageToModel>> moveTankMessages = new HashMap<>();

	public static MessageToModelFactory getInstance()
	{
		return instance;
	}

	private MessageToModelFactory()
	{}

	public synchronized MessageToModel getMoveTankMessage(int playerId, Direction direction)
	{
		if (!moveTankMessages.containsKey(playerId))
		{
			Map<Direction, MessageToModel> temp = new HashMap<>(4);

			for (Direction dir : Direction.values())
			{
				temp.put(dir, new MoveTankMessage(playerId, dir));
			}

			moveTankMessages.put(playerId, temp);
		}

		return moveTankMessages.get(playerId).get(direction);
	}

	public synchronized MessageToModel getShootMessage(int playerId)
	{
		if (!shootMessages.containsKey(playerId))
		{
			shootMessages.put(playerId, new ShootMessage(playerId));
		}

		return shootMessages.get(playerId);
	}

	public synchronized MessageToModel getSpawnTankMessage(int playerId)
	{
		if (!spawnTankMessages.containsKey(playerId))
		{
			spawnTankMessages.put(playerId, new SpawnTankMessage(playerId));
		}

		return spawnTankMessages.get(playerId);
	}

	public MessageToModel getStartGameMessage(GameMode mode)
	{
		return new StartGameMessage(mode);
	}

	public MessageToModel getStartLevelMessage(int levelId)
	{
		return new StartLevelMessage(levelId);
	}
}
