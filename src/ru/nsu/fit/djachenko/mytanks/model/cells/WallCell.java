package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

class WallCell extends Cell
{
	WallCell()
	{
		super(Type.WALL);
	}

	@Override
	public boolean ableToMove(Direction dir, int depth)
	{
		return false;
	}

	@Override
	public void move(Direction dir, int depth)
	{
	}

	@Override
	public void move(int toX, int toY)
	{
	}

	@Override
	public boolean ableToReplace()
	{
		return false;
	}

	@Override
	public boolean ableToHit()
	{
		return true;
	}

	@Override
	public void hit()
	{
	}

	@Override
	public boolean hasToBeWaited()
	{
		return true;
	}
}
