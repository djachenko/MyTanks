package ru.nsu.fit.djachenko.mytanks;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

public class MoveMessage extends Message
{
	public final Direction direction;

	public MoveMessage(Direction direction)
	{
		super(Type.MOVEMESSAGE);

		this.direction = direction;
	}
}
