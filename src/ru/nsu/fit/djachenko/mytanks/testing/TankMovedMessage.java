package ru.nsu.fit.djachenko.mytanks.testing;

import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class TankMovedMessage extends MessageToView
{
	private int id;
	private Direction direction;

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
