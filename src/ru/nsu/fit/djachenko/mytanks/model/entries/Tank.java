package ru.nsu.fit.djachenko.mytanks.model.entries;

import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToViewFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.CellFactory;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;

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

	private boolean alive = true;

	private final Map<Direction, MessageToView> messages = new HashMap<>();

	public Tank(Level level, int x, int y, Direction dir)
	{
		this.level = level;

		this.x = x;
		this.y = y;

		currentDirection = dir;

		for (Direction direction : Direction.values())
		{
			messages.put(direction, MessageToViewFactory.getInstance().getTankMovedMessage(id, direction));
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

			level.accept(messages.get(direction));
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
				level.move(x - curDx + dx, y - curDy + dy, x + curDx - dx, y + curDy - dy);//TODO: change
			}
			else
			{
				level.move(x - curDx + dy, y - curDy + dx, x + curDx + dy, y + curDy + dx);//TODO: change
				level.move(x - curDx - dy, y - curDy - dx, x + curDx - dy, y + curDy - dx);//TODO: change
			}

			currentDirection = direction;
			level.accept(messages.get(direction));
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
			level.move(x + currentDirection.getDx(), y + currentDirection.getDy(), x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());//TODO: change

			currentDirection = currentDirection.opposite();

			x += currentDirection.getDx();
			y += currentDirection.getDy();

			level.accept(messages.get(currentDirection));
			level.accept(messages.get(currentDirection));
		}
	}

	private boolean ableToMove(Direction direction)
	{
		if (!alive)
		{
			return false;
		}

		switch (currentDirection)
		{
			case RIGHT:
				return level.ableToMove(x - 1, y - 1, direction, 2) &&
						level.ableToMove(x - 1, y, direction, 3) &&
						level.ableToMove(x - 1, y + 1, direction, 2);
			case UP:
				return level.ableToMove(x - 1, y + 1, direction, 2) &&
						level.ableToMove(x, y + 1, direction, 3) &&
						level.ableToMove(x + 1, y + 1, direction, 2);
			case LEFT:
				return level.ableToMove(x + 1, y - 1, direction, 2) &&
						level.ableToMove(x + 1, y, direction, 3) &&
						level.ableToMove(x + 1, y + 1, direction, 2);
			case DOWN:
				return level.ableToMove(x - 1, y - 1, direction, 2) &&
						level.ableToMove(x, y - 1, direction, 3) &&
						level.ableToMove(x + 1, y - 1, direction, 2);
			default:
				//throw
				return false;
		}
	}

	private boolean ableToTurn(Direction direction)
	{
		if (!alive)
		{
			return false;
		}

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
		return alive && level.ableToReplace(x - 2 * currentDirection.getDx(), y - 2 * currentDirection.getDy());
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
		return alive && level.ableToSpawnBullet(x + 2 * currentDirection.getDx(), y + 2 * currentDirection.getDy());
	}

	public void hit()
	{
		alive = false;
		level.hitTank(this);
	}

	@Override
	public void draw(Field field)
	{
		int dx = currentDirection.getDx();
		int dy = currentDirection.getDy();

		CellFactory cellFactory = CellFactory.getInstance();

		for (int j = -1; j <= 1; j++)//y
		{
			for (int i = -1; i <= 1; i++)//x
			{
				if (!((dx == 0 && j == dy && i != 0) || (dy == 0 && j != 0 && i == dx)))
				{
					field.replace(x + i, y + j, cellFactory.getTankCell(field, this, x + i, y + j));
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

		for (int j = -1; j <= 1; j++)//y
		{
			for (int i = -1; i <= 1; i++)//x
			{
				if (!((dx == 0 && j == dy && i != 0) || (dy == 0 && j != 0 && i == dx)))
				{
					field.replace(x + i, y + j, cellFactory.getGroundCell());
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