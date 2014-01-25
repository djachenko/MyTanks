package ru.nsu.fit.djachenko.mytanks.model.management;

import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.StartGameMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.LevelStartedMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToViewFactory;

public class Client implements MessageAcceptor
{
	private Game game;

	private int wasdId;
	private int arrowsId;

	private final ModelViewCommunicator communicator = new ModelViewCommunicator(this);

	public ModelViewCommunicator getCommunicator()
	{
		return communicator;
	}

	private void startLocalGame()
	{
		game = new Game();

		game.register(this);
	}

	void createPlayers(GameMode mode)
	{
		switch (mode)
		{
			case SINGLE:
				int id = game.registerPlayer();
				game.registerAI();

				wasdId = id;
				arrowsId = id;
				break;
			case SHARED:
				wasdId = game.registerPlayer();
				arrowsId = game.registerPlayer();
		}
	}

	@Override
	public void accept(MessageToModel message)
	{
		message.handle(game);
	}

	@Override
	public void accept(MessageToView message)
	{
		message.handle(communicator);
	}

	public void accept(StartGameMessage message)
	{
		startLocalGame();
		createPlayers(message.getMode());

		MessageToViewFactory.getInstance().getChooseLevelMessage().handle(communicator);
	}

	public void accept(LevelStartedMessage message)
	{
		message.setArrowsId(arrowsId);
		message.setWasdId(wasdId);

		message.handle(communicator);
	}
}
