package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.view.GameView;

public class HandleMessageTask implements ViewTask
{
	private final MessageChannel<MessageToView> channel;
	private final GameView gameView;

	public HandleMessageTask(MessageChannel<MessageToView> channel, GameView gameView)
	{
		this.channel = channel;
		this.gameView = gameView;
	}

	@Override
	public void execute(int iteration)
	{
		MessageToView message = channel.tryGet();

		if (message != null)
		{
			message.handle(gameView);
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return true;
	}
}
