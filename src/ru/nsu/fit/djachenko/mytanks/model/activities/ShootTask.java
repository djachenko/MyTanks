package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Tank;

public class ShootTask extends Task
{
	private Tank tank;

	public ShootTask(Tank tank)
	{
		super(Type.SHOOT);
		this.tank = tank;
	}

	@Override
	public void execute()
	{
		tank.shoot();
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
