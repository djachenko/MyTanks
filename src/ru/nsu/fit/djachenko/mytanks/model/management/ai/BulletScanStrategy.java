package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.DirectedPoint;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.Imperative;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.ImperativeFactory;

class BulletScanStrategy implements Strategy
{
	private static ImperativeFactory factory = ImperativeFactory.getInstance();

	private enum State
	{
		SEARCH,
		CHECK,
		DANGER
	}

	private State state = State.SEARCH;

	private ShortestWaySubStrategy shortestWayStrategy = new ShortestWaySubStrategy();
	private FindBulletSubStrategy findBulletStrategy = new FindBulletSubStrategy();

	private int checkDistance;

	@Override
	public Imperative run(Tank.State tankState, Field.State fieldState, AI parent)
	{
		return run(new DirectedPoint(tankState.getX(), tankState.getY(), tankState.getDirection()), fieldState);
	}

	public Imperative run(DirectedPoint directedPoint, Field.State fieldState)
	{
		FindBulletSubStrategy.Result point = findBulletStrategy.run(directedPoint, fieldState);

		if (point.isValid())
		{
			switch (state)
			{
				case SEARCH:
					state = State.CHECK;
					checkDistance = point.getDistance();

					return factory.getWaitImperative();

				case CHECK:
					if (point.getDistance() >= checkDistance)
					{
						return factory.getWaitImperative();
					}
					else
					{
						state = State.DANGER;
					}

				case DANGER:
					boolean[][] mask = new boolean[fieldState.height()][fieldState.width()];

					for (boolean[] line : mask)
					{
						for (int i = 0; i < line.length; i++)
						{
							line[i] = true;
						}
					}

					Direction direction = point.getDirection();

					int dx = direction.opposite().getDx();
					int dy = direction.opposite().getDy();

					int width = fieldState.width();
					int height = fieldState.height();

					int x = point.getX();
					int y = point.getY();

					if (direction.isHorisontal())
					{
						while (x >= 0 && x < width && y >= 0 && y < height && fieldState.at(x, y) != Cell.Type.WALL)
						{
							for (int i = -1; i <= 1; i++)
							{
								mask[y + i][x] = false;
							}

							x += dx;
							y += dy;
						}
					}
					else
					{
						while (x >= 0 && x < width && y >= 0 && y < height && fieldState.at(x, y) != Cell.Type.WALL)
						{
							for (int i = -1; i <= 1; i++)
							{
								mask[y][x + i] = false;
							}

							x += dx;
							y += dy;
						}
					}

					StringBuilder builder = new StringBuilder();

					for (boolean[] line : mask)
					{
						for (boolean cell : line)
						{
							if (cell)
							{
								builder.append('#');
							}
							else
							{
								builder.append(' ');
							}
						}

						builder.append('\n');
					}

					System.out.println(builder);


					return shortestWayStrategy.getNextMove(directedPoint, fieldState, mask);

				default:
					throw new NullPointerException();
			}
		}
		else
		{
			state = State.SEARCH;

			return factory.getSkipImperative();
		}
	}

	@Override
	public int getPriority()
	{
		return 0;
	}
}