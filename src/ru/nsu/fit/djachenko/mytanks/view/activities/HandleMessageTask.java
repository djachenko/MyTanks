package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.view.GameView;

public class HandleMessageTask implements ViewTask
{
	private final MessageChannel<MessageToView>.GetPoint getPoint;
	private final GameView gameView;

	public HandleMessageTask(MessageChannel<MessageToView>.GetPoint getPoint, GameView gameView)
	{
		this.getPoint = getPoint;
		this.gameView = gameView;
	}

	@Override
	public void execute(int iteration)
	{
		MessageToView message = getPoint.tryGet();

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
