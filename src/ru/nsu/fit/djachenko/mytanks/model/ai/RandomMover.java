package ru.nsu.fit.djachenko.mytanks.model.ai;

import ru.nsu.fit.djachenko.mytanks.communication.MoveTankMessage;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Level;

import java.util.Random;

public class RandomMover extends AI
{
	private Random random = new Random();
	private Direction previousDirection = Direction.DOWN;

	public RandomMover(Level channel)
	{
		super(channel);
	}

	@Override
	public void execute(int iteration)
	{
		if (random.nextInt(2) == 0)
		{
			return;
		}

		int rand = random.nextInt(12);

		switch (rand)
		{
			case 0:
				previousDirection = Direction.RIGHT;
				break;
			case 1:
				previousDirection = Direction.UP;
				break;
			case 2:
				previousDirection = Direction.LEFT;
				break;
			case 3:
				previousDirection = Direction.DOWN;
				break;
			default:
				break;
		}

		send(new MoveTankMessage(getId(), previousDirection));
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return true;
	}
}
