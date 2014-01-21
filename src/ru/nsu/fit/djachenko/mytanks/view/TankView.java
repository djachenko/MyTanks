package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

import javax.swing.*;

class TankView extends JLabel
{
	private final CellView[][] tank;

	private Direction currentDirection;

	TankView(int x, int y, Direction direction, boolean isFriend)
	{
		this.currentDirection = direction;

		tank = new CellView[3][3];

		initUI(x, y);
	}

	private void initUI(int x, int y)
	{
		setLayout(null);

		int dx = currentDirection.getDx();
		int dy = currentDirection.getDy();

		for (int i = -1; i <= 1; i++)//y
		{
			for (int j = -1; j <= 1; j++)//x
		 	{
				if (!((currentDirection.isVertical() && i == dy && j != 0) || (currentDirection.isHorisontal() && i != 0 && j == dx)))
				{
					tank[i + 1][j + 1] = new CellView(CellView.Type.TANK, 1 + j, 1 + i);
					add(tank[i + 1][j + 1]);
				}
				else
				{
					tank[i + 1][j + 1] = null;
				}
			}
		}

		setBounds(CellView.GRIDSIZE * (x - 1), CellView.GRIDSIZE * (y - 1), CellView.GRIDSIZE * 3, CellView.GRIDSIZE * 3);
		setLocation((x - 1) * CellView.GRIDSIZE, (y - 1) * CellView.GRIDSIZE);
	}

	public void move(Direction newDirection)
	{
		if (currentDirection != newDirection)
		{
			int newDx = newDirection.getDx();
			int newDy = newDirection.getDy();

			int currentDx = currentDirection.getDx();
			int currentDy = currentDirection.getDy();

			if (currentDirection == newDirection.opposite())
			{
				tank[1 + newDx][1 + newDy].move(currentDirection);
				tank[1 - newDx][1 - newDy].move(currentDirection);
				tank[1 + newDy - newDx][1 + newDx - newDy].move(currentDirection);
				tank[1 + newDy + newDx][1 + newDx + newDy].move(currentDirection);

				tank[1 + currentDy - newDx][1 + currentDx - newDy] = tank[1 - newDx][1 - newDy];
				tank[1 + currentDy + newDx][1 + currentDx + newDy] = tank[1 + newDx][1 + newDy];
				tank[1 - newDx][1 - newDy] = tank[1 - currentDy - newDx][1 - currentDx - newDy];
				tank[1 + newDx][1 + newDy] = tank[1 - currentDy + newDx][1 - currentDx + newDy];
				tank[1 - currentDy - newDx][1 - currentDx - newDy] = null;
				tank[1 - currentDy + newDx][1 - currentDx + newDy] = null;
			}
			else
			{
				tank[1 - newDy][1 - newDx].move(currentDirection);//middle left
				tank[1 - newDy - currentDy][1 - newDx - currentDx].move(currentDirection);//up left
				tank[1 - currentDy][1 - currentDx].move(newDirection.opposite());//middle up
				tank[1 + newDy - currentDy][1 + newDx - currentDx].move(newDirection.opposite());//right up

				tank[1 + currentDy - newDy][1 + currentDx - newDx] = tank[1 - newDy][1 - newDx];
				tank[1 - newDy][1 - newDx] = tank[1 - newDy - currentDy][1 - newDx - currentDx];
				tank[1 - newDy - currentDy][1 - newDx - currentDx] = tank[1 - currentDy][1 - currentDx];
				tank[1 - currentDy][1 - currentDx] = tank[1 + newDy - currentDy][1 + newDx - currentDx];
				tank[1 + newDy - currentDy][1 + newDx - currentDx] = null;
			}

			currentDirection = newDirection;
		}
		else
		{
			int dx = newDirection.getDx();
			int dy = newDirection.getDy();

			for (int i = 0; i < CellView.SIZE; i++)
			{
				setLocation(getX() + dx, getY() + dy);
			}
		}
	}
}
