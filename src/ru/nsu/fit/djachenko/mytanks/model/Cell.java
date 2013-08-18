package ru.nsu.fit.djachenko.mytanks.model;

public abstract class Cell
{
	private Field field;
	private int x;
	private int y;

	public enum Type
	{
		TANK('t'),
		WALL('x'),
		FLOOR('.');

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

	public boolean ableToMove(MoveDirection dir)
	{
		return field.ableToMove(x, y, dir);
	}

	public void move(MoveDirection dir)
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
