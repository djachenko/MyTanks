package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Client;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public abstract class MessageToView implements Message
{
	public void handle(AppWindow appWindow)
	{
		appWindow.accept(this);
	}

	public void handle(LevelView levelView)
	{
		levelView.accept(this);
	}

	public void handle(Client client)
	{
		client.accept(this);
	}
}