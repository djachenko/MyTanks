package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Tank;
import ru.nsu.fit.djachenko.mytanks.view.GameView;
import ru.nsu.fit.djachenko.mytanks.view.TankView;

public class DrawTankMessage implements MessageToView
{
	private Tank tank;

	public DrawTankMessage(Tank tank)
	{
		this.tank = tank;
		System.out.println("draw tank " + this);
	}

	@Override
	public void handle(GameView gameView)
	{
		gameView.add(new TankView(tank));
		System.out.println("DrawTankMessage" + this.getClass().getCanonicalName());
	}
}
