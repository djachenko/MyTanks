package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Tank;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class DrawTankMessage extends MessageToView
{
	private int x;
	private int y;
	private Direction direction;
	private int id;

	public DrawTankMessage(Tank tank)
	{
		this.x = tank.getX();
		this.y = tank.getY();
		this.id = tank.getId();
		this.direction = tank.getDirection();

		System.out.println("dtm ctor");
	}

	@Override
	public void handle(LevelView levelView)
	{
		System.out.println("dtm handle");

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

	public int getId()
	{
		return id;
	}

	public Direction getDirection()
	{
		return direction;
	}
}
