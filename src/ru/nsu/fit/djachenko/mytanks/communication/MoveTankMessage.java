package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Game;

public class MoveTankMessage implements MessageToModel
{
	private final Direction direction;

	public MoveTankMessage(Direction direction)
	{
		this.direction = direction;
	}

	@Override
	public void handle(Game game)
	{
		game.moveTank(direction);
	}
}
