package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;

import java.util.Random;

public class RandomMover extends AI
{
	private Random random = new Random();
	private Direction direction = Direction.DOWN;

	private MessageToModelFactory factory = MessageToModelFactory.getInstance();

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

		send(factory.getMoveTankMessage(getId(), direction));
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return true;
	}
}
