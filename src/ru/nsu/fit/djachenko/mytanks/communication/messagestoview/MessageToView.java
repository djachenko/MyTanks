package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.communication.Message;
import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;
import ru.nsu.fit.djachenko.mytanks.model.entries.Level;
import ru.nsu.fit.djachenko.mytanks.model.management.Client;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public abstract class MessageToView implements Message
{
	public void handle(MessageAcceptor acceptor)
	{
		acceptor.accept(this);
	}

	public void handle(AppWindow appWindow)
	{
		appWindow.accept(this);
	}

	public void handle(Client client)
	{
		client.accept(this);
	}

	public void handle(Game game)
	{
		game.accept(this);
	}

	public void handle(Level level)
	{
		level.accept(this);
	}

	public void handle(LevelView levelView)
	{
		levelView.accept(this);
	}
}