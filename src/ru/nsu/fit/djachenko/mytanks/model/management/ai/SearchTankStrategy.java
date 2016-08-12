package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

import java.util.*;

public class SearchTankStrategy
{
	public static class Result
	{
		private Set<int[]> tankParts;

		private Result()
		{
			tankParts = null;
		}

		private Result(Set<int[]> tankParts)
		{
			this.tankParts = tankParts;
		}

		public Set<int[]> getTankParts()
		{
			return tankParts;
		}
	}

	private static class Point
	{
		private int x;
		private int y;
		private Direction direction;

		Point(int x, int y, Direction direction)
		{
			this.x = x;
			this.y = y;
			this.direction = direction;
		}

		int getX()
		{
			return x;
		}

		int getY()
		{
			return y;
		}
	}

	private static interface Condition
	{
		boolean scipCase(Cell.Type type);
		boolean stopCase();
	}

	//Set<int[]> scanNearest(Result initialPoint, Tank.State tankState, Field.State fieldState, Condition condition)

	Result run(Tank.State tankState, Field.State fieldState)
	{
		Set<Point> result = scanForCondition(
			new Point(tankState.getX(), tankState.getY(), null),
			tankState.getDirection(),
			fieldState,
			new Condition()
			{
			 @Override
			 public boolean scipCase(Cell.Type type)
			 {
			     return type == Cell.Type.WALL;
			 }

			 @Override
			 public boolean stopCase()
			 {
			     return true;
			 }
			});

		if (result.size() != 1)
		{
			throw new NullPointerException();//todo: change
		}
		else
		{
			Point point = null;

			for (Point i : result)
			{
				point = i;
			}

			result = scanForCondition(
				point,
				tankState.getDirection(),
				fieldState,
				new Condition()
				{
					@Override
					public boolean scipCase(Cell.Type type)
					{
						return type != Cell.Type.TANK;
					}

					@Override
					public boolean stopCase()
					{
						return false;
					}
				});

			Set<int[]> returning = new HashSet<>();

			for (Point i : result)
			{
				returning.add(new int[]{i.getX(), i.getY()});
			}

			return new Result(returning);
		}
	}

	private Set<Point> scanForCondition(Point initialPoint, Direction tankDirection, Field.State fieldState, Condition condition)
	{
		int x = initialPoint.getX();
		int y = initialPoint.getY();

		int[][] map = new int[fieldState.height()][fieldState.width()];

		for (int[] line : map)
		{
			for (int j = 0; j < line.length; j++)
			{
				line[j] = -1;
			}
		}

		Queue<Point> candidates = new LinkedList<>();
		
		for (Direction direction: Direction.values())
		{
			candidates.add(new Point(x, y, direction));
		}
		
		map[y][x] = 0;

		int dx = tankDirection.getDx();
		int dy = tankDirection.getDy();

		Set<Point> result = new HashSet<>();

		while (!candidates.isEmpty())
		{
			Point currentPoint = candidates.remove();

			if (currentPoint.x < 0 ||
			    currentPoint.x >= fieldState.width() ||
			    currentPoint.y < 0 ||
			    currentPoint.y >= fieldState.height() &&
			    condition.scipCase(fieldState.at(currentPoint.x, currentPoint.y)))
			{
				continue;
			}

			if
			(
				fieldState.at(currentPoint.x, currentPoint.y) == Cell.Type.TANK &&//it is tank
				(
					Math.abs(currentPoint.x - x) <= 1 &&
					Math.abs(currentPoint.y - y) <= 1 &&
					!(
						(
							tankDirection.isHorisontal() &&
							dx == currentPoint.x - x &&
							currentPoint.y - y != 0
						) ||
						(
							tankDirection.isVertical() &&
							dy == currentPoint.y - y &&
							currentPoint.x - x != 0
						)
					)
				)
			)//not myself
			{
				result.add(currentPoint);

				if (condition.stopCase())
				{
					return result;
				}
			}

			for (int delta = -1; delta <= 1; delta++)
			{
				int candidateX = currentPoint.x + (currentPoint.direction.isVertical() ? delta : currentPoint.direction.getDx());
				int candidateY = currentPoint.y + (currentPoint.direction.isHorisontal() ? delta : currentPoint.direction.getDy());

				if ((map[candidateY][candidateX] > map[currentPoint.y][currentPoint.x] + 1 ||//if this way is shorter than existing
						map[candidateY][candidateX] == -1))//or there isn't any
				{
					map[candidateY][candidateX] = map[currentPoint.y][currentPoint.x] + 1;
					candidates.add(new Point(candidateX, candidateY, currentPoint.direction));//enqueue
				}
			}
		}

		return result;
	}
}