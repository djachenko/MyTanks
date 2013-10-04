package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Game;

public class ShootMessage implements MessageToModel
{
	public ShootMessage()
	{
	}

	@Override
	public void handle(Game game)
	{
		game.shoot();
	}
}
