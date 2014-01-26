package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;

class BulletScanStrategy
{
	static class Result
	{
		private Direction directionToBullet = null;
		private int x = -1;
		private int y = -1;
		private int distance = -1;
		private boolean found = false;

		private Result()
		{
			invalidate();
		}

		public Direction getDirectionToBullet()
		{
			return directionToBullet;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public int getDistance()
		{
			return distance;
		}

		boolean isFound()
		{
			return found;
		}

		private void set(int x, int y, int distance, Direction directionToBullet)
		{
			this.x = x;
			this.y = y;
			this.distance = distance;
			this.directionToBullet = directionToBullet;

			found = true;
		}

		private void invalidate()
		{
			directionToBullet = null;
			x = -1;
			y = -1;
			distance = -1;
			found = false;
		}
	}

	Result getCallback()
	{
		return new Result();
	}

	boolean run(int tankX, int tankY, Field.State state, Result callback)
	{
		int stateWidth = state.width();
		int stateHeight = state.height();

		int distance = Math.max(tankX, Math.max(tankY, Math.max(stateWidth - 1 - tankX, stateHeight - 1 - tankY)));

		callback.invalidate();

		for (int round = 1; round < distance && !callback.isFound(); round++)
		{
			for (int delta = -1; delta <= 1 && !callback.isFound(); delta++)
			{
				if (tankY - round >= 0 && state.at(tankX + delta, tankY - round) == Cell.Type.BULLET)
				{
					callback.set(tankX + delta, tankY - round, round, Direction.UP);

					break;
				}

				if (tankY + round < stateHeight && state.at(tankX + delta, tankY + round) == Cell.Type.BULLET)
				{
					callback.set(tankX + delta, tankY + round, round, Direction.DOWN);

					break;
				}

				if (tankX - round >= 0 && state.at(tankX - round, tankY + delta) == Cell.Type.BULLET)
				{
					callback.set(tankX - round, tankY + delta, round, Direction.LEFT);

					break;
				}

				if (tankX + round < stateWidth && state.at(tankX + round, tankY + delta) == Cell.Type.BULLET)
				{
					callback.set(tankX + round, tankY + delta, round, Direction.RIGHT);

					break;
				}
			}
		}

		return callback.isFound();
	}
}
