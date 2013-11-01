package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.view.LevelView;
import ru.nsu.fit.djachenko.mytanks.view.TankView;

public class UpdateTankViewTask implements ViewTask
{
	private TankView tankView;
	private LevelView levelView;

	private boolean readyToRemove;

	public UpdateTankViewTask(LevelView levelView, TankView tankView)
	{
		this.tankView = tankView;
		this.levelView = levelView;

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
			levelView.remove(tankView);
			levelView.repaint();
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return !readyToRemove;
	}
}
