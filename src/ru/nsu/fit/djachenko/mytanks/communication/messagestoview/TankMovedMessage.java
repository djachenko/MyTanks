package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class TankMovedMessage extends MessageToView
{
	private final int id;
	private final Direction direction;

	public TankMovedMessage(int id, Direction direction)
	{
		this.id = id;
		this.direction = direction;
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

	public Direction getDirection()
	{
		return direction;
	}
}
