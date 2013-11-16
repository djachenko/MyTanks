package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.communication.AddControllerMessage;
import ru.nsu.fit.djachenko.mytanks.communication.DrawBulletMessage;
import ru.nsu.fit.djachenko.mytanks.communication.DrawTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.model.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.Tank;
import ru.nsu.fit.djachenko.mytanks.model.cells.Cell;
import ru.nsu.fit.djachenko.mytanks.testing.TankMovedMessage;
import ru.nsu.fit.djachenko.mytanks.testing.TankRemovedMessage;
import ru.nsu.fit.djachenko.mytanks.view.activities.UpdateBulletViewTask;
import ru.nsu.fit.djachenko.mytanks.view.activities.UpdateTankViewTask;
import ru.nsu.fit.djachenko.mytanks.view.activities.ViewTaskPerformer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LevelView extends JPanel
{
	private ViewTaskPerformer performer;
	private Map<Integer, TankView> tankViews = new HashMap<>();
	private Map<Integer, BulletView> bulletViews = new HashMap<>();

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

		setPreferredSize(new Dimension(width * CellView.GRIDSIZE, height * CellView.GRIDSIZE));
	}

	private void add(TankView tank)
	{
		super.add(tank);
		setComponentZOrder(tank, 0);
		repaint();
	}

	private void add(TankView tankView, int id)
	{
		tankViews.put(id, tankView);
		add(tankView);
	}

	private void add(BulletView bullet)
	{
		//bulletViews.put(0, bullet);

		super.add(bullet);
		setComponentZOrder(bullet, 0);
		repaint();

		performer.enqueue(new UpdateBulletViewTask(bullet, this));
	}

	public void remove(TankView tankView)
	{
		super.remove(tankView);
	}

	public void remove(BulletView bulletView)
	{
		//bulletViews.remove(0);

		super.remove(bulletView);
	}

	private void removeTank(int id)
	{
		TankView tankView = tankViews.get(id);

		if (tankView != null)
		{
			super.remove(tankView);
			tankViews.remove(id);
		}
	}

	private void removeBullet(int id)
	{
		BulletView bulletView = bulletViews.get(id);

		if (bulletView != null)
		{
			super.remove(bulletView);
			bulletViews.remove(id);
		}
	}

	public void accept(MessageToView message)
	{
		//throw ;
	}

	public void accept(DrawTankMessage message)
	{
		System.out.println("draw message");
		add(new TankView(message.getX(), message.getY(), message.getDirection()), message.getId());
	}

	public void accept(DrawBulletMessage message)
	{
		add(new BulletView(message.getBullet()));
		//add(new BulletView(message.getX(), message.getY(), message.getDirection()), message.getId());
	}

	public void accept(AddControllerMessage message)
	{
		addKeyListener(message.getController());
	}

	public void accept(TankMovedMessage message)
	{
		TankView tankView = tankViews.get(message.getId());

		if (tankView != null)
		{
			tankView.move(message.getDirection());
		}
	}

	public void accept(TankRemovedMessage message)
	{
		removeTank(message.getId());
	}
}
