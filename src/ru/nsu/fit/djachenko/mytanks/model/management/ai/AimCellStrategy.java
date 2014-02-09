package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

import java.util.Queue;

public class AimCellStrategy
{
	private static FindNearestPointStrategy findStrategy = new FindNearestPointStrategy();
	private static FindNearestPointStrategy.Result findCallback = findStrategy.getCallback();

	private static BuildShortestWayStrategy shortestWayStrategy = new BuildShortestWayStrategy();
	private static BuildShortestWayStrategy.Result shortestWayCallback = shortestWayStrategy.getCallback();

	static class Result
	{
		private Queue<Direction> route;

		private Result()
		{
			invalidate();
		}

		private void invalidate()
		{
			route = null;
		}

		Queue<Direction> getRoute()
		{
			return route;
		}

		Direction getNextMove()
		{
			return route.remove();
		}
	}

	static Result getCallback()
	{
		return new Result();
	}

	public void run(int cellX, int cellY, Tank.State tankState, Field.State fieldState, Result callback)
	{
		if (callback.route != null && callback.route.size() != 0)
		{
			return;
		}

		boolean[][] availability = new boolean[fieldState.height()][fieldState.width()];

		for (Direction direction : Direction.values())
		{
			int x = cellX;
			int y = cellY;

			while (x >= 0 && x < fieldState.width() && y >= 0 && y < fieldState.height() && fieldState.at(x, y) != Cell.Type.WALL)
			{
				availability[y][x] = true;

				x += direction.getDx();
				y += direction.getDy();
			}

			availability[y - direction.getDy()][x - direction.getDx()] = false;
		}

		findStrategy.run(tankState, fieldState, availability, findCallback);

		if (findCallback.isFound())
		{
			shortestWayStrategy.run(findCallback.getX(), findCallback.getY(), findCallback.getDistances(), shortestWayCallback);

			Direction destDirection = Direction.recognize(cellX - findCallback.getX(), cellY - findCallback.getY());

			callback.route = shortestWayCallback.getRoute();

			if (callback.route != null)
			{
				Direction last = null;
				
				for (Direction direction : callback.route)
				{
					last = direction;
				}
				
				if (last != null && last != destDirection)
				{
					//callback.route.add(destDirection);
				}
			}
		}
	}
}
