package ru.nsu.fit.djachenko.mytanks.model;

public class Tank
{
	private int id;
	private static int count = 0;

	private int x;
	private int y;

	private Field field;
	private MoveDirection currentDirection;
	private boolean friend;
	private boolean alive;

	public Tank(Field field, int x, int y, boolean friend)
	{
		this.field = field;

		id = count;
		count++;

		//new Tank(field, x, y,friend, MoveDirection.UP);

		this.x = x;
		this.y = y;

		this.friend = friend;

		currentDirection = MoveDirection.UP;

		alive = true;
	}

	public Tank(Field field, int x, int y, boolean friend, MoveDirection dir)
	{
		this.field = field;

		this.x = x;
		this.y = y;

		this.friend = friend;

		currentDirection = dir;

		alive = true;
	}

	public boolean ableToMove(MoveDirection direction) throws UnexpectedSituation
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
				throw new UnexpectedSituation(currentDirection.name());
		}
	}

	public void move(MoveDirection dir) throws UnexpectedSituation
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
					field.move(x - 1, y, dir);
					field.move(x - 1, y + 1, dir);

					break;
				case UP:
					field.move(x - 1, y + 1, dir);
					field.move(x, y + 1, dir);
					field.move(x + 1, y + 1, dir);

					break;
				case LEFT:
					field.move(x + 1, y - 1, dir);
					field.move(x + 1, y, dir);
					field.move(x + 1, y + 1, dir);

					break;
				case DOWN:
					field.move(x - 1, y - 1, dir);
					field.move(x, y - 1, dir);
					field.move(x + 1, y - 1, dir);

					break;
				default:
					throw new UnexpectedSituation(dir.name());
			}

			x += dir.getDx();
			y += dir.getDy();
		}
	}

	public boolean ableToTurn(MoveDirection direction)
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
		if (ableToFlip())
		{
			field.replace(x + currentDirection.getDx(), y + currentDirection.getDy(), x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());

			currentDirection = currentDirection.opposite();
		}
	}

	public void turn(MoveDirection direction)//turn clockwise
	{
		if (ableToTurn(direction))
		{
			if (direction != currentDirection.opposite())
			{
				field.replace(x - currentDirection.getDx() + direction.getDx(), y - currentDirection.getDy() + direction.getDy(),
						x + currentDirection.getDx() - direction.getDx(), y + currentDirection.getDy() - direction.getDy());
			}
			else
			{
				field.replace(x - currentDirection.getDx() + direction.getDy(), y - currentDirection.getDy() + direction.getDx(), x + currentDirection.getDx() + direction.getDy(), y + currentDirection.getDy() + direction.getDx());
				field.replace(x - currentDirection.getDx() - direction.getDy(), y - currentDirection.getDy() - direction.getDx(), x + currentDirection.getDx() - direction.getDy(), y + currentDirection.getDy() - direction.getDx());
			}

			currentDirection = direction;
		}
		else
		{
			flip();
		}
	}
}



























