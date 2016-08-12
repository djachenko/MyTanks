package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.DirectedPoint;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

import java.util.*;

public class RecognizeTankStrategy
{
	private static final boolean[][] pattern = new boolean[][]{
		{true, true, true},
		{true, true, true},
		{true, true, true}
	};

	static class Result extends DirectedPoint
	{
		private boolean valid;

		private static final Result invalid = new Result();

		private Result()
		{
			super(-1, -1, null);
			valid = false;
		}

		private Result(int x, int y, Direction direction)
		{
			super(x, y, direction);
			this.valid = true;
		}

		public boolean isValid()
		{
			return valid;
		}
	}

	Result run(Set<int[]> tankParts)
	{
		if (tankParts.size() == 0)
		{
			return null;//todo: throw
		}

		int[] min = new int[2];
		int[] dimension = new int[2];

		for (int i = 0; i < min.length; i++)
		{
			min[i] = -1;
			dimension[i] = -1;
		}

		for (int[] point : tankParts)
		{
			for (int i = 0; i < 2; i++)
			{
				if (point[i] < min[i] || min[i] == -1)
				{
					min[i] = point[i];
				}

				if (point[i] > dimension[i])
				{
					dimension[i] = point[i];
				}
			}
		}

		for (int i = 0; i < 2; i++)
		{
			dimension[i] -= (min[i] - 1);
		}

		if (dimension[0] > 3 || dimension[1] > 3)
		{
			return null;//todo: throw
		}

		boolean[][] model = new boolean[dimension[1]][dimension[0]];

		for (int[] point : tankParts)
		{
			model[point[1] - min[1]][point[0] - min[0]] = true;
		}

		for (Direction direction : Direction.values())
		{
			pattern[1 + direction.getDy() + direction.getDx()][1 + direction.getDy() + direction.getDx()] = false;
			pattern[1 + direction.getDy() - direction.getDx()][1 - direction.getDy() + direction.getDx()] = false;

			for (int dy = 0; dy <= 3 - dimension[1]; dy++)
			{
				for (int dx = 0; dx <= 3 - dimension[0]; dx++)
				{
					boolean match = true;

					for (int j = 0; match && j < model.length; j++)
					{
						for (int i = 0; match && i < model[j].length; i++)
						{
							if (model[j][i] && !pattern[j + dy][i + dx])
							{
								match = false;
							}
						}
					}

					if (match)
					{

						pattern[1 + direction.getDy() + direction.getDx()][1 + direction.getDy() + direction.getDx()] = true;
						pattern[1 + direction.getDy() - direction.getDx()][1 - direction.getDy() + direction.getDx()] = true;

						return new Result(-1, -1, null);
					}
				}
			}

			pattern[1 + direction.getDy() + direction.getDx()][1 + direction.getDy() + direction.getDx()] = true;
			pattern[1 + direction.getDy() - direction.getDx()][1 - direction.getDy() + direction.getDx()] = true;
		}

		return Result.invalid;
	}
}