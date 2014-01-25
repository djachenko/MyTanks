package ru.nsu.fit.djachenko.mytanks.communication.messagestomodel;

import ru.nsu.fit.djachenko.mytanks.model.entries.Level;

public class ShootMessage extends MessageToModel
{
	private final int id;

	ShootMessage(int id)
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
