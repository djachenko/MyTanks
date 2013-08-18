package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Field;

public class GroundCell extends Cell
{
	public GroundCell(Field field, int x, int y)
	{
		super(Type.GROUND, field, x, y);
	}

	@Override
	public boolean ableToMove(Direction dir)
	{
		return true;
	}

	@Override
	public void move(Direction dir)
	{}

	@Override
	public boolean ableToReplace()
	{
		return true;
	}
}
