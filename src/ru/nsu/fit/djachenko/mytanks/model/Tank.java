package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.TankMovedMessage;
import ru.nsu.fit.djachenko.mytanks.model.cells.CellFactory;

import java.util.HashMap;
import java.util.Map;

public class Tank implements FieldElement
{
	private static int count = 0;
	private final int id = count++;

	private int x;
	private int y;

	private final Level level;
	private Direction currentDirection;

	private final Map<Direction, TankMovedMessage> messages = new HashMap<>();

	public Tank(Level level, int x, int y, Direction dir)
	{
		this.level = level;

		this.x = x;
		this.y = y;

		currentDirection = dir;

		for (Direction direction : Direction.values())
		{
			messages.put(direction, new TankMovedMessage(id, direction));
		}
	}

	public void move(Direction direction)
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
					//throw
			}

			x += direction.getDx();
			y += direction.getDy();

			level.send(messages.get(direction));
		}
	}

	private void turn(Direction direction)
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
			level.send(messages.get(direction));
		}
		else if (direction == currentDirection.opposite())
		{
			flip();
		}
	}

	private void flip()
	{
		if (ableToFlip())
		{
			level.move(x + currentDirection.getDx(), y + currentDirection.getDy(), x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());

			currentDirection = currentDirection.opposite();

			x += currentDirection.getDx();
			y += currentDirection.getDy();

			level.send(messages.get(currentDirection));
			level.send(messages.get(currentDirection));
		}
	}

	private boolean ableToMove(Direction direction)
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

	private boolean ableToTurn(Direction direction)
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

	private boolean ableToFlip()
	{
		return level.ableToReplace(x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());
	}

	public void shoot()
	{
		if (ableToShoot())
		{
			level.spawnBullet(x + 2 * currentDirection.getDx(), y + 2 * currentDirection.getDy(), currentDirection);
		}
	}

	private boolean ableToShoot()
	{
		return level.ableToSpawnBullet(x + 2 * currentDirection.getDx(), y + 2 * currentDirection.getDy());
	}

	public void hit()
	{
		level.hitTank(this);
	}

	@Override
	public void draw(Field field)
	{
		int dx = currentDirection.getDx();
		int dy = currentDirection.getDy();

		CellFactory cellFactory = CellFactory.getInstance();

		for (int k = -1; k <= 1; k++)//y
		{
			for (int j = -1; j <= 1; j++)//x
			{
				if (!((dx == 0 && k == dy && j != 0) || (dy == 0 && k != 0 && j == dx)))
				{
					field.replace(x + j, y + k, cellFactory.getTankCell(field, this, x + j, y + k));
				}
			}
		}
	}

	@Override
	public void erase(Field field)
	{
		int dx = currentDirection.getDx();
		int dy = currentDirection.getDy();

		CellFactory cellFactory = CellFactory.getInstance();

		for (int k = -1; k <= 1; k++)//y
		{
			for (int j = -1; j <= 1; j++)//x
			{
				if (!((dx == 0 && k == dy && j != 0) || (dy == 0 && k != 0 && j == dx)))
				{
					field.replace(x + j, y + k, cellFactory.getGroundCell());
				}
			}
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

	public int getId()
	{
		return id;
	}
}