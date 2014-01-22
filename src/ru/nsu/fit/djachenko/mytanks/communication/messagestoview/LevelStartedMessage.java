package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.model.Client;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;

public class LevelStartedMessage extends MessageToView
{
	private final Level level;
	private int wasdId;
	private int arrowsId;

	public LevelStartedMessage(Level level)
	{
		this.level = level;
	}

	@Override
	public void handle(AppWindow appWindow)
	{
		appWindow.accept(this);
	}

	@Override
	public void handle(Client client)
	{
		client.accept(this);
	}

	public Level getLevel()
	{
		return level;
	}

	public void setWasdId(int id)
	{
		this.wasdId = id;
	}

	public int getWasdId()
	{
		return wasdId;
	}

	public void setArrowsId(int id)
	{
		this.arrowsId = id;
	}

	public int getArrowsId()
	{
		return arrowsId;
	}
}
