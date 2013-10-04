package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Bullet;

public class MoveBulletTask implements Task
{
	private Bullet bullet;

	public final static int SCIPCOUNT = 10;

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
