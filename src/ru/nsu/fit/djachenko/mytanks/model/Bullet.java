package ru.nsu.fit.djachenko.mytanks.model;

public class Bullet
{
	private Level level;

	private int x;
	private int y;

	public final Direction direction;

	Bullet(Level level, int x, int y, Direction direction)
	{
		this.level = level;

		this.x = x;
		this.y = y;

		this.direction = direction;
	}

	public boolean ableToHit(int x, int y)
	{
		return level.ableToHit(x, y);
	}

	public void hit(int dx, int dy)
	{
		level.hit(x + dx, y + dy);
	}

	public void move()
	{
		level.move(x, y, direction, 1);
	}

	public void hit()
	{}

	public void explode()
	{}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
