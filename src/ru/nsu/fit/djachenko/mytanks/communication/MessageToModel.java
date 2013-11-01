package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.Client;

public abstract class MessageToModel implements Message
{
	public void handle(Game game)
	{
		game.accept(this);
	}

	public void handle(Client client)
	{
		client.accept(this);
	}

	public void handle(Level level)
	{
		level.accept(this);
	}
}
