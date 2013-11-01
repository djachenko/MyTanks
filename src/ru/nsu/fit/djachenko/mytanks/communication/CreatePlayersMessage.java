package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.model.GameMode;

public class CreatePlayersMessage extends MessageToModel
{
	private MessageChannel<MessageToView> channel;
	private GameMode mode;

	private String[] names;

	private CreatePlayersMessage(MessageChannel<MessageToView> channel, GameMode mode)
	{
		this.channel = channel;
		this.mode = mode;
	}

	public CreatePlayersMessage(MessageChannel<MessageToView> channel, String name)
	{
		this(channel, GameMode.SINGLE);

		names = new String[1];
		names[0] = name;
	}

	CreatePlayersMessage(MessageChannel<MessageToView> channel, String leftName, String rightName)
	{
		this(channel, GameMode.SHARED);

		names = new String[2];
		names[0] = leftName;
		names[1] = rightName;
	}

	@Override
	public void handle(Game game)
	{
		game.accept(this);
	}

	public MessageChannel<MessageToView> getChannel()
	{
		return channel;
	}

	public GameMode getMode()
	{
		return mode;
	}

	public String[] getNames()
	{
		return names;
	}
}
