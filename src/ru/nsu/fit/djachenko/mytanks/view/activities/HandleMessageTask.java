package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.communication.MessageDonor;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;

public class HandleMessageTask implements ViewTask
{
	private final MessageDonor<MessageToView> channel;
	private final AppWindow appWindow;

	public HandleMessageTask(MessageDonor<MessageToView> channel, AppWindow appWindow)
	{
		this.channel = channel;
		this.appWindow = appWindow;
	}

	@Override
	public void execute(int iteration)
	{
		MessageToView message = channel.tryGet();

		if (message != null)
		{
			message.handle(appWindow);
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return true;
	}
}
