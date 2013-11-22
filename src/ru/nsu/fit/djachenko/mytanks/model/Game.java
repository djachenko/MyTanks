package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.controller.AIController;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game
{
	private List<Client> clients = new LinkedList<>();

	private Level currentLevel = null;
	private LevelHolder holder = new LevelHolder();

	private List<Player> players = new LinkedList<>();

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
