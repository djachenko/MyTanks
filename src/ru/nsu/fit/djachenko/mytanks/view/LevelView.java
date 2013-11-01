package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.communication.AddControllerMessage;
import ru.nsu.fit.djachenko.mytanks.communication.DrawBulletMessage;
import ru.nsu.fit.djachenko.mytanks.communication.DrawTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.model.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.Tank;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.view.activities.UpdateBulletViewTask;
import ru.nsu.fit.djachenko.mytanks.view.activities.UpdateTankViewTask;
import ru.nsu.fit.djachenko.mytanks.view.activities.ViewTaskPerformer;

import javax.swing.*;
import java.awt.*;

public class LevelView extends JPanel
{
	private ViewTaskPerformer performer;

	LevelView(Level origin, ViewTaskPerformer performer)
	{
		this.performer = performer;

		initUI(origin);
	}

	private void initUI(Level origin)
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

		for (Tank tank : origin.getTanks())//REFACTOR maybe ViewFactory with type overload
		{
			add(new TankView(tank));
		}

		for (Bullet bullet : origin.getBullets())
		{
			add(new BulletView(bullet));
		}

		setPreferredSize(new Dimension(width * CellView.GRIDSIZE, height * CellView.GRIDSIZE));
	}

	private void add(TankView tank)
	{
		super.add(tank);
		setComponentZOrder(tank, 0);
		repaint();

		performer.enqueue(new UpdateTankViewTask(this, tank));
	}

	private void add(BulletView bullet)
	{
		super.add(bullet);
		setComponentZOrder(bullet, 0);
		repaint();

		performer.enqueue(new UpdateBulletViewTask(bullet, this));
	}

	public void accept(MessageToView message)
	{
		//throw ;
	}

	public void accept(DrawTankMessage message)
	{
		add(new TankView(message.getTank()));
	}

	public void accept(DrawBulletMessage message)
	{
		add(new BulletView(message.getBullet()));
	}

	public void accept(AddControllerMessage message)
	{
		addKeyListener(message.getController());
	}
}
