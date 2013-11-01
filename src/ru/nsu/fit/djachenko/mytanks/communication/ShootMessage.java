package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Level;

public class ShootMessage extends MessageToModel
{
	private int id;

	public ShootMessage(int id)
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
