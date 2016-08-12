package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.Imperative;

public class HuntStrategy implements Strategy
{
	@Override
	public Imperative run(Tank.State tankState, Field.State fieldState, AI parent)
	{
		//if not cached tank then find
		//if cached tank then follow
		//else random

		return null;
	}

	@Override
	public int getPriority()
	{
		return 0;
	}
}
