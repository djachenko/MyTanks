package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.entrylevel.Level;
import ru.nsu.fit.djachenko.mytanks.model.entrylevel.Tank;

public class RemoveTankTask implements Task
{
	private final Tank tank;
	private final Level level;

	public RemoveTankTask(Tank tank, Level level)
	{
		this.tank = tank;
		this.level = level;
	}

	@Override
	public void execute(int iteration)
	{
		level.remove(tank);
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
