package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

public abstract class Cell
{
	public enum Type
	{
		TANK('t'),
		WALL('x'),
		GROUND('.'),
		BULLET('b');

		public final char representation;

		private Type(char x)
		{
			representation = x;
		}
	}

	private final Type type;

	Cell(Type type)
	{
		this.type = type;
	}

	public Type getType()
	{
		return type;
	}

	public abstract boolean ableToMove(Direction dir, int depth);
	public abstract void move(Direction dir, int depth);
	public abstract void move(int toX, int toY);
	public abstract boolean ableToReplace();
	public abstract boolean ableToHit();
	public abstract void hit();
	public abstract boolean hasToBeWaited();
}
