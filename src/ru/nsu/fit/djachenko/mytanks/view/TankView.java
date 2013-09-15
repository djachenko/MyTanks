package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Tank;

import javax.swing.*;
import java.awt.*;

public class TankView extends JLabel
{
	private CellView[][] tank;

	private Tank origin;

	private int x;
	private int y;
	private Direction direction;

	TankView(Tank origin)
	{
		this.origin = origin;

		x = origin.getX();
		y = origin.getY();
		direction = origin.getDirection();

		tank = new CellView[3][3];

		initUI();
	}

	private void initUI()
	{
		setLayout(null);

		int dx = direction.dx;
		int dy = direction.dy;

		for (int i = -1; i <= 1; i++)//y
		{
			for (int j = -1; j <= 1; j++)//x
		 	{
				if (!((direction.isVertical() && i == dy && j != 0) || (direction.isHorisontal() && i != 0 && j == dx)))
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
		setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		setLocation((x - 1) * CellView.GRIDSIZE, (y - 1) * CellView.GRIDSIZE);
		System.out.println("loc: " + getX() + " " + getY());

		System.out.println("tankview: " + getComponentCount());
	}

	CellView at(int index)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (tank[i][j] != null)
				{
					if (index == 0)
					{
						return tank[i][j];
					}
					else
					{
						index--;
					}
				}
			}
		}

		return null;
	}

	public void iteration()
	{
		if (direction != origin.getDirection())
		{
			Direction originDirection = origin.getDirection();
			int dx = originDirection.dx;
			int dy = originDirection.dy;

			if (direction == originDirection.opposite())
			{
				tank[1 + dx][1 + dy].move(direction);
				tank[1 - dx][1 - dy].move(direction);
				tank[1 + dy - dx][1 + dx - dy].move(direction);
				tank[1 + dy + dx][1 + dx + dy].move(direction);

				tank[1 + direction.dy - dx][1 + direction.dx - dy] = tank[1 - dx][1 - dy];
				tank[1 + direction.dy + dx][1 + direction.dx + dy] = tank[1 + dx][1 + dy];
				tank[1 - dx][1 - dy] = tank[1 - direction.dy - dx][1 - direction.dx - dy];
				tank[1 + dx][1 + dy] = tank[1 - direction.dy + dx][1 - direction.dx + dy];
				tank[1 - direction.dy - dx][1 - direction.dx - dy] = null;
				tank[1 - direction.dy + dx][1 - direction.dx + dy] = null;
			}
			else
			{
				tank[1 - dy][1 - dx].move(direction);//middle left
				tank[1 - dy - direction.dy][1 - dx - direction.dx].move(direction);//up left
				tank[1 - direction.dy][1 - direction.dx].move(originDirection.opposite());//middle up
				tank[1 + dy - direction.dy][1 + dx - direction.dx].move(originDirection.opposite());//right up

				tank[1 + direction.dy - dy][1 + direction.dx - dx] = tank[1 - dy][1 - dx];
				tank[1 - dy][1 - dx] = tank[1 - dy - direction.dy][1 - dx - direction.dx];
				tank[1 - dy - direction.dy][1 - dx - direction.dx] = tank[1 - direction.dy][1 - direction.dx];
				tank[1 - direction.dy][1 - direction.dx] = tank[1 + dy - direction.dy][1 + dx - direction.dx];
				tank[1 + dy - direction.dy][1 + dx - direction.dx] = null;
			}

			direction = originDirection;
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
