package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Tank;

public class TankView
{
	private CellView[][] tank;

	TankView(Tank origin)
	{
		int x = origin.getX();
		int y = origin.getY();
		Direction direction = origin.getDirection();

		int dx = direction.dx;
		int dy = direction.dy;

		tank = new CellView[3][3];

		for (int j = -1; j <= 1; j++)//y
		{
			for (int i = -1; i <= 1; i++)//x
			{
				if (!((dx == 0 && i == dy && j != 0) || (dy == 0 && i != 0 && j == dx)))
				{
					tank[i + 1][j + 1] = new CellView(CellView.Type.TANK, x + i, y + j);
				}
				else
				{
					tank[i + 1][j + 1] = null;
				}
			}
		}
	}

	CellView at(int x, int y)
	{
		return tank[y][x];
	}
}
