package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter
{
	private MessageChannel<MessageToModel>.SetPoint setPoint;

	Controller(MessageChannel<MessageToModel> messageChannel)
	{
		if (messageChannel != null)
		{
			this.setPoint = messageChannel.getSetPoint();
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
				setPoint.set(new MoveMessage(Direction.RIGHT));
				break;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				setPoint.set(new MoveMessage(Direction.UP));
				break;

			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				setPoint.set(new MoveMessage(Direction.LEFT));
				break;

			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				setPoint.set(new MoveMessage(Direction.DOWN));
				break;

			case KeyEvent.VK_SPACE:
				setPoint.set(new ShootMessage());
				break;

			default:
				break;
		}
	}
}
