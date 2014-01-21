package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.LevelStartedMessage;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.communication.StartLevelMessage;
import ru.nsu.fit.djachenko.mytanks.model.activities.TaskPerformer;
import ru.nsu.fit.djachenko.mytanks.model.ai.AI;
import ru.nsu.fit.djachenko.mytanks.model.ai.RandomMover;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game
{
	private final List<Client> clients = new LinkedList<>();

	private Level currentLevel = null;
	private final LevelHolder holder = new LevelHolder();

	private final Map<Integer, Player> players = new HashMap<>();

	private TaskPerformer aiTaskPerformer = null;

	private int pendingAIsCount = 0;

	private void startLevel(int index) throws IOException
	{
		currentLevel = new Level(holder.getLevel(index), this);

		aiTaskPerformer = new TaskPerformer();

		for (int i = 0; i < pendingAIsCount; i++)
		{
			AI ai = new RandomMover(currentLevel);

			addPlayer(ai);
			aiTaskPerformer.enqueue(ai);
		}

		send(new LevelStartedMessage(currentLevel));

		for (Player player : players.values())
		{
			player.notifyLevelStarted(currentLevel.getState());
			currentLevel.spawnTank(player.getId());
		}

		pendingAIsCount = 0;
	}

	void send(MessageToView messageToView)
	{
		for (Client client : clients)
		{
			messageToView.handle(client);
		}
	}

	void notifyTankSpawned(int playerId, int x, int y, Direction direction)
	{
		players.get(playerId).notifyTankSpawned(x, y, direction);
	}

	void notifyTankHit(int playerId)
	{
		players.get(playerId).notifyTankHit();
	}

	public synchronized void register(Client client)
	{
		clients.add(client);
	}

	public int registerPlayer()
	{
		Player player = new Player();

		addPlayer(player);

		return player.getId();
	}

	public synchronized void registerAI()
	{
		pendingAIsCount++;
	}

	private synchronized void addPlayer(Player player)
	{
		players.put(player.getId(), player);
	}

	public void accept(MessageToModel message)
	{
		if (currentLevel != null)
		{
			message.handle(currentLevel);
		}
	}

	public synchronized void accept(StartLevelMessage message)
	{
		try
		{
			startLevel(message.getLevelId());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
