package ru.nsu.fit.djachenko.mytanks.controller;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WASDController extends UserController implements KeyListener
{
	public WASDController(MessageChannel<MessageToModel> channel, int playerId)
	{
		super(channel, playerId);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_W:
				enable(UP);
				break;

			case KeyEvent.VK_A:
				enable(LEFT);
				break;

			case KeyEvent.VK_S:
				enable(DOWN);
				break;

			case KeyEvent.VK_D:
				enable(RIGHT);
				break;

			case KeyEvent.VK_SPACE:
				enable(ENTER);
				break;

			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_W:
				disable(UP);
				break;

			case KeyEvent.VK_A:
				disable(LEFT);
				break;

			case KeyEvent.VK_S:
				disable(DOWN);
				break;

			case KeyEvent.VK_D:
				disable(RIGHT);
				break;

			case KeyEvent.VK_SPACE:
				disable(ENTER);
				break;

			default:
				break;
		}
	}
}
