package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.MessageManager;
import ru.nsu.fit.djachenko.mytanks.MoveMessage;
import ru.nsu.fit.djachenko.mytanks.ShootMessage;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter
{
	private MessageManager.AccessPoint accessPoint = new MessageManager().getAccessPoint();

	Controller(MessageManager messageManager)
	{
		if (messageManager != null)
		{
			this.accessPoint = messageManager.getAccessPoint();
		}
		else
		{
			throw new NullPointerException();
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				accessPoint.set(new MoveMessage(Direction.RIGHT));
				break;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				accessPoint.set(new MoveMessage(Direction.UP));
				break;

			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				accessPoint.set(new MoveMessage(Direction.LEFT));
				break;

			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				accessPoint.set(new MoveMessage(Direction.DOWN));
				break;

			case KeyEvent.VK_SPACE:
				accessPoint.set(new ShootMessage());
				break;

			default:
				break;
		}
	}
}
