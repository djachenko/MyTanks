package ru.nsu.fit.djachenko.mytanks.model.ai;

import ru.nsu.fit.djachenko.mytanks.model.Level;

public class StraightMover extends AI
{
	private int tankX;
	private int tankY;

	StraightMover(Level level)
	{
		super(level);
	}

	@Override
	public void execute(int iteration)
	{

	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
