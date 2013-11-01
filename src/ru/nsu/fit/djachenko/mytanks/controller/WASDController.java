package ru.nsu.fit.djachenko.mytanks.controller;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MoveTankMessage;
import ru.nsu.fit.djachenko.mytanks.communication.ShootMessage;
import ru.nsu.fit.djachenko.mytanks.model.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WASDController implements KeyListener, Controller
{
	private MessageChannel<MessageToModel> channelToModel;
	private int playerId;

	public WASDController(MessageChannel<MessageToModel> channel, int playerId)
	{
		this.channelToModel = channel;
		this.playerId = playerId;
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
				channelToModel.set(new MoveTankMessage(playerId, Direction.UP));
				break;

			case KeyEvent.VK_A:
				channelToModel.set(new MoveTankMessage(playerId, Direction.LEFT));
				break;

			case KeyEvent.VK_S:
				channelToModel.set(new MoveTankMessage(playerId, Direction.DOWN));
				break;

			case KeyEvent.VK_D:
				channelToModel.set(new MoveTankMessage(playerId, Direction.RIGHT));
				break;

			case KeyEvent.VK_SPACE:
				channelToModel.set(new ShootMessage(playerId));
				break;

			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}
}
