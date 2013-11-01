package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.communication.ChooseLevelMessage;
import ru.nsu.fit.djachenko.mytanks.communication.CreatePlayersMessage;
import ru.nsu.fit.djachenko.mytanks.communication.StartGameMessage;

public class Client implements Runnable
{
	private Game game;

	private final MessageChannel<MessageToModel> channelToClient = new MessageChannel<>();
	private MessageChannel<MessageToModel> channelToGame;
	private MessageChannel<MessageToView> channelToView;

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

		game.addChannelToView(channelToView);
		channelToGame = game.getChannelToModel();

		game.start();
	}

	void createPlayers(GameMode mode)
	{
		switch (mode)
		{
			case SINGLE:
				channelToGame.set(new CreatePlayersMessage(channelToView, "Harry"));
				break;
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
		if (channelToGame != null)
		{
			channelToGame.set(message);
		}
	}

	public void accept(StartGameMessage message)
	{
		startLocalGame();
		createPlayers(message.getMode());

		channelToView.set(new ChooseLevelMessage());
	}
}
