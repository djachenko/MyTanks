package ru.nsu.fit.djachenko.mytanks.communication.messagestomodel;

import ru.nsu.fit.djachenko.mytanks.model.Client;
import ru.nsu.fit.djachenko.mytanks.model.GameMode;

public class StartGameMessage extends MessageToModel
{
	private final GameMode mode;

	public StartGameMessage(GameMode mode)
	{
		this.mode = mode;
	}

	@Override
	public void handle(Client client)
	{
		client.accept(this);
	}

	public GameMode getMode()
	{
		return mode;
	}
}
