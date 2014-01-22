package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.entrylevel.Tank;

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
