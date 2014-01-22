package ru.nsu.fit.djachenko.mytanks.model.ai;

import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.model.Player;
import ru.nsu.fit.djachenko.mytanks.model.activities.Task;

public abstract class AI extends Player implements Task
{
	AI(Game game)
	{
		super(game);
	}
}
