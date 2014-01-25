package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;

class BulletScanStrategy
{
	private boolean result = false;
	private Direction runDirection = null;

	public void action(int playerId, int tankX, int tankY, Direction tankDirection, Field.State state)
	{
		int stateWidth = state.width();
		int stateHeight = state.height();

		int distance = Math.max(tankX, Math.max(tankY, Math.max(stateWidth - 1 - tankX, stateHeight - 1 - tankY)));

		for (int round = 1; round < distance; round++)
		{
			for (int i = -1; i <= 1 && !result; i++)
			{
				if (tankY - round >= 0 && state.at(tankX + i, tankY - round) == Cell.Type.BULLET)
				{
					result = true;

					runDirection = Direction.DOWN;

					break;
				}

				if (tankY + round < stateHeight && state.at(tankX + i, tankY + round) == Cell.Type.BULLET)
				{
					result = true;

					runDirection = Direction.UP;
				}
			}

			for (int j = -1; j <= 1 && !result; j++)
			{
				if (tankX - round >= 0 && state.at(tankX - round, tankY + j) == Cell.Type.BULLET)
				{
					result = true;

					runDirection = Direction.RIGHT;

					break;
				}

				if (tankX + round < stateWidth && state.at(tankX + round, tankY + j) == Cell.Type.BULLET)
				{
					result = true;

					runDirection = Direction.LEFT;
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
