package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Tank;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class DrawTankMessage extends MessageToView
{
	private Tank tank;

	public DrawTankMessage(Tank tank)
	{
		this.tank = tank;
	}

	@Override
	public void handle(LevelView levelView)
	{
		levelView.accept(this);
	}

	public Tank getTank()
	{
		return tank;
	}
}
