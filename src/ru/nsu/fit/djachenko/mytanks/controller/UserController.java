package ru.nsu.fit.djachenko.mytanks.controller;

import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.entries.activities.Task;
import ru.nsu.fit.djachenko.mytanks.model.entries.activities.TaskPerformer;

abstract class UserController
{
	private final MessageAcceptor channelToModel;

	public static final int RIGHT = 0;
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;
	public static final int ENTER = 4;

	private int[] keys = new int[5];
	private MessageToModel[] messages = new MessageToModel[5];

	private static final int PERIOD = 75;
	private static TaskPerformer performer = new TaskPerformer(PERIOD);

	UserController(MessageAcceptor channel, int playerId)
	{
		this.channelToModel = channel;

		for (int i = 0; i < keys.length; i++)
		{
			keys[i] = -1;
		}

		MessageToModelFactory factory = MessageToModelFactory.getInstance();

		messages[0] = factory.getMoveTankMessage(playerId, Direction.RIGHT);
		messages[1] = factory.getMoveTankMessage(playerId, Direction.UP);
		messages[2] = factory.getMoveTankMessage(playerId, Direction.LEFT);
		messages[3] = factory.getMoveTankMessage(playerId, Direction.DOWN);
		messages[4] = factory.getShootMessage(playerId);

		performer.enqueue(new Task()
		{
			@Override
			public void execute(int iteration)
			{
				iteration();
			}

			@Override
			public boolean hasToBeRepeated()
			{
				return true;
			}
		});
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
					messages[i].handle(channelToModel);
				}

				keys[i]++;
			}
		}
	}
}
