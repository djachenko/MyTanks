package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.management.Client;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;

public class LevelStartedMessage extends MessageToView
{
	private final Field.State state;
	private int wasdId;
	private int arrowsId;

	LevelStartedMessage(Field.State state)
	{
		this.state = state;
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

	public Field.State getState()
	{
		return state;
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
