package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.view.LevelView;

import java.awt.event.KeyListener;

public class AddControllerMessage extends MessageToView
{
	private final KeyListener controller;

	public AddControllerMessage(KeyListener controller)
	{
		this.controller = controller;
	}

	@Override
	public void handle(LevelView levelView)
	{
		levelView.accept(this);
	}

	public KeyListener getController()
	{
		return controller;
	}
}