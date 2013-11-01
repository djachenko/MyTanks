package ru.nsu.fit.djachenko.mytanks.controller;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;

public class AIController implements Runnable, Controller
{
	private MessageChannel<MessageToModel> channelToModel;
	private int playerId;

	public AIController(MessageChannel<MessageToModel> channel, int playerId)
	{
		this.channelToModel = channel;
		this.playerId = playerId;
	}

	@Override
	public void run()
	{
	}
}
