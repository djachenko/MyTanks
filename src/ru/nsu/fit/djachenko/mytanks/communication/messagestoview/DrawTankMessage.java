package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class DrawTankMessage extends MessageToView
{
	private final int x;
	private final int y;
	private final Direction direction;
	private final int tankId;
	private final int playerId;

	DrawTankMessage(int x, int y, Direction direction, int tankId, int playerId)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tankId = tankId;
		this.playerId = playerId;
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

	public int getY()
	{
		return y;
	}

	public int getTankId()
	{
		return tankId;
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
