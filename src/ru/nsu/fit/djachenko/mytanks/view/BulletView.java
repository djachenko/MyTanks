package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Bullet;

public class BulletView extends CellView
{
	private final Bullet origin;

	private int x;
	private int y;

	public BulletView(Bullet origin)
	{
		super(Type.BULLET, origin.getX(), origin.getY());

		this.origin = origin;
		this.x = origin.getX();
		this.y = origin.getY();
	}

	public void iteration()
	{
		if (x != origin.getX() || y != origin.getY())//moved
		{
			int dx = origin.getX() - x;
			int dy = origin.getY() - y;

			for (int i = 0; i < CellView.SIZE; i++)
			{
				setLocation(getX() + dx, getY() + dy);
			}

			x += dx;
			y += dy;
		}
	}

	public boolean isActive()
	{
		return origin.isActive();
	}
}
