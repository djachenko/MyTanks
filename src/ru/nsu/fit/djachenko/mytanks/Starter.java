package ru.nsu.fit.djachenko.mytanks;

import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.view.GameView;

public class Starter
{
 	public static void main(String[] args)
	{
		MessageManager messageManager = new MessageManager();

		Game game = new Game(messageManager);
		game.start();
		new GameView(game, messageManager).setVisible(true);
	}
}
