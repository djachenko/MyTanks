package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.DirectedPoint;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Point;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.Imperative;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.ImperativeFactory;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestWaySubStrategy
{
	private static final ImperativeFactory factory = ImperativeFactory.getInstance();

	private final FindNearestPointStrategy findStrategy = new FindNearestPointStrategy();

	Imperative getNextMove(DirectedPoint directedPoint, Field.State fieldState, boolean[][] mask)
	{
		return factory.getMoveImperative(getRoute(directedPoint, fieldState, mask).element());
	}

	Queue<Direction> getRoute(DirectedPoint directedPoint, Field.State fieldState, boolean[][] mask)
	{
		FindNearestPointStrategy.Result result = findStrategy.run(directedPoint, fieldState, mask);

		System.out.println("valid " + result.isValid() + " " + result.getPoint().getX() + " " + result.getPoint().getY());

		Point resultPoint = result.getPoint();

		int destX = resultPoint.getX();
		int destY = resultPoint.getY();
		int[][] distances = result.getDistances();

		LinkedList<Direction> route = new LinkedList<>();

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

			if (distances[destY][destX] - distances[destY - dy][destX - dx] == 1)
			{
				route.addFirst(currentDirection);

				destX -= currentDirection.getDx();
				destY -= currentDirection.getDy();
			}
			else//we know that tank came to current point from this candidate, so candidate can't be -1. the only what matters is way it came here
			{
				route.addFirst(currentDirection);
				route.addFirst(currentDirection);

				destX -= currentDirection.getDx();
				destY -= currentDirection.getDy();

				currentDirection = null;
			}
		}

		return route;
	}
}
