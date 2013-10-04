package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Tank;
import ru.nsu.fit.djachenko.mytanks.model.UnexpectedSituationException;

public class MoveTankTask implements Task
{
	private final Tank tank;
	private final Direction direction;

	public MoveTankTask(Tank tank, Direction direction)
	{
		this.tank = tank;
		this.direction = direction;
	}

	@Override
	public void execute(int iteration)
	{
		try
		{
			tank.move(direction);
		}
		catch (UnexpectedSituationException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
