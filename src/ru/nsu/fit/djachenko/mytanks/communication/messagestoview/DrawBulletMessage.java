package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.model.entrylevel.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class DrawBulletMessage extends MessageToView
{
	private final int x;
	private final int y;
	private final Direction direction;
	private final int id;

	public DrawBulletMessage(Bullet bullet)
	{
		this.x = bullet.getX();
		this.y = bullet.getY();
		this.direction = bullet.getDirection();
		this.id = bullet.getId();
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
