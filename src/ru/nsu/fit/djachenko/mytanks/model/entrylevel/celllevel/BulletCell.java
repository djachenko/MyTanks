package ru.nsu.fit.djachenko.mytanks.model.entrylevel.celllevel;

import ru.nsu.fit.djachenko.mytanks.model.entrylevel.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

public class BulletCell extends Cell
{
	private final Field field;
	private final Bullet origin;
	private int x;
	private int y;

	BulletCell(Field field, Bullet origin, int x, int y)
	{
		super(Type.BULLET);

		this.field = field;
		this.origin = origin;

		this.x = x;
		this.y = y;
	}

	@Override
	public boolean ableToMove(Direction dir, int depth)
	{
		return true;
	}

	@Override
	public void move(Direction dir, int depth)
	{
		if (depth != 1)
		{
			origin.hit(- dir.getDx(), - dir.getDy());//REFACTOR
		}
		else
		{
			field.replace(x + dir.getDx(), y + dir.getDy(), this);

			x += dir.getDx();
			y += dir.getDy();
		}
	}

	@Override
	public void move(int toX, int toY)
	{
	}

	@Override
	public boolean ableToReplace()
	{
		return true;
	}

	@Override
	public boolean ableToHit()
	{
		return true;
	}

	@Override
	public void hit()
	{
		origin.hit();
	}

	@Override
	public boolean hasToBeWaited()
	{
		return true;
	}
}
