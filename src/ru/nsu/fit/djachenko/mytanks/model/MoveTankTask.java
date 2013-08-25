package ru.nsu.fit.djachenko.mytanks.model;

public class MoveTankTask extends Task
{
	private final Tank tank;
	private final Direction direction;

	public MoveTankTask(Tank tank, Direction direction)
	{
		super(Type.MOVETANK);

		this.tank = tank;
		this.direction = direction;
	}

	@Override
	public void execute()
	{
		try
		{
			tank.move(direction);
		}
		catch (UnexpectedSituationException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return false;
	}
}
