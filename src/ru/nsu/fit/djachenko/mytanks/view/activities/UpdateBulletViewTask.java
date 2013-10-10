package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.view.BulletView;
import ru.nsu.fit.djachenko.mytanks.view.FieldView;

public class UpdateBulletViewTask implements ViewTask
{
	private BulletView bulletView;
	private FieldView fieldView;

	private boolean readyToRemove;

	public UpdateBulletViewTask(BulletView bulletView, FieldView fieldView)
	{
		this.bulletView = bulletView;
		this.fieldView = fieldView;

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
			fieldView.remove(bulletView);
			fieldView.repaint();
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return !readyToRemove;
	}
}
