package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

import java.util.Queue;

public class AimCellStrategy
{
	private static ShortestWaySubStrategy shortestWayStrategy = new ShortestWaySubStrategy();

	public void run(int cellX, int cellY, Tank.State tankState, Field.State fieldState)
	{
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

		Queue<Direction> route = shortestWayStrategy.getRoute(tankState, fieldState, availability);

		Direction last = null;

		int lastX = tankState.getX();
		int lastY = tankState.getY();

		for (Direction direction : route)
		{
			last = direction;

			lastX += direction.getDx();
			lastY += direction.getDy();
		}

		Direction destDirection = Direction.recognize(cellX - lastX, cellY - lastY);

		if (last != null && last != destDirection)
		{
			route.add(destDirection);
		}
	}
}
