package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Tank;

import javax.swing.*;

public class TankView extends JLabel
{
	private CellView[][] tank;

	private Tank origin;

	private int x;
	private int y;
	private Direction currentDirection;

	TankView(int x, int y, Direction direction)
	{
		this.x = x;
		this.y = y;
		this.currentDirection = direction;

		tank = new CellView[3][3];

		initUI();
	}

	private void initUI()
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

	public void iteration()
	{
		if(origin.isAlive())
		{
			if (currentDirection != origin.getDirection())
			{
				Direction originDirection = origin.getDirection();
				int dx = originDirection.getDx();
				int dy = originDirection.getDy();

				if (currentDirection == originDirection.opposite())
				{
					tank[1 + dx][1 + dy].move(currentDirection);
					tank[1 - dx][1 - dy].move(currentDirection);
					tank[1 + dy - dx][1 + dx - dy].move(currentDirection);
					tank[1 + dy + dx][1 + dx + dy].move(currentDirection);

					tank[1 + currentDirection.getDy() - dx][1 + currentDirection.getDx() - dy] = tank[1 - dx][1 - dy];
					tank[1 + currentDirection.getDy() + dx][1 + currentDirection.getDx() + dy] = tank[1 + dx][1 + dy];
					tank[1 - dx][1 - dy] = tank[1 - currentDirection.getDy() - dx][1 - currentDirection.getDx() - dy];
					tank[1 + dx][1 + dy] = tank[1 - currentDirection.getDy() + dx][1 - currentDirection.getDx() + dy];
					tank[1 - currentDirection.getDy() - dx][1 - currentDirection.getDx() - dy] = null;
					tank[1 - currentDirection.getDy() + dx][1 - currentDirection.getDx() + dy] = null;
				}
				else
				{
					tank[1 - dy][1 - dx].move(currentDirection);//middle left
					tank[1 - dy - currentDirection.getDy()][1 - dx - currentDirection.getDx()].move(currentDirection);//up left
					tank[1 - currentDirection.getDy()][1 - currentDirection.getDx()].move(originDirection.opposite());//middle up
					tank[1 + dy - currentDirection.getDy()][1 + dx - currentDirection.getDx()].move(originDirection.opposite());//right up

					tank[1 + currentDirection.getDy() - dy][1 + currentDirection.getDx() - dx] = tank[1 - dy][1 - dx];
					tank[1 - dy][1 - dx] = tank[1 - dy - currentDirection.getDy()][1 - dx - currentDirection.getDx()];
					tank[1 - dy - currentDirection.getDy()][1 - dx - currentDirection.getDx()] = tank[1 - currentDirection.getDy()][1 - currentDirection.getDx()];
					tank[1 - currentDirection.getDy()][1 - currentDirection.getDx()] = tank[1 + dy - currentDirection.getDy()][1 + dx - currentDirection.getDx()];
					tank[1 + dy - currentDirection.getDy()][1 + dx - currentDirection.getDx()] = null;
				}

				currentDirection = originDirection;
			}

			if (x != origin.getX() || y != origin.getY())//moved
			{
				int dx = origin.getX() - x;
				int dy = origin.getY() - y;

				for (int i = 0; i < CellView.SIZE; i++)
				{
					setLocation(getX() + dx, getY() + dy);
				}

				x += dx;
				y += dy;
			}
		}
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

	public boolean isAlive()
	{
		return origin.isAlive();
	}
}
