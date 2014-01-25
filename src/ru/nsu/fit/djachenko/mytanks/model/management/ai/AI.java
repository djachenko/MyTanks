package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.management.Game;
import ru.nsu.fit.djachenko.mytanks.model.management.Player;
import ru.nsu.fit.djachenko.mytanks.model.activities.Task;

public abstract class AI extends Player implements Task
{
	AI(Game game)
	{
		super(game);
	}
}
