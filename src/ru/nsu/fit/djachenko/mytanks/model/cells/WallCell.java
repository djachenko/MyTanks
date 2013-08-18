package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Field;

public class WallCell extends Cell
{
	public WallCell(Field field, int x, int y)
	{
		super(Type.WALL, field, x, y);
	}

	@Override
	public boolean ableToMove(Direction dir)
	{
		return false;
	}

	@Override
	public void move(Direction dir)
	{}

	@Override
	public boolean ableToReplace()
	{
		return false;
	}
}
