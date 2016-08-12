package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;
import ru.nsu.fit.djachenko.mytanks.model.management.Player;
import ru.nsu.fit.djachenko.mytanks.model.entries.activities.Task;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.Imperative;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.MoveImperative;

public abstract class AI extends Player implements Task
{
	MessageToModelFactory factory = MessageToModelFactory.getInstance();

	AI(Game game)
	{
		super(game);
	}

	public void endIteration()
	{}

	public void enqueue(Strategy strategy)
	{}

	public void dequeue(Strategy strategy)
	{}

	public void handle(Imperative imperative)
	{}

	public void handle(MoveImperative imperative)
	{
		send(factory.getMoveTankMessage(getId(), imperative.getDirection()));
	}
}