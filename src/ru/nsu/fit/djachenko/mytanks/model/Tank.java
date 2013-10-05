package ru.nsu.fit.djachenko.mytanks.model;

public class Tank
{
	private int x;
	private int y;

	private Level level;
	private Direction currentDirection;

	public Tank(Level field, int x, int y, Direction dir)
	{
		this.level = field;

		this.x = x;
		this.y = y;

		currentDirection = dir;
	}

	public boolean ableToMove(Direction direction) throws UnexpectedSituationException
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
				throw new UnexpectedSituationException(currentDirection.name());
		}
	}

	public void move(Direction direction) throws UnexpectedSituationException
	{
		if (direction != currentDirection)
		{
			turn(direction);
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
					throw new UnexpectedSituationException(direction.name());
			}

			x += direction.getDx();
			y += direction.getDy();
		}
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

			x += currentDirection.dx;
			y += currentDirection.dy;
		}
	}

	public void turn(Direction direction)
	{
		if (ableToTurn(direction))
		{
			int curDx = currentDirection.dx;
			int curDy = currentDirection.dy;

			int dx = direction.dx;
			int dy = direction.dy;

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
		return level.ableToSpawnBullet(x + 2 * currentDirection.dx, y + 2 * currentDirection.dy);
	}

	public void shoot()
	{
		if (ableToShoot())
		{
			level.addBullet(x + 2 * currentDirection.dx, y + 2 * currentDirection.dy, currentDirection);
		}
	}

	public void hit()
	{
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
}



























