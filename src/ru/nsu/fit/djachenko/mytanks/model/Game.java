package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.communication.DrawBulletMessage;
import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;

import java.io.IOException;

public class Game extends Thread
{
	private MessageChannel<MessageToModel>.GetPoint getPoint = null;
	private MessageChannel<MessageToView>.SetPoint setPoint = null;
	private Level currentLevel = null;

	public Game(MessageChannel<MessageToModel> hereChannel, MessageChannel<MessageToView> thereChannel)
	{
		try
		{
			currentLevel = new Level("resources/test/Tank/TankTestMap.tnk", this);
			currentLevel.setTank(new Tank(currentLevel, 4, 13, Direction.DOWN));
		}
		catch (IOException | MapFormatException e)
		{
			e.printStackTrace();
		}

		getPoint = hereChannel.getGetPoint();
		setPoint = thereChannel.getSetPoint();
	}

	@Override
	public void run()
	{
		while (true)
		{
			MessageToModel message = getPoint.get();

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

	public void spawnBullet(Bullet bullet)
	{
		setPoint.set(new DrawBulletMessage(bullet));
	}
}
