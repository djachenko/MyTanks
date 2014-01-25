package ru.nsu.fit.djachenko.mytanks.controller;

import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ArrowsController extends UserController implements KeyListener
{
	public ArrowsController(MessageAcceptor channel, int playerId)
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
			case KeyEvent.VK_UP:
				enable(UP);
				break;

			case KeyEvent.VK_LEFT:
				enable(LEFT);
				break;

			case KeyEvent.VK_DOWN:
				enable(DOWN);
				break;

			case KeyEvent.VK_RIGHT:
				enable(RIGHT);
				break;

			case KeyEvent.VK_ENTER:
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
			case KeyEvent.VK_UP:
				disable(UP);
				break;

			case KeyEvent.VK_LEFT:
				disable(LEFT);
				break;

			case KeyEvent.VK_DOWN:
				disable(DOWN);
				break;

			case KeyEvent.VK_RIGHT:
				disable(RIGHT);
				break;

			case KeyEvent.VK_ENTER:
				disable(ENTER);
				break;

			default:
				break;
		}
	}
}
