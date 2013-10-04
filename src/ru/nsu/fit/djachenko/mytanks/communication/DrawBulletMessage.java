package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Bullet;
import ru.nsu.fit.djachenko.mytanks.view.BulletView;
import ru.nsu.fit.djachenko.mytanks.view.GameView;

public class DrawBulletMessage implements MessageToView
{
	private Bullet bullet;

	public DrawBulletMessage(Bullet bullet)
	{
		this.bullet = bullet;
	}

	@Override
	public void handle(GameView gameView)
	{
		gameView.add(new BulletView(bullet));
	}
}
