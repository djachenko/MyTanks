package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

import java.util.*;

public class RecognizeTankStrategy
{
	static class Result
	{
		static class TankVariant
		{
			int x;
			int y;
			Direction direction;

			TankVariant(int x, int y, Direction direction)
			{
				this.direction = direction;
				this.x = x;
				this.y = y;
			}
		}

		int rating;

		private List<TankVariant> variants;

		public List<TankVariant> getVariants()
		{
			return variants;
		}
	}

	Result getCallback()
	{
		return new Result();
	}

	void run(SearchTankStrategy.Result result, Result callback)
	{
		List<int[]> tankPoints = result.getTankParts();

		if (tankPoints.size() == 0)
		{
			return;
		}

		int[] min = new int[2];
		int[] dimension = new int[2];

		for (int i = 0; i < min.length; i++)
		{
			min[i] = tankPoints.get(0)[i];
			dimension[i] = tankPoints.get(0)[i];
		}

		for (int[] point : tankPoints)
		{
			for (int i = 0; i < 2; i++)
			{
				if (point[i] < min[i])
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
			return;
		}

		boolean[][] model = new boolean[dimension[1]][dimension[0]];

		for (int[] point : tankPoints)
		{
			model[point[1] - min[1]][point[0] - min[0]] = true;
		}

		boolean[][] pattern = new boolean[][]{
				{true, true, true},
				{true, true, true},
				{true, true, true}};

		List<Result.TankVariant> variants = new LinkedList<>();

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
						variants.add(new Result.TankVariant(min[0] - dx + 1, min[1] - dy + 1, direction));
					}
				}
			}

			pattern[1 + direction.getDy() + direction.getDx()][1 + direction.getDy() + direction.getDx()] = true;
			pattern[1 + direction.getDy() - direction.getDx()][1 - direction.getDy() + direction.getDx()] = true;
		}

		callback.rating = 7 - tankPoints.size();
		callback.variants = variants;
	}
}