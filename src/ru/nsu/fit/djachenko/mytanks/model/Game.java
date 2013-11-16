package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.controller.AIController;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game extends Thread
{
	private final MessageChannel<MessageToModel> channelToModel = new MessageChannel<>();
	private List<MessageChannel<MessageToView>> channelsToView = new LinkedList<>();

	private Level currentLevel = null;
	private LevelHolder holder = new LevelHolder();
	private int pauses = 0;

	private Map<MessageChannel<MessageToView>, int[]> playerIds = new HashMap<>();
	private List<AIController> aiControllers = new LinkedList<>();
	private List<Player> players = new LinkedList<>();

	@Override
	public void run()
	{
		while (true)
		{
			synchronized (this)
			{
				while (isPaused())
				{
					try
					{
						wait();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}

			MessageToModel message = channelToModel.get();

			message.handle(this);
		}
	}

	private void startLevel(int index) throws IOException
	{
		currentLevel = new Level(holder.getLevel(index), this);

		for (MessageChannel<MessageToView> channelToView : channelsToView)
		{
			int[] ids = playerIds.get(channelToView);
			channelToView.set(new LevelStartedMessage(currentLevel, ids));
		}

		currentLevel.add(new Tank(currentLevel, 20, 13, Direction.UP));
		currentLevel.add(new Tank(currentLevel, 4, 13, Direction.UP));
	}

	void send(MessageToView messageToView)
	{
		synchronized (this)
		{
			while (isPaused())
			{
				try
				{
					wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

			for (MessageChannel<MessageToView> channelToView : channelsToView)
			{
				channelToView.set(messageToView);
			}
		}
	}

	public MessageChannel<MessageToModel> getChannelToModel()
	{
		return channelToModel;
	}

	public void addChannelToView(MessageChannel<MessageToView> channel)
	{
		channelsToView.add(channel);
	}

	public synchronized void pause()
	{
		pauses++;
	}

	public synchronized void unpause()
	{
		if (pauses > 0)
		{
			pauses--;
		}

		if (pauses == 0)
		{
			notifyAll();
		}
	}

	private synchronized boolean isPaused()
	{
		return pauses > 0;
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

	public void accept(CreatePlayersMessage message)
	{
		String[] names = message.getNames();

		switch (message.getMode())
		{
			case SINGLE:
			{
				Player player = new Player(names[0]);

				int[] ids = new int[2];
				ids[0] = player.getId();
				ids[1] = player.getId();

				players.add(player);

				playerIds.put(message.getChannel(), ids);

				Player ai = new Player("AI");
				players.add(ai);

				aiControllers.add(new AIController(getChannelToModel(), ai.getId()));

				break;
			}
			case SHARED:
			{
				Player player1 = new Player(names[0]);
				Player player2 = new Player(names[1]);

				int[] ids = new int[2];
				ids[0] = player1.getId();
				ids[1] = player2.getId();

				playerIds.put(message.getChannel(), ids);

				players.add(player1);
				players.add(player1);

				break;
			}
		}
	}
}
