package ru.nsu.fit.djachenko.mytanks.testing;

import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;

public class TankSpawnedMessage extends MessageToModel
{
	private int playerId;
	private int tankId;

	public TankSpawnedMessage(int playerId, int tankId)
	{
		this.playerId = playerId;
		this.tankId = tankId;
	}

	@Override
	public void handle(Game game)
	{
		game.accept(this);
	}


	public int getPlayerId()
	{
		return playerId;
	}

	public int getTankId()
	{
		return tankId;
	}
}
