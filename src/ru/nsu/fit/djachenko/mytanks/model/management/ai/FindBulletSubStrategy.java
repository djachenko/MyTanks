package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.DistancedPoint;
import ru.nsu.fit.djachenko.mytanks.model.Point;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;

import java.util.LinkedList;
import java.util.Queue;

public class FindBulletSubStrategy
{
	static class Result extends DistancedPoint
	{
		private boolean valid;

		private Result()
		{
			super(-1, -1, null, -1);
			this.valid = false;
		}

		private Result(int x, int y, Direction direction, int distance)
		{
			super(x, y, direction, distance);
			this.valid = true;
		}

		boolean isValid()
		{
			return valid;
		}
	}

	Result run(Point point, Field.State fieldState)
	{
		int startX = point.getX();
		int startY = point.getY();

		Queue<Result> candidates = new LinkedList<>();

		for (int i = -1; i <= 1; i++)
		{
			candidates.add(new Result(startX - 1, startY + i, Direction.LEFT, 1));
			candidates.add(new Result(startX + 1, startY + i, Direction.RIGHT, 1));
			candidates.add(new Result(startX + i, startY + 1, Direction.DOWN, 1));
			candidates.add(new Result(startX + i, startY - 1, Direction.UP, 1));
		}

		int width = fieldState.width();
		int height = fieldState.height();

		while (!candidates.isEmpty())
		{
			Result currentPoint = candidates.remove();

			if (currentPoint.getX() >= 0 &&
			    currentPoint.getX() < width &&
			    currentPoint.getY() >= 0 &&
			    currentPoint.getY() < height &&
			    fieldState.at(currentPoint.getX(), currentPoint.getY()) != Cell.Type.WALL)
			{
				if (fieldState.at(currentPoint.getX(), currentPoint.getY()) == Cell.Type.BULLET)
				{
					return currentPoint;
				}
				else
				{
					candidates.add(new Result(currentPoint.getX() + currentPoint.getDirection().getDx(),
					                         currentPoint.getY() + currentPoint.getDirection().getDy(),
					                         currentPoint.getDirection(),
					                         currentPoint.getDistance() + 1));
				}
			}
		}

		return new Result();
	}
}