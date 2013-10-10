package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Field;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.view.activities.UpdateBulletViewTask;
import ru.nsu.fit.djachenko.mytanks.view.activities.UpdateTankViewTask;
import ru.nsu.fit.djachenko.mytanks.view.activities.ViewTaskPerformer;

import javax.swing.*;
import java.awt.*;

public class FieldView extends JPanel
{
	private ViewTaskPerformer performer;

	FieldView(Field origin, ViewTaskPerformer performer)
	{
		this.performer = performer;

		initUI(origin);
	}

	private void initUI(Field origin)
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
		super.add(tank);
		setComponentZOrder(tank, 0);
		repaint();

		performer.enqueue(new UpdateTankViewTask(this, tank));
	}

	public void add(BulletView bullet)
	{
		super.add(bullet);
		setComponentZOrder(bullet, 0);
		repaint();

		performer.enqueue(new UpdateBulletViewTask(bullet, this));
	}
}
