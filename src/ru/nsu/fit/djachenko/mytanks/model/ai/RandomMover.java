package ru.nsu.fit.djachenko.mytanks.model.ai;

import ru.nsu.fit.djachenko.mytanks.communication.MoveTankMessage;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Game;

import java.util.Random;

public class RandomMover extends AI
{
	private Random random = new Random();
	private Direction direction = Direction.DOWN;

	public RandomMover(Game channel)
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
				direction = Direction.RIGHT;
				break;
			case 1:
				direction = Direction.UP;
				break;
			case 2:
				direction = Direction.LEFT;
				break;
			case 3:
				direction = Direction.DOWN;
				break;
			default:
				break;
		}

		send(new MoveTankMessage(getId(), direction));
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return true;
	}
}
