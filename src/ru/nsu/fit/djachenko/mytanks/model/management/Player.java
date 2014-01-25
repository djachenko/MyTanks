package ru.nsu.fit.djachenko.mytanks.model.management;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.SpawnTankMessage;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cellls.Field;

public class Player
{
	private static int count = 0;
	private final int id = count++;

	private final Game game;

	public Player(Game game)
	{
		this.game = game;
	}

	protected int getId()
	{
		return id;
	}

	protected void notifyLevelStarted(Field.State state)
	{
		send(new SpawnTankMessage(id));
	}

	protected void notifyTankSpawned(int x, int y, Direction direction)
	{}

	protected void notifyTankHit()
	{
		send(new SpawnTankMessage(id));
	}

	protected void send(MessageToModel message)
	{
		message.handle(game);
	}
}
