package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Bullet;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class DrawBulletMessage extends MessageToView
{
	private Bullet bullet;

	public DrawBulletMessage(Bullet bullet)
	{
		this.bullet = bullet;
	}

	@Override
	public void handle(LevelView levelView)
	{
		levelView.accept(this);
	}

	public Bullet getBullet()
	{
		return bullet;
	}
}
