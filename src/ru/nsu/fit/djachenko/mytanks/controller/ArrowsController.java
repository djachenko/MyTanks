package ru.nsu.fit.djachenko.mytanks.controller;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MoveTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.ShootMessage;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class ArrowsController implements KeyListener, Controller
{
	private final MessageChannel<MessageToModel> channelToModel;

	private static final int RIGHT = 0;
	private static final int UP = 1;
	private static final int LEFT = 2;
	private static final int DOWN = 3;
	private static final int ENTER = 4;

	private int[] keys = new int[5];
	private MessageToModel[] messages = new MessageToModel[5];

	public ArrowsController(MessageChannel<MessageToModel> channel, final int playerId)
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

		new Timer(80, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				synchronized (ArrowsController.this)
				{
					for (int i = 0; i < keys.length; i++)
					{
						if (keys[i] > -1)
						{
							if (keys[i] != 1)
							{
								channelToModel.set(messages[i]);
							}

							keys[i]++;
						}
					}
				}
			}
		}).start();
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public synchronized void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_UP:
					keys[UP] = 0;
				break;

			case KeyEvent.VK_LEFT:
					keys[LEFT] = 0;
				break;

			case KeyEvent.VK_DOWN:
					keys[DOWN] = 0;
				break;

			case KeyEvent.VK_RIGHT:
					keys[RIGHT] = 0;
				break;

			case KeyEvent.VK_ENTER:
					keys[ENTER] = 0;
				break;

			default:
				break;
		}
	}

	@Override
	public synchronized void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_UP:
				keys[UP] = -1;
				break;

			case KeyEvent.VK_LEFT:
				keys[LEFT] = -1;
				break;

			case KeyEvent.VK_DOWN:
				keys[DOWN] = -1;
				break;

			case KeyEvent.VK_RIGHT:
				keys[RIGHT] = -1;
				break;

			case KeyEvent.VK_ENTER:
				keys[ENTER] = -1;
				break;

			default:
				break;
		}
	}
}
