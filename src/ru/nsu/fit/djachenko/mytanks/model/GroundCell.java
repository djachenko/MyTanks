package ru.nsu.fit.djachenko.mytanks.model;

public class GroundCell extends Cell
{
	public GroundCell(Field field, int x, int y)
	{
		super(Type.FLOOR, field, x, y);
	}

	@Override
	public boolean ableToReplace()
	{
		return true;
	}
}
