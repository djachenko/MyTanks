package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;

public class BulletScanStrategy
{
	private boolean result = false;
	private Direction runDirection = null;

	public void action(int playerId, int tankX, int tankY, Direction tankDirection, Field.State state)
	{
		int stateWidth = state.width();
		int stateHeight = state.height();

		for (int i = tankX; i >= 0 && !result; i--)
		{
			for (int j = -1; j <= 1 && !result; j++)
			{
				if (state.at(i, tankY + j) == Cell.Type.BULLET)
				{
					result = true;

					runDirection = Direction.RIGHT;
				}
			}
		}

		for (int i = tankX; i < stateWidth && !result; i++)
		{
			for (int j = -1; j <= 1 && !result; j++)
			{
				if (state.at(i, tankY + j) == Cell.Type.BULLET)
				{
					result = true;

					runDirection = Direction.LEFT;
				}
			}
		}

		for (int j = tankY; j >= 0 && !result; j--)
		{
			for (int i = -1; i <= 1 && !result; i++)
			{
				if (state.at(tankX + i, j) == Cell.Type.BULLET)
				{
					result = true;

					runDirection = Direction.DOWN;
				}
			}
		}

		for (int j = tankY; j < stateHeight && !result; j++)
		{
			for (int i = -1; i <= 1 && !result; i++)
			{
				if (state.at(tankX + i, j) == Cell.Type.BULLET)
				{
					result = true;

					runDirection = Direction.UP;
				}
			}
		}
	}

	public Direction getRunDirection()
	{
		return runDirection;
	}

	public boolean getResult()
	{
		return result;
	}
}
