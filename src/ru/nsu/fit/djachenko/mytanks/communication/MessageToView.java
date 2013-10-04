package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.view.GameView;

public interface MessageToView extends Message
{
	public void handle(GameView gameView);
}
