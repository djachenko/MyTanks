package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.Level;

public class RemoveBulletTask implements Task
{
	private Bullet bullet;
	private Level level;

	public RemoveBulletTask(Bullet bullet, Level level)
	{
		this.bullet = bullet;
		this.level = level;
	}

	@Override
	public void execute(int iteration)
	{
		level.erase(bullet);
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
