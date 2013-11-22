package ru.nsu.fit.djachenko.mytanks.model;

class Player
{
	private static int count = 0;
	private final int id = count++;

	private final String name;

	public Player(String name)
	{
		this.name = name;
	}

	int getId()
	{
		return id;
	}

	String getName()
	{
		return name;
	}
}
