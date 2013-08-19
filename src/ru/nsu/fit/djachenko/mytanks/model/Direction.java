package ru.nsu.fit.djachenko.mytanks.model;

public enum Direction
{
	LEFT(-1, 0)
			{
				@Override
				public Direction opposite()
				{
					return RIGHT;
				}
			},
	UP(0, -1)
			{
				@Override
				public Direction opposite()
				{
					return DOWN;
				}
			},//y axis is down-directed
	RIGHT(1, 0)
			{
				@Override
				public Direction opposite()
				{
					return LEFT;
				}
			},
	DOWN(0, 1)
			{
				@Override
				public Direction opposite()
				{
					return UP;
				}
			};//y axis is down-directed


	public final int dx;
	public final int dy;

	private Direction(int x, int y)
	{
		dx = x;
		dy = y;
	}

	public int getDx()
	{
		return dx;
	}

	public int getDy()
	{
		return dy;
	}

	public abstract Direction opposite();

	public boolean isHorisontal()
	{
		return dy == 0;
	}

	public boolean isVertical()
	{
		return dx == 0;
	}
}
