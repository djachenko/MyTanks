package ru.nsu.fit.djachenko.mytanks.model.management;

import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.StartLevelMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToViewFactory;
import ru.nsu.fit.djachenko.mytanks.model.entries.Level;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;
import ru.nsu.fit.djachenko.mytanks.model.entries.activities.TaskPerformer;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.AI;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.Runner;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game implements MessageAcceptor
{
	private final List<Client> clients = new LinkedList<>();

	private Level currentLevel = null;
	private final LevelHolder holder = new LevelHolder();

	private final Map<Integer, Player> players = new HashMap<>();
	private final List<AI> ais = new LinkedList<>();

	private TaskPerformer aiTaskPerformer = new TaskPerformer();

	private void startLevel(int index) throws IOException
	{
		currentLevel = new Level(holder.getLevel(index), this);

		for (AI ai : ais)
		{
			aiTaskPerformer.enqueue(ai);
		}

		send(MessageToViewFactory.getInstance().getLevelStartedMessage(currentLevel.getState()));

		for (Player player : players.values())
		{
			player.notifyLevelStarted(currentLevel.getState());
		}
	}

	void send(MessageToView messageToView)
	{
		for (Client client : clients)
		{
			messageToView.handle(client);
		}
	}

	public void notifyTankSpawned(int playerId, Tank.State state)
	{
		players.get(playerId).notifyTankSpawned(state);
	}

	public void notifyTankHit(int playerId)
	{
		players.get(playerId).notifyTankHit();
	}

	public synchronized void register(Client client)
	{
		clients.add(client);
	}

	public int registerPlayer()
	{
		Player player = new Player(this);

		addPlayer(player);

		return player.getId();
	}

	public synchronized void registerAI()
	{
		AI ai = new Runner(this);

		addPlayer(ai);
		ais.add(ai);
	}

	private synchronized void addPlayer(Player player)
	{
		players.put(player.getId(), player);
	}

	public void accept(MessageToView message)
	{
		send(message);
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
