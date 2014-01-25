package ru.nsu.fit.djachenko.mytanks.communication.messagestomodel;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.entries.Level;

public class MoveTankMessage extends MessageToModel
{
	private final Direction direction;
	private final int playerId;

	MoveTankMessage(int playerId, Direction direction)
	{
		this.direction = direction;
		this.playerId = playerId;
	}

	@Override
	public void handle(Level level)
	{
		level.accept(this);
	}

	public Direction getDirection()
	{
		return direction;
	}

	public int getPlayerId()
	{
		return playerId;
	}
}
