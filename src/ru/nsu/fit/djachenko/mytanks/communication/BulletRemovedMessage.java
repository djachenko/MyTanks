package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class BulletRemovedMessage extends MessageToView
{
	private int id;

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