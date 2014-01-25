package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.entries.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

public class CellFactory
{
	private static CellFactory instance;

	private CellFactory()
	{}

	public static CellFactory getInstance()
	{
		if (instance == null)
		{
			synchronized (CellFactory.class)
			{
				if (instance == null)
				{
					instance = new CellFactory();
				}
			}
		}

		return instance;
	}

	private static GroundCell groundCell;
	private static WallCell wallCell;

	public BulletCell getBulletCell(Field field, Bullet origin, int x, int y)
	{
		return new BulletCell(field, origin, x, y);
	}

	public GroundCell getGroundCell()
	{
		if (groundCell == null)
		{
			synchronized (CellFactory.class)
			{
				if (groundCell == null)
				{
					groundCell = new GroundCell();
				}
			}
		}

		return groundCell;
	}

	public TankCell getTankCell(Field field, Tank tank, int x, int y)
	{
		return new TankCell(field, tank, x, y);
	}

	public WallCell getWallCell()
	{
		if (wallCell == null)
		{
			synchronized (this.getClass())
			{
				if (wallCell == null)
				{
					wallCell = new WallCell();
				}
			}
		}

		return wallCell;
	}
}
