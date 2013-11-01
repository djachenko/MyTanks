package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Bullet;

public class MoveBulletTask implements Task
{
	private Bullet bullet;

	private final static int SCIPCOUNT = 5;

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
