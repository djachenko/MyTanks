package ru.nsu.fit.djachenko.mytanks;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.view.GameView;

public class Starter
{
 	public static void main(String[] args)
	{
		MessageChannel<MessageToModel> viewToModelChannel = new MessageChannel<>();
		MessageChannel<MessageToView> modelToViewChannel = new MessageChannel<>();

		Game game = new Game(viewToModelChannel, modelToViewChannel);
		game.start();
		new GameView(game, modelToViewChannel, viewToModelChannel).setVisible(true);
	}
}
