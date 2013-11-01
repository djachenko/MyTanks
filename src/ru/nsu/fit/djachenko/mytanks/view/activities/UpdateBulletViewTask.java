package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.view.BulletView;
import ru.nsu.fit.djachenko.mytanks.view.LevelView;

public class UpdateBulletViewTask implements ViewTask
{
	private BulletView bulletView;
	private LevelView levelView;

	private boolean readyToRemove;

	public UpdateBulletViewTask(BulletView bulletView, LevelView levelView)
	{
		this.bulletView = bulletView;
		this.levelView = levelView;

		readyToRemove = false;
	}

	@Override
	public void execute(int iteration)
	{
		readyToRemove = !bulletView.isActive();

		if (!readyToRemove)
		{
			bulletView.iteration();
		}
		else
		{
			levelView.remove(bulletView);
			levelView.repaint();
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return !readyToRemove;
	}
}
