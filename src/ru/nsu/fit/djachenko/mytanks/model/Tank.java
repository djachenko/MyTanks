package ru.nsu.fit.djachenko.mytanks.model;

public class Tank
{
	private int x;
	private int y;

	private Field field;
	private Direction currentDirection;

	public Tank(Field field, int x, int y, boolean friend, Direction dir)
	{
		this.field = field;

		this.x = x;
		this.y = y;

		currentDirection = dir;
	}

	public boolean ableToMove(Direction direction) throws UnexpectedSituationException
	{
		switch (currentDirection)
		{
			case RIGHT:
				return field.ableToMove(x - 1, y - 1, direction) && field.ableToMove(x - 1, y, direction) && field.ableToMove(x - 1, y + 1, direction);
			case UP:
				return field.ableToMove(x - 1, y + 1, direction) && field.ableToMove(x, y + 1, direction) && field.ableToMove(x + 1, y + 1, direction);
			case LEFT:
				return field.ableToMove(x + 1, y - 1, direction) && field.ableToMove(x + 1, y, direction) && field.ableToMove(x + 1, y + 1, direction);
			case DOWN:
				return field.ableToMove(x - 1, y - 1, direction) && field.ableToMove(x, y - 1, direction) && field.ableToMove(x + 1, y - 1, direction);
			default:
				throw new UnexpectedSituationException(currentDirection.name());
		}
	}

	public void move(Direction dir) throws UnexpectedSituationException
	{
		if (dir != currentDirection)
		{
			turn(dir);
		}
		else if (ableToMove(dir))
		{
			switch (currentDirection)
			{
				case RIGHT:
					field.move(x - 1, y - 1, dir);
					System.out.println("tank1");
					field.move(x - 1, y,     dir);
					System.out.println("tank2");
					field.move(x - 1, y + 1, dir);

					break;
				case UP:
					field.move(x - 1, y + 1, dir);
					field.move(x,     y + 1, dir);
					field.move(x + 1, y + 1, dir);

					break;
				case LEFT:
					field.move(x + 1, y - 1, dir);
					field.move(x + 1, y,     dir);
					field.move(x + 1, y + 1, dir);

					break;
				case DOWN:
					field.move(x - 1, y - 1, dir);
					field.move(x,     y - 1, dir);
					field.move(x + 1, y - 1, dir);

					break;
				default:
					throw new UnexpectedSituationException(dir.name());
			}

			x += dir.getDx();
			y += dir.getDy();
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
			return field.ableToReplace(x + currentDirection.getDx() - direction.getDx(), y + currentDirection.getDy() - direction.getDy());
		}
		else
		{
			return field.ableToReplace(x + currentDirection.getDx() + direction.getDy(), y + currentDirection.getDy() + direction.getDx()) &&
					field.ableToReplace(x + currentDirection.getDx() - direction.getDy(), y + currentDirection.getDy() - direction.getDx());
		}
	}

	public boolean ableToFlip()
	{
		return field.ableToReplace(x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());
	}

	public void flip()
	{
		System.out.println("flip");
		if (ableToFlip())
		{
			field.move(x + currentDirection.getDx(), y + currentDirection.getDy(), x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());

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
				field.move(x - curDx + dx, y - curDy + dy, x + curDx - dx, y + curDy - dy);
			}
			else
			{
				field.move(x - curDx + dy, y - curDy + dx, x + curDx + dy, y + curDy + dx);
				field.move(x - curDx - dy, y - curDy - dx, x + curDx - dy, y + curDy - dx);
			}

			currentDirection = direction;
		}
		else if (direction == currentDirection.opposite())
		{
			flip();
		}
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



























