package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Field;

public abstract class Cell
{
	private Field field;
	private int x;
	private int y;

	public enum Type
	{
		TANK('t'),
		WALL('x'),
		GROUND('.');

		public final char representation;

		private Type(char x)
		{
			representation = x;
		}
	}

	public final Type type;

	public Cell(Type type, Field field, int x, int y)
	{
		this.type = type;
		this.field = field;
		this.x = x;
		this.y = y;
	}

	public boolean ableToMove(Direction dir)
	{
		return field.ableToMove(x, y, dir);
	}

	public void move(Direction dir)
	{
		if (ableToMove(dir))
		{
			field.move(x, y, dir);

			x += dir.getDx();
			y += dir.getDy();
		}
	}

	public abstract boolean ableToReplace();

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
