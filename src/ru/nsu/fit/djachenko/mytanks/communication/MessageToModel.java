package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Game;

public interface MessageToModel extends Message
{
	void handle(Game game);
}
