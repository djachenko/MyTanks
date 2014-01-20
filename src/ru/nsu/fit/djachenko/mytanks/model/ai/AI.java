package ru.nsu.fit.djachenko.mytanks.model.ai;

import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.Player;
import ru.nsu.fit.djachenko.mytanks.model.activities.Task;

public abstract class AI extends Player implements Task
{
	private Level level;

	AI(Level level)
	{
		this.level = level;
	}

	void send(MessageToModel message)
	{
		message.handle(level);
	}
}
