package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.*;

import java.io.IOException;

public class Game extends Thread
{
	private MessageChannel<MessageToModel> hereChannel = null;
	private MessageChannel<MessageToView> thereChannel = null;
	private Level currentLevel = null;

	public Game(MessageChannel<MessageToModel> hereChannel, MessageChannel<MessageToView> thereChannel)
	{
		this.hereChannel = hereChannel;
		this.thereChannel = thereChannel;
	}

	@Override
	public void run()
	{
		try
		{
			currentLevel = new Level("resources/test/Tank/TankTestMap.tnk", this);
			Tank first = new Tank(currentLevel, 4, 13, Direction.UP);
			currentLevel.addTank(first);
			currentLevel.setActiveTank(0);
			currentLevel.addTank(new Tank(currentLevel, 20, 13, Direction.UP));
		}
		catch (IOException | MapFormatException e)
		{
			e.printStackTrace();
		}

		while (true)
		{
			MessageToModel message = hereChannel.get();

			message.handle(this);
		}
	}

	public Level getCurrentLevel()
	{
		return currentLevel;
	}

	public void shoot()
	{
		if (currentLevel != null)
		{
			currentLevel.shoot();
		}
	}

	public void moveTank(Direction direction)
	{
		if (currentLevel != null)
		{
			currentLevel.moveTank(direction);
		}
	}

	public void startLevel(int index)
	{/*
		try
		{
			currentLevel = new Level();
		}
		catch (IOException | MapFormatException e)
		{
			e.printStackTrace();
		}*/
	}

	public void addBullet(Bullet bullet)
	{
		thereChannel.set(new DrawBulletMessage(bullet));
	}

	public void addTank(Tank tank)
	{
		thereChannel.set(new DrawTankMessage(tank));
	}
}
