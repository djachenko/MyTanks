package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class BuildEscapeRouteStrategy
{
	static class Result
	{
		private int x;
		private int y;

		private Queue<Direction> route;

		private boolean valid;

		private Result()
		{
			invalidate();
		}

		private void invalidate()
		{
			x = -1;
			y = -1;

			route = null;
			valid = false;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public Direction getNextMove()
		{
			if (route.size() == 0)
			{
				return null;
			}

			if (route.size() == 1)
			{
				valid = false;
			}

			return route.remove();
		}
	}

	Result getCallback()
	{
		return new Result();
	}

	private BulletScanStrategy bulletScanStrategy = new BulletScanStrategy();

	void run(int tankX, int tankY, Direction tankDirection, Field.State state, BulletScanStrategy.Result result, Result callback)
	{
		if (!callback.valid || bulletScanStrategy.run(callback.x, callback.y, state, null))
		{
			countRoute(tankX, tankY, tankDirection, state, result, callback);
		}
	}

	private void countRoute(int tankX, int tankY, Direction tankDirection, Field.State state, BulletScanStrategy.Result result, Result callback)
	{
		int[][] map = new int[state.height()][state.width()];

		for (int[] line : map)
		{
			for (int j = 0; j < line.length; j++)
			{
				line[j] = -1;
			}
		}

		Direction bulletDirection = result.getDirectionToBullet().opposite();

		class Point
		{
			public int x;
			public int y;
			public Direction direction;

			Point(int x, int y, Direction direction)
			{
				this.x = x;
				this.y = y;
				this.direction = direction;
			}
		}

		Vector<Queue<Point>> generations = new Vector<>();

		generations.add(0, new LinkedList<Point>());
		generations.add(1, new LinkedList<Point>());

		generations.get(0).add(new Point(tankX, tankY, tankDirection));
		map[tankY][tankX] = 0;

		boolean found = false;

		for (int generation = 0; !found; generation++)
		{
			Queue<Point> candidates = generations.get(generation);

			if (candidates.size() == 0 && generations.get(generation + 1).size() == 0)
			{
				break;
			}

			generations.add(generation + 2, new LinkedList<Point>());

			for (Point currentPoint : candidates)
			{
				if (((bulletDirection.isHorisontal() && Math.abs(currentPoint.y - result.getY()) >= 2)  ||
					 (bulletDirection.isVertical()   && Math.abs(currentPoint.x - result.getX()) >= 2)) && //this is safe from first bullet
					  !bulletScanStrategy.run(currentPoint.x, currentPoint.y, state, null))//check if there is other one
				{
					found = true;

					callback.x = currentPoint.x;
					callback.y = currentPoint.y;

					break;
				}
				else
				{
					for (int dx = -1; dx <= 1; dx++)
					{
						for (int dy = - Math.abs((dx + 1) % 2); dy <= Math.abs((dx + 1) % 2); dy += 2)
						{
							int candidateX = currentPoint.x + dx;
							int candidateY = currentPoint.y + dy;

							//in order to prevent NullPointerExceptions on edges
							if (candidateX < 1 || candidateX >= state.width() - 1 || candidateY < 1 || candidateY >= state.height() - 1)
							{
								continue;
							}

							boolean ok = true;

							for (int i = -1; i <= 1; i++)
							{
								for (int j = -1; j <= 1; j++)
								{
									if (state.at(candidateX + i, candidateY + j) == Cell.Type.WALL)
									{
										ok = false;
									}
								}
							}

							if ((map[candidateY][candidateX] > map[currentPoint.y][currentPoint.x] + 1 ||//if this way is shorter than existing
									map[candidateY][candidateX] == -1) &&
									ok)//or there isn't any
							{
								Direction candidateDirection = Direction.recognize(dx, dy);

								if (currentPoint.direction != candidateDirection)//a turn is considered as action and requires its own iteration
								{
									map[candidateY][candidateX] = generation + 2;
									generations.get(generation + 2).add(new Point(candidateX, candidateY, candidateDirection));//enqueue
								}
								else
								{
									map[candidateY][candidateX] = generation + 1;
									generations.get(generation + 1).add(new Point(candidateX, candidateY, candidateDirection));//enqueue
								}
							}
						}
					}
				}
			}
		}

		int x = callback.x;
		int y = callback.y;

		LinkedList<Direction> directions = new LinkedList<>();

		for (int value = map[y][x]; value > 0; )
		{
			int dx = 0;
			int dy = 0;

			for(int requiredValue = value - 1; dx == 0 && dy == 0; requiredValue--)
			{
				for (int i = 0; i < 9; i++)
				{
					if (i % 2 == 0)
					{
						continue;
					}

					if (map[y + (i / 3) - 1][x + (i % 3) - 1] == requiredValue)
					{
						dx = (i % 3) - 1;
						dy = (i / 3) - 1;
					}
				}
			}

			directions.addFirst(Direction.recognize(-dx, -dy));

			if (value - map[y + dy][x + dx] == 2)
			{
				directions.addFirst(Direction.recognize(-dx, -dy));
			}

			x += dx;
			y += dy;
			value = map[y][x];
		}

		callback.route = directions;
		callback.valid = true;
	}
}
