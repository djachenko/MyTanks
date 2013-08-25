package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Field;
import ru.nsu.fit.djachenko.mytanks.model.Tank;

public class TankCell extends Cell
{
	private Field field;
	private Tank tank;

	private int x;
	private int y;

	public TankCell(Field field, Tank tank, int x, int y)
	{
		super(Type.TANK);

		this.field = field;
		this.tank = tank;

		this.x = x;
		this.y = y;
	}

	@Override
	public boolean ableToMove(Direction dir, int depth)
	{
		return depth > 0 && field.ableToMove(x + dir.dx, y + dir.dy, dir, depth - 1);
	}

	@Override
	public void move(Direction dir, int depth)
	{
		if (ableToMove(dir, depth))
		{
			field.move(x + dir.dx, y + dir.dy, dir, depth - 1);
			field.replace(x + dir.dx, y + dir.dy, this);

			x += dir.dx;
			y += dir.dy;
		}
	}

	@Override
	public void move(int toX, int toY)
	{
		x = toX;
		y = toY;
	}

	@Override
	public boolean ableToReplace()
	{
		return false;
	}

	@Override
	public boolean ableToHit()
	{
		return true;
	}

	@Override
	public void hit()
	{
		tank.hit();
	}
}
