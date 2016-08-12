package ru.nsu.fit.djachenko.mytanks.model;

public class DirectedPoint extends Point
{
	private Direction direction;

	public DirectedPoint(int x, int y, Direction direction)
	{
		super(x, y);

		this.direction = direction;
	}

	public Direction getDirection()
	{
		return direction;
	}
}
