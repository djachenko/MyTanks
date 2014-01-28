package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

class TankCell extends Cell
{
	private final Field field;
	private final Tank tank;

	private int x;
	private int y;

	TankCell(Field field, Tank tank, int x, int y)
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
		return depth > 0 && field.ableToMove(x + dir.getDx(), y + dir.getDy(), dir, depth - 1);
	}

	@Override
	public void move(Direction dir, int depth)
	{
		if (ableToMove(dir, depth))
		{
			field.move(x + dir.getDx(), y + dir.getDy(), dir, depth - 1);
			field.replace(x + dir.getDx(), y + dir.getDy(), this);

			x += dir.getDx();
			y += dir.getDy();
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

	@Override
	public boolean hasToBeWaited()
	{
		return false;
	}
}
