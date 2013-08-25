package ru.nsu.fit.djachenko.mytanks.model;

public class Bullet
{
	public static int PERIOD = (int)(1 * 1000);

	private Level level;

	private int x;
	private int y;

	public final Direction direction;
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

		x += direction.dx;
		y += direction.dy;
	}

	public void hit()
	{
		explode();
	}

	public void explode()
	{
		level.erase(this);
		active = false;
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
