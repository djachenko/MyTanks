package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Field;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;

import javax.swing.*;
import java.awt.*;

public class FieldView extends JPanel
{
	private final Field origin;

	FieldView(Field origin)
	{
		this.origin = origin;

		initUI();
	}

	private void initUI()
	{
		setLayout(null);

		int width = origin.width();
		int height = origin.height();

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				Cell originCell = origin.at(x, y);

				switch (originCell.type)
				{
					case WALL:
						add(new CellView(CellView.Type.WALL, x, y));
						break;
					case GROUND:
					case TANK:
					case BULLET:
						add(new CellView(CellView.Type.GROUND, x, y));
						break;
					default:
						break;
				}
			}
		}

		setPreferredSize(new Dimension(width * CellView.GRIDSIZE, height * CellView.GRIDSIZE));
	}

	public void add(TankView tank)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				CellView cell = tank.at(i, j);

				if (cell != null)
				{
					System.out.println("add() " + cell.getY());
					add(cell);
					setComponentZOrder(cell, 0);
				}
			}
		}
	}
}
