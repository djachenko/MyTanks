package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Field;

public class WallCell extends Cell
{
	public WallCell(Field field, int x, int y)
	{
		super(Type.WALL, field, x, y);
	}

	@Override
	public boolean ableToReplace()
	{
		return false;
	}
}
