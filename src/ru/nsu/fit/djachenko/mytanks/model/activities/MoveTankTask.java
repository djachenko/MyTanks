package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

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
		tank.move(direction);
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
