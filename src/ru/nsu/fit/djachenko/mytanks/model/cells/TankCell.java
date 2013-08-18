package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Field;

public class TankCell extends Cell
{
	public TankCell(Field field, int x, int y)
	{
		super(Type.TANK, field, x, y);
	}

	@Override
	public boolean ableToReplace()
	{
		return false;
	}
}
