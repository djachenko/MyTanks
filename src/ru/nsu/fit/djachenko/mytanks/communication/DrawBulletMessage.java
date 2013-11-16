package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class DrawBulletMessage extends MessageToView
{
	private Bullet bullet;

	private int x;
	private int y;
	private Direction direction;
	private int id;

	public DrawBulletMessage(Bullet bullet)
	{
		this.bullet = bullet;

		this.x = bullet.getX();
		this.y = bullet.getY();
		this.direction = bullet.getDirection();
		this.id = bullet.getId();
	}

	@Override
	public void handle(LevelView levelView)
	{
		levelView.accept(this);
	}

	public Bullet getBullet()
	{
		return bullet;
	}

	public int getX()
	{
		return x;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public int getY()
	{
		return y;
	}

	public int getId()
	{
		return id;
	}
}
