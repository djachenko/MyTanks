package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

import java.util.LinkedList;
import java.util.Queue;

public class BuildShortestWayStrategy
{
	public static class Result
	{
		private Queue<Direction> route;

		public Direction getNextMove()
		{
			if (route == null || route.size() == 0)
			{
				return null;
			}

			return route.remove();
		}

		public Queue<Direction> getRoute()
		{
			Queue<Direction> temp = route;

			route = null;

			return temp;
		}
	}

	public Result getCallback()
	{
		return new Result();
	}

	void run(int destX, int destY, int[][] distances, Result callback)
	{
		LinkedList<Direction> directions = new LinkedList<>();

		int dx;
		int dy;

		Direction currentDirection = null;

		while (distances[destY][destX] != 0)
		{
			if (currentDirection == null)
			{
				dx = 0;
				dy = 0;

				for (Direction direction : Direction.values())
				{
					dx = direction.getDx();
					dy = direction.getDy();

					if (distances[destY - dy][destX - dx] != -1 &&
							distances[destY - dy][destX - dx] < distances[destY][destX])
					{
						dx = direction.getDx();
						dy = direction.getDy();

						break;
					}
				}

				currentDirection = Direction.recognize(dx, dy);
			}

			dx = currentDirection.getDx();
			dy = currentDirection.getDy();

			if (distances[destY - dy][destX - dx] != -1 &&
					distances[destY][destX] - distances[destY - dy][destX - dx] == 1)
			{
				directions.addFirst(currentDirection);

				destX -= currentDirection.getDx();
				destY -= currentDirection.getDy();
			}
			else
			{
				directions.addFirst(currentDirection);
				directions.addFirst(currentDirection);

				destX -= currentDirection.getDx();
				destY -= currentDirection.getDy();

				currentDirection = null;
			}
		}
		callback.route = directions;
	}
}
