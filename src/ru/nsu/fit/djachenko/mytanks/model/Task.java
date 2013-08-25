package ru.nsu.fit.djachenko.mytanks.model;

public abstract class Task
{
	enum Type
	{
		MOVETANK(1),
		MOVEBULLET(1),
		HIT(0);

		private Type(int priority)
		{
			this.priority = priority;
		}

		public final int priority;
	}

	public final Type type;

	public Task(Type type)
	{
		this.type = type;
	}

	public abstract void execute();
	public abstract boolean hasToBeRepeated();
}
