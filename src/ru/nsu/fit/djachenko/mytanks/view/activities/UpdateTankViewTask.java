package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.view.TankView;

public class UpdateTankViewTask implements ViewTask
{
	private TankView tankView;

	public UpdateTankViewTask(TankView tankView)
	{
		this.tankView = tankView;
	}

	@Override
	public void execute()
	{
		tankView.iteration();
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return true;
	}
}
