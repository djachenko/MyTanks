package ru.nsu.fit.djachenko.mytanks.model;

public class WallCell extends Cell
{

	public WallCell(Field field, int x, int y)
	{
		super(Type.WALL, field, x, y);
	}
}
