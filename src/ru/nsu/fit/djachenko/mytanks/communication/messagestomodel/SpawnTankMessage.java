package ru.nsu.fit.djachenko.mytanks.communication.messagestomodel;

import ru.nsu.fit.djachenko.mytanks.model.entries.Level;

public class SpawnTankMessage extends MessageToModel
{
	private final int id;

	SpawnTankMessage(int id)
	{
		this.id = id;
	}

	@Override
	public void handle(Level level)
	{
		level.accept(this);
	}

	public int getId()
	{
		return id;
	}
}
