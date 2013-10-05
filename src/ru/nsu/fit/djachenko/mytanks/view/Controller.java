package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter
{
	private MessageChannel<MessageToModel> channelToModel;

	Controller(MessageChannel<MessageToModel> messageChannel)
	{
		if (messageChannel != null)
		{
			this.channelToModel = messageChannel;
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
		System.out.println("key " + key);

		switch (key)
		{
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				channelToModel.set(new MoveTankMessage(Direction.RIGHT));
				break;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				channelToModel.set(new MoveTankMessage(Direction.UP));
				break;

			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				channelToModel.set(new MoveTankMessage(Direction.LEFT));
				break;

			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				channelToModel.set(new MoveTankMessage(Direction.DOWN));
				break;

			case KeyEvent.VK_SPACE:
				channelToModel.set(new ShootMessage());
				break;

			default:
				break;
		}
	}
}
