package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Game;

public class StartLevelMessage extends MessageToModel
{
	private int levelId;

	public StartLevelMessage(int levelId)
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
