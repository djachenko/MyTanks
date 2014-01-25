package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.entries.Level;

public class SpawnTankTask implements Task
{
	private long countdown;
	private final Level level;
	private final int playerId;

	public SpawnTankTask(Level level, double delay, int playerId)
	{
		this.countdown = Math.round(delay * (1000 / TaskPerformer.PERIOD));
		this.level = level;
		this.playerId = playerId;
	}

	@Override
	public void execute(int iteration)
	{
		countdown--;

		if (countdown == 0)
		{
			level.spawnTank(playerId);
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return countdown > 0;
	}
}
