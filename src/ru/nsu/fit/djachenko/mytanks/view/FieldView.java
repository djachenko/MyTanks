package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Field;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.activities.TaskPerformer;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.view.activities.UpdateTankViewTask;

import javax.swing.*;
import java.awt.*;

public class FieldView extends JPanel
{
	private final Field origin;
	private TaskPerformer performer = new TaskPerformer();

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
						CellView cellView = new CellView(CellView.Type.WALL, x, y);
						add(cellView);
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
		/*for (int i = 0; i < 7; i++)
		{
			CellView cell = tank.at(i);

			if (cell != null)
			{
				System.out.println("add() " + cell.getY());
				add(cell);
				setComponentZOrder(cell, 0);
			}
		}*/

		super.add(tank);
		setComponentZOrder(tank, 0);

		performer.enqueue(new UpdateTankViewTask(tank));
	}

}
