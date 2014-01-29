package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;

import java.util.*;

public class SearchTankStrategy
{
	public static class Result
	{
		private List<int[]> tankParts;
		private List<int[]> environment;

		private Result()
		{
			invalidate();
		}

		public List<int[]> getTankParts()
		{
			return tankParts;
		}

		public List<int[]> getEnvironment()
		{
			return environment;
		}

		private void invalidate()
		{
			tankParts = null;
			environment = null;
		}
	}

	public Result getCallback()
	{
		return new Result();
	}

	public void run(int tankX, int tankY, Direction tankDirection, Field.State state, Result callback)
	{
		int[][] map = new int[state.height()][state.width()];

		for (int[] line : map)
		{
			for (int j = 0; j < line.length; j++)
			{
				line[j] = -1;
			}
		}

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

		Queue<Point> candidates = new LinkedList<>();
		
		for (Direction direction: Direction.values())
		{
			candidates.add(new Point(tankX, tankY, direction));
		}
		
		map[tankY][tankX] = 0;

		while (!candidates.isEmpty())
		{
			Point currentPoint = candidates.remove();

			for (int i = -1; i <= 1; i++)
			{
				int candidateX = currentPoint.x + (currentPoint.direction.isVertical() ? i : currentPoint.direction.getDx());
				int candidateY = currentPoint.y + (currentPoint.direction.isHorisontal() ? i : currentPoint.direction.getDy());

				if (candidateX < 0 || candidateX >= state.width() || candidateY < 0 || candidateY >= state.height())
				{
					continue;
				}

				if ((map[candidateY][candidateX] > map[currentPoint.y][currentPoint.x] + 1 ||//if this way is shorter than existing
						map[candidateY][candidateX] == -1) &&
						state.at(candidateX, candidateY) != Cell.Type.WALL)//or there isn't any
				{
					map[candidateY][candidateX] = map[currentPoint.y][currentPoint.x] + 1;
					candidates.add(new Point(candidateX, candidateY, currentPoint.direction));//enqueue
				}
			}
		}

		int x = -1;
		int y = -1;
		int currentMetric = -1;

		int dx = tankDirection.getDx();
		int dy = tankDirection.getDy();

		for (int i = 0; i < state.width(); i++)
		{
			for (int j = 0; j < state.height(); j++)
			{
				if (state.at(i, j) == Cell.Type.TANK &&//it is tank
						map[j][i] != -1 && //I see you
						(
							map[j][i] < currentMetric ||
							currentMetric == -1
						) &&//the first or the nearest
						!(
							Math.abs(i - tankX) <= 1 &&
							Math.abs(j - tankY) <= 1 &&
							!(
								(
									tankDirection.isHorisontal() &&
									dx == i - tankX &&
									j - tankY != 0
								) ||
								(
									tankDirection.isVertical() &&
									dy == j - tankY &&
									i - tankX != 0
								)
							)
						)
					)//not myself
				{
					currentMetric = map[j][i];
					x = i;
					y = j;
				}
			}
		}

		Set<int[]> visited = new TreeSet<>(new Comparator<int[]>()
		{
			@Override
			public int compare(int[] o1, int[] o2)
			{
				for (int i = 0; i < o1.length; i++)
				{
					if (o1[i] != o2[i])
					{
						return o1[i] - o2[i];
					}
				}

				return 0;
			}
		});

		List<int[]> tankPoints = new LinkedList<>();

		if (currentMetric != -1)
		{
			Queue<int[]> queue = new LinkedList<>();

			queue.add(new int[]{x, y});
			visited.add(new int[]{x, y});

			while (!queue.isEmpty())
			{
				int[] currentPoint = queue.remove();

				x = currentPoint[0];
				y = currentPoint[1];

				int left = x == 0 ? 0 : -1;
				int right = x == state.width() - 1 ? 0 : 1;
				int up = y == 0 ? 0 : -1;
				int down = y == state.height() - 1 ? 0 : 1;

				for (int i = left; i <= right; i++)
				{
					for (int j = up; j <= down; j++)
					{
						if (state.at(x + i, y + j) == Cell.Type.TANK &&
							map[y + j][x + i] != -1 &&
							!(Math.abs(x + i - tankX) <= 1 && Math.abs(y + j - tankY) <= 1 &&
							!((tankDirection.isHorisontal() && dx == x + i - tankX && y + j - tankY != 0) ||
							(tankDirection.isVertical() && dy == y + j - tankY && x + i - tankX != 0))))
						{
							int[] visit = new int[]{x + i, y + j};

							if (!visited.contains(visit))
							{
								queue.add(visit);
								visited.add(visit);
							}
						}
					}
				}

				tankPoints.add(currentPoint);
			}
		}

		List<int[]> environment = new LinkedList<>();
		visited.clear();

		for (int[] currentPoint : tankPoints)
		{
			x = currentPoint[0];
			y = currentPoint[1];

			int left = x == 0 ? 0 : -1;
			int right = x == state.width() - 1 ? 0 : 1;
			int up = y == 0 ? 0 : -1;
			int down = y == state.height() - 1 ? 0 : 1;

			for (int i = left; i <= right; i++)
			{
				for (int j = up; j <= down; j++)
				{
					if (state.at(x + i, y + j) != Cell.Type.TANK && map[y + j][x + i] != -1)
					{
						int[] visit = new int[]{x + i, y + j};

						if (!visited.contains(visit))
						{
							visited.add(visit);
							environment.add(visit);
						}
					}
				}
			}
		}

		callback.tankParts = tankPoints;
		callback.environment = environment;
	}
}
