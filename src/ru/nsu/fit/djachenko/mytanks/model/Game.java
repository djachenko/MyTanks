package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.LevelStartedMessage;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.communication.StartLevelMessage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Game
{
	private final List<Client> clients = new LinkedList<>();

	private Level currentLevel = null;
	private final LevelHolder holder = new LevelHolder();

	private final List<Player> players = new LinkedList<>();

	private void startLevel(int index) throws IOException
	{
		currentLevel = new Level(holder.getLevel(index), this);

		send(new LevelStartedMessage(currentLevel));


		for (Player player : players)
		{
			currentLevel.spawnTank(player.getId());
		}
	}

	void send(MessageToView messageToView)
	{
		for (Client client : clients)
		{
			messageToView.handle(client);
		}
	}

	public void register(Client client)
	{
		clients.add(client);
	}

	public int registerPlayer()
	{
		Player player = new Player(null);

		players.add(player);

		return player.getId();
	}

	public void accept(MessageToModel message)
	{
		if (currentLevel != null)
		{
			message.handle(currentLevel);
		}
	}

	public void accept(StartLevelMessage message)
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
