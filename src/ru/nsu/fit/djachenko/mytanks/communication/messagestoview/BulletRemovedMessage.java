package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class BulletRemovedMessage extends MessageToView
{
	private final int id;

	public BulletRemovedMessage(int id)
	{
		this.id = id;
	}

	@Override
	public void handle(LevelView levelView)
	{
		levelView.accept(this);
	}

	public int getId()
	{
		return id;
	}
}
