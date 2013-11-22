package ru.nsu.fit.djachenko.mytanks.controller;

import ru.nsu.fit.djachenko.mytanks.model.Game;

public class AIController implements Runnable, Controller
{
	private Game game;
	private int playerId;

	public AIController(Game game, int playerId)
	{
		this.game = game;
		this.playerId = playerId;
	}

	@Override
	public void run()
	{
	}
}
