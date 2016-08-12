package ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

import java.util.HashMap;
import java.util.Map;

public class ImperativeFactory
{
	private static final ImperativeFactory instance;
	private static final Map<Direction, MoveImperative> moveImperatives;

	static
	{
		moveImperatives = new HashMap<>();
		instance = new ImperativeFactory();
	}

	private static final WaitImperative waitImperative = new WaitImperative();
	private static final ShootImperative shootImperative = new ShootImperative();
	private static final SkipImperative skipImperative = new SkipImperative();

	private ImperativeFactory()
	{
		for (Direction direction : Direction.values())
		{
			moveImperatives.put(direction, new MoveImperative(direction));
		}
	}

	public static ImperativeFactory getInstance()
	{
		return instance;
	}

	public Imperative getWaitImperative()
	{
		return waitImperative;
	}

	public Imperative getMoveImperative(Direction direction)
	{
		if (direction == null)
		{
			throw new NullPointerException("Null direction");
		}

		return moveImperatives.get(direction);
	}

	public Imperative getShootImperative()
	{
		return shootImperative;
	}

	public Imperative getSkipImperative()
	{
		return skipImperative;
	}
}
