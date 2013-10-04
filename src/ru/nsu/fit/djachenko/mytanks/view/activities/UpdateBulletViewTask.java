package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.view.BulletView;
import ru.nsu.fit.djachenko.mytanks.view.FieldView;

public class UpdateBulletViewTask implements ViewTask
{
	private BulletView bulletView;
	private FieldView fieldView;

	public UpdateBulletViewTask(BulletView bulletView, FieldView fieldView)
	{
		this.bulletView = bulletView;
		this.fieldView = fieldView;
	}

	@Override
	public void execute(int iteration)
	{
		bulletView.iteration();

		if (!bulletView.isActive())
		{
			fieldView.remove(bulletView);
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return bulletView.isActive();
	}
}
