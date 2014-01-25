package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.entries.Bullet;

public class MoveBulletTask implements Task
{
	private final Bullet bullet;

	private final static int SCIPCOUNT = 2;

	public MoveBulletTask(Bullet bullet)
	{
		this.bullet = bullet;
	}

	@Override
	public void execute(int iteration)
	{
		if (iteration % SCIPCOUNT == 0)
		{
			bullet.move();
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return bullet.isActive();
	}
}
