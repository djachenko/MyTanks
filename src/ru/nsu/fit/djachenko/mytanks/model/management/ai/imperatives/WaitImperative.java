package ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives;

import ru.nsu.fit.djachenko.mytanks.model.management.ai.AI;

public class WaitImperative extends Imperative
{
	WaitImperative()
	{}

	@Override
	public void handle(AI ai)
	{
		ai.handle(this);
	}
}
