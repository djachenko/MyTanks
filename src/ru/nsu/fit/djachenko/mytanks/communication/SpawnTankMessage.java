package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Level;

public class SpawnTankMessage extends MessageToModel
{
	private final int id;

	public SpawnTankMessage(int id)
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
