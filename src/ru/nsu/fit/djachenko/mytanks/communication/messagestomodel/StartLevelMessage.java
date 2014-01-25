package ru.nsu.fit.djachenko.mytanks.communication.messagestomodel;

import ru.nsu.fit.djachenko.mytanks.model.management.Game;

public class StartLevelMessage extends MessageToModel
{
	private final int levelId;

	StartLevelMessage(int levelId)
	{
		this.levelId = levelId;
	}

	@Override
	public void handle(Game game)
	{
		game.accept(this);
	}

	public int getLevelId()
	{
		return levelId;
	}
}
