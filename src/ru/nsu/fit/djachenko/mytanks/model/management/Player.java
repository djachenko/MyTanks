package ru.nsu.fit.djachenko.mytanks.model.management;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

public class Player
{
	private static int count = 0;
	private final int id = count++;

	private final Game game;

	private final MessageToModelFactory factory = MessageToModelFactory.getInstance();

	public Player(Game game)
	{
		this.game = game;
	}

	protected final int getId()
	{
		return id;
	}

	protected void notifyLevelStarted(Field.State state)
	{
		send(factory.getSpawnTankMessage(id));
	}

	protected void notifyTankSpawned(Tank.State state)
	{}

	protected void notifyTankHit()
	{
		send(factory.getSpawnTankMessage(id));
	}

	protected final void send(MessageToModel message)
	{
		message.handle(game);
	}
}
