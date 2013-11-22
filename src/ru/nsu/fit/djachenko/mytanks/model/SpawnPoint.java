package ru.nsu.fit.djachenko.mytanks.model;

public class SpawnPoint
{
	private int x;
	private int y;

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
