package ru.nsu.fit.djachenko.mytanks.model;

class SpawnPoint
{
	private final int x;
	private final int y;

	SpawnPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
