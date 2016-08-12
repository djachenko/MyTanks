package ru.nsu.fit.djachenko.mytanks.model;

public class DistancedPoint extends DirectedPoint
{
	private int distance;

	public DistancedPoint(int x, int y, Direction direction, int distance)
	{
		super(x, y, direction);

		this.distance = distance;
}

	public int getDistance()
	{
		return distance;
	}
}