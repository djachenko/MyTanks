package ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.AI;

public class MoveImperative extends Imperative
{
	private final Direction direction;

	MoveImperative(Direction direction)
	{
		this.direction = direction;
	}

	public Direction getDirection()
	{
		return direction;
	}

	@Override
	public void handle(AI ai)
	{
		ai.handle(this);
	}
}
