package ru.nsu.fit.djachenko.mytanks.model;

public class Bullet
{
	private Level level;

	private int x;
	private int y;

	private final Direction direction;
	private boolean active = true;

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

		x += direction.getDx();
		y += direction.getDy();
	}

	public void hit()
	{
		explode();
	}

	public void explode()
	{
		active = false;

		level.remove(this);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public boolean isActive()
	{
		return active;
	}
}
