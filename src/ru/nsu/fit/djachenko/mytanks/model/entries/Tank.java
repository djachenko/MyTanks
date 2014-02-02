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
	
	public static class State
	{
		private int x;
		private int y;
		private Direction direction;
		
		private State(int x, int y, Direction direction)
		{
			this.x = x;
			this.y = y;
			this.direction = direction;
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
			return direction;
		}
	}
	
	private final State state;

	private final Level level;

	private boolean alive = true;

	private final Map<Direction, MessageToView> messages = new HashMap<>();

	public Tank(Level level, int x, int y, Direction direction)
	{
		this.level = level;

		this.state = new State(x, y, direction);

		for (Direction dir : Direction.values())
		{
			messages.put(dir, MessageToViewFactory.getInstance().getTankMovedMessage(id, dir));
		}
	}

	public void move(Direction direction)
	{
		if (direction != state.direction)
		{
			turn(direction);
		}
		else if (ableToMove(direction))
		{
			switch (state.direction)
			{
				case RIGHT:
					level.move(state.x - 1, state.y - 1, direction, 2);
					level.move(state.x - 1, state.y, direction, 3);
					level.move(state.x - 1, state.y + 1, direction, 2);

					break;
				case UP:
					level.move(state.x - 1, state.y + 1, direction, 2);
					level.move(state.x, state.y + 1, direction, 3);
					level.move(state.x + 1, state.y + 1, direction, 2);

					break;
				case LEFT:
					level.move(state.x + 1, state.y - 1, direction, 2);
					level.move(state.x + 1, state.y, direction, 3);
					level.move(state.x + 1, state.y + 1, direction, 2);

					break;
				case DOWN:
					level.move(state.x - 1, state.y - 1, direction, 2);
					level.move(state.x, state.y - 1, direction, 3);
					level.move(state.x + 1, state.y - 1, direction, 2);

					break;
				default:
					//throw
			}

			state.x += direction.getDx();
			state.y += direction.getDy();

			level.accept(messages.get(direction));
		}
	}

	private void turn(Direction direction)
	{
		if (ableToTurn(direction))
		{
			int curDx = state.direction.getDx();
			int curDy = state.direction.getDy();

			int dx = direction.getDx();
			int dy = direction.getDy();

			if (direction != state.direction.opposite())
			{
				level.move(state.x - curDx + dx, state.y - curDy + dy, state.x + curDx - dx, state.y + curDy - dy);//TODO: change
			}
			else
			{
				level.move(state.x - curDx + dy, state.y - curDy + dx, state.x + curDx + dy, state.y + curDy + dx);//TODO: change
				level.move(state.x - curDx - dy, state.y - curDy - dx, state.x + curDx - dy, state.y + curDy - dx);//TODO: change
			}

			state.direction = direction;
			level.accept(messages.get(direction));
		}
		else if (direction == state.direction.opposite())
		{
			flip();
		}
	}

	private void flip()
	{
		if (ableToFlip())
		{
			level.move(state.x + state.direction.getDx(), state.y + state.direction.getDy(), state.x - 2 * state.direction.getDx(), state.y - 2 * state.direction.getDy());//TODO: change

			state.direction = state.direction.opposite();

			state.x += state.direction.getDx();
			state.y += state.direction.getDy();

			level.accept(messages.get(state.direction));
			level.accept(messages.get(state.direction));
		}
	}

	private boolean ableToMove(Direction direction)
	{
		if (!alive)
		{
			return false;
		}

		switch (state.direction)
		{
			case RIGHT:
				return level.ableToMove(state.x - 1, state.y - 1, direction, 2) &&
						level.ableToMove(state.x - 1, state.y, direction, 3) &&
						level.ableToMove(state.x - 1, state.y + 1, direction, 2);
			case UP:
				return level.ableToMove(state.x - 1, state.y + 1, direction, 2) &&
						level.ableToMove(state.x, state.y + 1, direction, 3) &&
						level.ableToMove(state.x + 1, state.y + 1, direction, 2);
			case LEFT:
				return level.ableToMove(state.x + 1, state.y - 1, direction, 2) &&
						level.ableToMove(state.x + 1, state.y, direction, 3) &&
						level.ableToMove(state.x + 1, state.y + 1, direction, 2);
			case DOWN:
				return level.ableToMove(state.x - 1, state.y - 1, direction, 2) &&
						level.ableToMove(state.x, state.y - 1, direction, 3) &&
						level.ableToMove(state.x + 1, state.y - 1, direction, 2);
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

		if (direction == state.direction)
		{
			return true;
		}

		if (direction != state.direction.opposite())
		{
			return level.ableToReplace(state.x + state.direction.getDx() - direction.getDx(), state.y + state.direction.getDy() - direction.getDy());
		}
		else
		{
			return level.ableToReplace(state.x + state.direction.getDx() + direction.getDy(), state.y + state.direction.getDy() + direction.getDx()) &&
					level.ableToReplace(state.x + state.direction.getDx() - direction.getDy(), state.y + state.direction.getDy() - direction.getDx());
		}
	}

	private boolean ableToFlip()
	{
		return alive && level.ableToReplace(state.x - 2 * state.direction.getDx(), state.y - 2 * state.direction.getDy());
	}

	public void shoot()
	{
		if (ableToShoot())
		{
			level.spawnBullet(state.x + 2 * state.direction.getDx(), state.y + 2 * state.direction.getDy(), state.direction);
		}
	}

	private boolean ableToShoot()
	{
		return alive && level.ableToSpawnBullet(state.x + 2 * state.direction.getDx(), state.y + 2 * state.direction.getDy());
	}

	public void hit()
	{
		alive = false;
		level.hitTank(this);
	}

	@Override
	public void draw(Field field)
	{
		int dx = state.direction.getDx();
		int dy = state.direction.getDy();

		CellFactory cellFactory = CellFactory.getInstance();

		for (int j = -1; j <= 1; j++)//y
		{
			for (int i = -1; i <= 1; i++)//x
			{
				if (!((dx == 0 && j == dy && i != 0) || (dy == 0 && j != 0 && i == dx)))
				{
					field.replace(state.x + i, state.y + j, cellFactory.getTankCell(field, this, state.x + i, state.y + j));
				}
			}
		}
	}

	@Override
	public void erase(Field field)
	{
		int dx = state.direction.getDx();
		int dy = state.direction.getDy();

		CellFactory cellFactory = CellFactory.getInstance();

		for (int j = -1; j <= 1; j++)//y
		{
			for (int i = -1; i <= 1; i++)//x
			{
				if (!((dx == 0 && j == dy && i != 0) || (dy == 0 && j != 0 && i == dx)))
				{
					field.replace(state.x + i, state.y + j, cellFactory.getGroundCell());
				}
			}
		}
	}

	public int getX()
	{
		return state.x;
	}

	public int getY()
	{
		return state.y;
	}

	public Direction getDirection()
	{
		return state.direction;
	}

	public int getId()
	{
		return id;
	}

	public State getState()
	{
		return state;
	}
}