package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

class BulletView extends CellView
{
	private final Direction direction;

	BulletView(int x, int y, Direction direction)
	{
		super(Type.BULLET, x, y);

		this.direction = direction;
	}

	void move()
	{
		super.move(direction);
	}
}
