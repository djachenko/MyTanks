package ru.nsu.fit.djachenko.mytanks.model.entries.activities;

import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

public class ShootTask implements Task
{
	private final Tank tank;

	public ShootTask(Tank tank)
	{
		this.tank = tank;
	}

	@Override
	public void execute(int iteration)
	{
		tank.shoot();
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
