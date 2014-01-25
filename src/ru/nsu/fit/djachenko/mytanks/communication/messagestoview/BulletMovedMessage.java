package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class BulletMovedMessage extends MessageToView
{
	private final int id;

	BulletMovedMessage(int bulletId)
	{
		this.id = bulletId;
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
