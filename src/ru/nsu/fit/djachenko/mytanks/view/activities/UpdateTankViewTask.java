package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.view.FieldView;
import ru.nsu.fit.djachenko.mytanks.view.TankView;

public class UpdateTankViewTask implements ViewTask
{
	private TankView tankView;
	private FieldView fieldView;

	private boolean readyToRemove;

	public UpdateTankViewTask(FieldView fieldView, TankView tankView)
	{
		this.tankView = tankView;
		this.fieldView = fieldView;

		readyToRemove = false;
	}

	@Override
	public void execute(int iteration)
	{
		readyToRemove = !tankView.isAlive();

		if (!readyToRemove)
		{
			tankView.iteration();
		}
		else
		{
			fieldView.remove(tankView);
			fieldView.repaint();
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return !readyToRemove;
	}
}
