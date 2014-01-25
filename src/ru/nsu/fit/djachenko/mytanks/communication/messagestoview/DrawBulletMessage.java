package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class DrawBulletMessage extends MessageToView
{
	private final int x;
	private final int y;
	private final Direction direction;
	private final int id;

	DrawBulletMessage(int x, int y, Direction direction, int bulletID)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.id = bulletID;
	}

	@Override
	public void handle(LevelView levelView)
	{
		levelView.accept(this);
	}

	public int getX()
	{
		return x;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public int getY()
	{
		return y;
	}

	public int getId()
	{
		return id;
	}
}
