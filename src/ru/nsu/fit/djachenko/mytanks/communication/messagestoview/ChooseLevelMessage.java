package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.view.AppWindow;

public class ChooseLevelMessage extends MessageToView
{
	@Override
	public void handle(AppWindow appWindow)
	{
		appWindow.accept(this);
	}
}
