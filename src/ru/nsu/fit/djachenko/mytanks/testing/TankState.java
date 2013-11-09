package ru.nsu.fit.djachenko.mytanks.testing;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

public class TankState
{
	private static int count = 0;
	private final int id = count++;

	private int x;
	private int y;

	private Direction direction;

	public TankState(int x, int y, Direction direction)
	{
		this.direction = direction;
		this.x = x;
		this.y = y;
	}

	public int getId()
	{
		return id;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void incrementX(int dx)
	{
		this.x += dx;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void incrementY(int dy)
	{
		this.y += dy;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
}
