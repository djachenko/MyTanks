package ru.nsu.fit.djachenko.mytanks.model.entries;


import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToViewFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.CellFactory;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;

public class Bullet implements FieldElement
{
	private static int count = 0;
	private final int id = count++;

	private final Level level;

	private int x;
	private int y;

	private final Direction direction;
	private boolean active = true;
	private final MessageToView movedMessage = MessageToViewFactory.getInstance().getBulletMovedMessage(id);

	Bullet(Level level, int x, int y, Direction direction)
	{
		this.level = level;

		this.x = x;
		this.y = y;

		this.direction = direction;
	}

	public boolean ableToHit(int x, int y)
	{
		return level.ableToHit(x, y);
	}

	public void hit(int dx, int dy)
	{
		level.hit(x + dx, y + dy);
		explode();
	}

	public void move()
	{
		if (ableToHit(x + direction.getDx(), y + direction.getDy()))
		{
			level.send(movedMessage);
			hit(direction.getDx(), direction.getDy());
		}
		else
		{
			level.move(x, y, direction, 1);
		}

		if (active)
		{
			x += direction.getDx();
			y += direction.getDy();

			level.send(movedMessage);
		}
	}

	public void hit()
	{
		explode();
	}

	private void explode()
	{
		active = false;

		level.remove(this);
	}

	@Override
	public void draw(Field field)
	{
		field.replace(x, y, CellFactory.getInstance().getBulletCell(field, this, x, y));
	}

	@Override
	public void erase(Field field)
	{
		field.replace(x, y, CellFactory.getInstance().getGroundCell());
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public boolean isActive()
	{
		return active;
	}

	public int getId()
	{
		return id;
	}
}
