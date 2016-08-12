package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.Constants;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

import javax.swing.*;

class TankView extends JLabel
{
	private final CellView[][] tank;

	private Direction currentDirection;

	static enum Team
	{
		FIRST,
		SECOND,
		OTHER
	}

	TankView(int x, int y, Direction direction, Team team)
	{
		this.currentDirection = direction;

		tank = new CellView[3][3];

		initUI(x, y, team);
	}

	private void initUI(int x, int y, Team team)
	{
		setLayout(null);

		int dx = currentDirection.getDx();
		int dy = currentDirection.getDy();

		CellView.Type type;

		switch (team)
		{
			case FIRST:
				type = CellView.Type.MYTANK;
				break;
			case SECOND:
				type = CellView.Type.FRIENDTANK;
				break;
			default:
				type = CellView.Type.TANK;
		}

		for (int i = -1; i <= 1; i++)//y
		{
			for (int j = -1; j <= 1; j++)//x
		 	{
				if (!((currentDirection.isVertical() && i == dy && j != 0) || (currentDirection.isHorisontal() && i != 0 && j == dx)))
				{
					tank[i + 1][j + 1] = new CellView(type, 1 + j, 1 + i);
					add(tank[i + 1][j + 1]);
				}
				else
				{
					tank[i + 1][j + 1] = null;
				}
			}
		}

		setBounds(Constants.CELLVIEWGRIDSIZE * (x - 1), Constants.CELLVIEWGRIDSIZE * (y - 1), Constants.CELLVIEWGRIDSIZE * 3, Constants.CELLVIEWGRIDSIZE * 3);
		setLocation((x - 1) * Constants.CELLVIEWGRIDSIZE, (y - 1) * Constants.CELLVIEWGRIDSIZE);
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

			for (int i = 0; i < Constants.CELLVIEWGRIDSIZE; i++)
			{
				setLocation(getX() + dx, getY() + dy);
			}
		}
	}
}
