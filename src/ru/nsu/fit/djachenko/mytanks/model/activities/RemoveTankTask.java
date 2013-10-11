package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.Tank;

public class RemoveTankTask implements Task
{
	private Tank tank;
	private Level level;

	public RemoveTankTask(Tank tank, Level level)
	{
		this.tank = tank;
		this.level = level;
	}

	@Override
	public void execute(int iteration)
	{
		level.erase(tank);
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
