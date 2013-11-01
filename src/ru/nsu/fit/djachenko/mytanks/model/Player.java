package ru.nsu.fit.djachenko.mytanks.model;

public class Player
{
	private static int count = 0;
	private final int id = count++;

	private String name;

	public Player(String name)
	{
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}
}
