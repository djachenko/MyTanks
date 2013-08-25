package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;

import javax.swing.*;
import java.awt.*;

public class LevelView extends JPanel
{
	private final Level origin;

	LevelView(Level origin)
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
						add(new CellView(CellView.Type.GROUND, x, y));
						break;
					case TANK:
						add(new CellView(CellView.Type.TANK, x, y));
						break;
					case BULLET:
						add(new CellView(CellView.Type.GROUND, x, y));
					default:
						break;
				}
			}
		}

		setPreferredSize(new Dimension(width * CellView.GRIDSIZE, height * CellView.GRIDSIZE));
	}
}
