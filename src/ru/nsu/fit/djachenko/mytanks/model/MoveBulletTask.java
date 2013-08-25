package ru.nsu.fit.djachenko.mytanks.model;

public class MoveBulletTask extends Task
{
	private Bullet bullet;

	private int count = 0;

	public MoveBulletTask(Bullet bullet)
	{
		super(Type.MOVEBULLET);

		this.bullet = bullet;
	}

	@Override
	public void execute()
	{
		if (count == 10)
		{
			bullet.move();
			count = 0;
		}
		else
		{
			count++;
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return bullet.isActive();
	}
}
