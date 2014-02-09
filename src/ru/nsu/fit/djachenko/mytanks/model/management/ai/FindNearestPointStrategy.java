package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class FindNearestPointStrategy
{
	public static class Result
	{
		private int x;
		private int y;

		private int[][] distances;

		private boolean found;

		private Result()
		{
			invalidate();
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		int[][] getDistances()
		{
			return distances;
		}

		boolean isFound()
		{
			return found;
		}

		private void invalidate()
		{
			x = -1;
			y = -1;

			distances = null;

			found = false;
		}
	}

	public Result getCallback()
	{
		return new Result();
	}

	public void run(Tank.State tankState, Field.State fieldState, boolean[][] mask, Result callback)
	{
		int[][] map = new int[fieldState.height()][fieldState.width()];

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

		Vector<Queue<Point>> generations = new Vector<>();

		generations.add(0, new LinkedList<Point>());
		generations.add(1, new LinkedList<Point>());

		generations.get(0).add(new Point(tankState.getX(), tankState.getY(), tankState.getDirection()));
		map[tankState.getY()][tankState.getX()] = 0;

		Point destination = new Point(-1, -1, null);

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
				if (!found && mask[currentPoint.y][currentPoint.x])//check if there is other one
				{
					found = true;

					destination = currentPoint;
				}
				else
				{
					for (Direction direction : Direction.values())
					{
						int dx = direction.getDx();
						int dy = direction.getDy();
						
						int candidateX = currentPoint.x + dx;
						int candidateY = currentPoint.y + dy;
	
						if (candidateX < 1 || candidateX >= fieldState.width() - 1 || candidateY < 1 || candidateY >= fieldState.height() - 1)
						{
							continue;
						}

						boolean flag = false;
	
						for (int i = -1; i <= 1; i++)
						{
							for (int j = -1; j <= 1; j++)
							{
								if (fieldState.at(candidateX + i, candidateY + j) == Cell.Type.WALL) //||
									//	fieldState.at(candidateX + i, candidateY + j) == Cell.Type.TANK)
								{
									flag = true;
								}
							}
						}

						if (!flag)
						{
							if (map[candidateY][candidateX] > map[currentPoint.y][currentPoint.x] + 1 ||//if this way is shorter than existing
								map[candidateY][candidateX] == -1)//or there isn't any
							{
								if (currentPoint.direction != direction)//a turn is considered as action and requires its own iteration
								{
									map[candidateY][candidateX] = generation + 2;
									generations.get(generation + 2).add(new Point(candidateX, candidateY, direction));//enqueue
								}
								else
								{
									map[candidateY][candidateX] = generation + 1;
									generations.get(generation + 1).add(new Point(candidateX, candidateY, direction));//enqueue
								}
							}

							if (currentPoint.direction == direction && map[candidateY][candidateX] == map[currentPoint.y][currentPoint.x] + 1)
							{
								generations.get(generation + 1).add(new Point(candidateX, candidateY, direction));//enqueue
							}
						}
					}
				}
			}
		}

		callback.x = destination.x;
		callback.y = destination.y;
		callback.distances = map;
		callback.found = true;
	}
}
