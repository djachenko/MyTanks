package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.model.activities.Task;
import ru.nsu.fit.djachenko.mytanks.view.TankView;

public class UpdateTankViewTask extends Task
{
	private TankView tankView;

	public UpdateTankViewTask(TankView tankView)
	{
		super(Type.HIT);

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
