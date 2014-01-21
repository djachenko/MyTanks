package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.*;

public class Client implements Runnable
{
	private Game game;

	private final MessageChannel<MessageToModel> channelToClient = new MessageChannel<>();
	private MessageChannel<MessageToView> channelToView;

	private int wasdId;
	private int arrowsId;

	public MessageChannel<MessageToModel> getChannelToClient()
	{
		return channelToClient;
	}

	public void setChannelToView(MessageChannel<MessageToView> channel)
	{
		channelToView = channel;
	}

	private void startLocalGame()
	{
		game = new Game();

		game.register(this);
	}

	void createPlayers(GameMode mode)
	{
		switch (mode)
		{
			case SINGLE:
				int id = game.registerPlayer();
				//game.registerAI();

				wasdId = id;
				arrowsId = id;
				break;
			case SHARED:
				wasdId = game.registerPlayer();
				arrowsId = game.registerPlayer();
		}
	}

	@Override
	public void run()
	{
		for ( ; ; )
		{
			MessageToModel message = channelToClient.get();

			message.handle(this);
		}
	}

	public void accept(MessageToModel message)
	{
		message.handle(game);
	}

	public void accept(MessageToView message)
	{
		channelToView.set(message);
	}

	public void accept(StartGameMessage message)
	{
		startLocalGame();
		createPlayers(message.getMode());

		channelToView.set(new ChooseLevelMessage());
	}

	public void accept(LevelStartedMessage message)
	{
		message.setArrowsId(arrowsId);
		message.setWasdId(wasdId);

		channelToView.set(message);
	}
}
