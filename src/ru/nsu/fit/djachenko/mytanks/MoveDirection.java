package ru.nsu.fit.djachenko.mytanks;

public enum MoveDirection
{
	LEFT(-1, 0)
			{
				@Override
				public MoveDirection opposite()
				{
					return RIGHT;
				}
			},
	UP(0, -1)
			{
				@Override
				public MoveDirection opposite()
				{
					return DOWN;
				}
			},//y axis is down-directed
	RIGHT(1, 0)
			{
				@Override
				public MoveDirection opposite()
				{
					return LEFT;
				}
			},
	DOWN(0, 1)
			{
				@Override
				public MoveDirection opposite()
				{
					return UP;
				}
			};//y axis is down-directed


	public final int dx;
	public final int dy;

	private MoveDirection(int x, int y)
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

	public abstract MoveDirection opposite();
}
