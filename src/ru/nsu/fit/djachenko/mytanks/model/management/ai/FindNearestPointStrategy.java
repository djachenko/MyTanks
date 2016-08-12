package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.DirectedPoint;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Point;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class FindNearestPointStrategy
{
	public static class Result
	{
		private Point point;

		private final int[][] distances;

		private final boolean valid;

		private Result(int x, int y, int[][] distances, boolean valid)
		{
			this(new Point(x, y), distances, valid);
		}

		private Result(Point point, int[][] distances, boolean valid)
		{
			this.point = point;

			this.distances = distances;
			this.valid = valid;
		}

		Point getPoint()
		{
			return point;
		}

		int[][] getDistances()
		{
			return distances;
		}

		boolean isValid()
		{
			return valid;
		}
	}

	public Result run(DirectedPoint directedPoint, Field.State fieldState, boolean[][] mask)
	{
		int[][] map = new int[fieldState.height()][fieldState.width()];

		for (int[] line : map)
		{
			for (int j = 0; j < line.length; j++)
			{
				line[j] = -1;
			}
		}

		Vector<Queue<DirectedPoint>> generations = new Vector<>();

		generations.add(0, new LinkedList<DirectedPoint>());
		generations.add(1, new LinkedList<DirectedPoint>());

		generations.get(0).add(new DirectedPoint(directedPoint.getX(), directedPoint.getY(), directedPoint.getDirection()));
		map[directedPoint.getY()][directedPoint.getX()] = 0;

		DirectedPoint destination = new DirectedPoint(-1, -1, null);

		boolean found = false;

		for (int generation = 0; !found; generation++)
		{
			Queue<DirectedPoint> candidates = generations.get(generation);

			if (candidates.size() == 0 && generations.get(generation + 1).size() == 0)
			{
				break;
			}

			generations.add(generation + 2, new LinkedList<DirectedPoint>());

			for (DirectedPoint currentPoint : candidates)
			{
				if (!found && mask[currentPoint.getY()][currentPoint.getX()])//check if there is other one
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
						
						int candidateX = currentPoint.getX() + dx;
						int candidateY = currentPoint.getY() + dy;
	
						if (candidateX < 1 || candidateX >= fieldState.width() - 1 || candidateY < 1 || candidateY >= fieldState.height() - 1)
						{
							continue;
						}

						boolean flag = false;
	
						for (int i = -1; i <= 1; i++)
						{
							for (int j = -1; j <= 1; j++)
							{
								if (fieldState.at(candidateX + i, candidateY + j) == Cell.Type.WALL)/* ||
								    fieldState.at(candidateX + i, candidateY + j) == Cell.Type.TANK)*///todo: complex analysis for being same
								{
									flag = true;
								}
							}
						}

						if (!flag)
						{
							if (map[candidateY][candidateX] > map[currentPoint.getY()][currentPoint.getX()] + 1 ||//if this way is shorter than existing
								map[candidateY][candidateX] == -1)//or there isn't any
							{
								if (currentPoint.getDirection() != direction)//a turn is considered as action and requires its own iteration
								{
									map[candidateY][candidateX] = generation + 2;
									generations.get(generation + 2).add(new DirectedPoint(candidateX, candidateY, direction));//enqueue
								}
								else
								{
									map[candidateY][candidateX] = generation + 1;
									generations.get(generation + 1).add(new DirectedPoint(candidateX, candidateY, direction));//enqueue
								}
							}

							if (currentPoint.getDirection() == direction && map[candidateY][candidateX] == map[currentPoint.getY()][currentPoint.getX()] + 1)
							{
								generations.get(generation + 1).add(new DirectedPoint(candidateX, candidateY, direction));//enqueue
							}
						}
					}
				}
			}
		}

		return new Result(destination.getX(), destination.getY(), map, found);
	}
}
