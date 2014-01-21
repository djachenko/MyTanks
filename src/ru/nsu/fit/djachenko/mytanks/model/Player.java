package ru.nsu.fit.djachenko.mytanks.model;

public class Player
{
	private static int count = 0;
	private final int id = count++;

	protected int getId()
	{
		return id;
	}

	void notifyLevelStarted(Field.State state)
	{}
	void notifyTankSpawned(int x, int y, Direction direction)
	{}
	void notifyTankHit()
	{}
}
