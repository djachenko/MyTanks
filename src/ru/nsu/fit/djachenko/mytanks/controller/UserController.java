package ru.nsu.fit.djachenko.mytanks.controller;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MoveTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.ShootMessage;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class UserController
{
	private final MessageChannel<MessageToModel> channelToModel;

	public static final int RIGHT = 0;
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;
	public static final int ENTER = 4;

	private int[] keys = new int[5];
	private MessageToModel[] messages = new MessageToModel[5];

	UserController(MessageChannel<MessageToModel> channel, int playerId)
	{
		this.channelToModel = channel;

		for (int i = 0; i < keys.length; i++)
		{
			keys[i] = -1;
		}

		messages[0] = new MoveTankMessage(playerId, Direction.RIGHT);
		messages[1] = new MoveTankMessage(playerId, Direction.UP);
		messages[2] = new MoveTankMessage(playerId, Direction.LEFT);
		messages[3] = new MoveTankMessage(playerId, Direction.DOWN);
		messages[4] = new ShootMessage(playerId);

		new Timer(75, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				iteration();
			}
		}).start();
	}

	synchronized void enable(int keyCode)
	{
		if (keyCode >= 0 && keyCode < keys.length)
		{
			keys[keyCode] = 0;
		}
	}

	synchronized void disable(int keyCode)
	{
		if (keyCode >= 0 && keyCode < keys.length)
		{
			keys[keyCode] = -1;
		}
	}

	private synchronized void iteration()
	{
		for (int i = 0; i < keys.length; i++)
		{
			if (keys[i] > -1)
			{
				if (keys[i] == 0 || keys[i] > 2)
				{
					channelToModel.set(messages[i]);
				}

				keys[i]++;
			}
		}
	}
}
