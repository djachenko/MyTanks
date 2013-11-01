package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Field;

public class BulletCell extends Cell
{
	private final Field field;
	private final Bullet origin;
	private int x;
	private int y;

	public BulletCell(Field field, Bullet origin, int x, int y)
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
			explode();
		}
		else if (origin.ableToHit(x + dir.getDx(), y + dir.getDy()))
		{
			origin.hit(dir.getDx(), dir.getDy());//REFACTOR
			explode();
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
		explode();
	}

	@Override
	public boolean hasToBeWaited()
	{
		return true;
	}

	private void explode()
	{
		origin.explode();
		field.replace(x, y, new GroundCell());
	}
}
