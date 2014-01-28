package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

class GroundCell extends Cell
{
	GroundCell()
	{
		super(Type.GROUND);
	}

	@Override
	public boolean ableToMove(Direction dir, int depth)
	{
		return true;
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
		return true;
	}

	@Override
	public boolean ableToHit()
	{
		return false;
	}

	@Override
	public void hit()
	{
	}

	@Override
	public boolean hasToBeWaited()
	{
		return false;
	}
}
