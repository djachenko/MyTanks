package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.testing.TankMovedMessage;

public class Tank
{
	private static int count = 0;

	private final int id = count++;

	private int x;
	private int y;

	private Level level;
	private Direction currentDirection;

	private boolean alive;

	public Tank(Level level, int x, int y, Direction dir)
	{
		this.level = level;

		this.x = x;
		this.y = y;

		this.alive = true;

		currentDirection = dir;
	}

	public boolean ableToMove(Direction direction)
	{
		switch (currentDirection)
		{
			case RIGHT:
				return level.ableToMove(x - 1, y - 1, direction, 2) && level.ableToMove(x - 1, y, direction, 3) && level.ableToMove(x - 1, y + 1, direction, 2);
			case UP:
				return level.ableToMove(x - 1, y + 1, direction, 2) && level.ableToMove(x, y + 1, direction, 3) && level.ableToMove(x + 1, y + 1, direction, 2);
			case LEFT:
				return level.ableToMove(x + 1, y - 1, direction, 2) && level.ableToMove(x + 1, y, direction, 3) && level.ableToMove(x + 1, y + 1, direction, 2);
			case DOWN:
				return level.ableToMove(x - 1, y - 1, direction, 2) && level.ableToMove(x, y - 1, direction, 3) && level.ableToMove(x + 1, y - 1, direction, 2);
			default:
				//throw
				return false;
		}
	}

	public void move(Direction direction)
	{
		System.out.println("pre " + alive);

		if (direction != currentDirection)
		{
			turn(direction);
			level.send(new TankMovedMessage(id, direction));

			System.out.println("post " + alive);
		}
		else if (ableToMove(direction))
		{
			switch (currentDirection)
			{
				case RIGHT:
					level.move(x - 1, y - 1, direction, 2);
					level.move(x - 1, y, direction, 3);
					level.move(x - 1, y + 1, direction, 2);

					break;
				case UP:
					level.move(x - 1, y + 1, direction, 2);
					level.move(x, y + 1, direction, 3);
					level.move(x + 1, y + 1, direction, 2);

					break;
				case LEFT:
					level.move(x + 1, y - 1, direction, 2);
					level.move(x + 1, y, direction, 3);
					level.move(x + 1, y + 1, direction, 2);

					break;
				case DOWN:
					level.move(x - 1, y - 1, direction, 2);
					level.move(x, y - 1, direction, 3);
					level.move(x + 1, y - 1, direction, 2);

					break;
				default:
					//throw
			}

			x += direction.getDx();
			y += direction.getDy();

			level.send(new TankMovedMessage(id, direction));
			System.out.println("post " + alive);
		}

		level.print();
	}

	public boolean ableToTurn(Direction direction)
	{
		if (direction == currentDirection)
		{
			return true;
		}

		if (direction != currentDirection.opposite())
		{
			return level.ableToReplace(x + currentDirection.getDx() - direction.getDx(), y + currentDirection.getDy() - direction.getDy());
		}
		else
		{
			return level.ableToReplace(x + currentDirection.getDx() + direction.getDy(), y + currentDirection.getDy() + direction.getDx()) &&
					level.ableToReplace(x + currentDirection.getDx() - direction.getDy(), y + currentDirection.getDy() - direction.getDx());
		}
	}

	public boolean ableToFlip()
	{
		return level.ableToReplace(x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());
	}

	public void flip()
	{
		if (ableToFlip())
		{
			level.move(x + currentDirection.getDx(), y + currentDirection.getDy(), x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());

			currentDirection = currentDirection.opposite();

			x += currentDirection.getDx();
			y += currentDirection.getDy();

			level.send(new TankMovedMessage(id, currentDirection));
		}
	}

	public void turn(Direction direction)
	{
		if (ableToTurn(direction))
		{
			int curDx = currentDirection.getDx();
			int curDy = currentDirection.getDy();

			int dx = direction.getDx();
			int dy = direction.getDy();

			if (direction != currentDirection.opposite())
			{
				level.move(x - curDx + dx, y - curDy + dy, x + curDx - dx, y + curDy - dy);
			}
			else
			{
				level.move(x - curDx + dy, y - curDy + dx, x + curDx + dy, y + curDy + dx);
				level.move(x - curDx - dy, y - curDy - dx, x + curDx - dy, y + curDy - dx);
			}

			currentDirection = direction;
		}
		else if (direction == currentDirection.opposite())
		{
			flip();
		}
	}

	public boolean ableToShoot()
	{
		return level.ableToSpawnBullet(x + 2 * currentDirection.getDx(), y + 2 * currentDirection.getDy());
	}

	public void shoot()
	{
		if (ableToShoot())
		{
			level.spawnBullet(x + 2 * currentDirection.getDx(), y + 2 * currentDirection.getDy(), currentDirection);
		}
	}

	public void hit()
	{
		alive = false;

		level.hitTank(this);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Direction getDirection()
	{
		return currentDirection;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public int getId()
	{
		return id;
	}
}