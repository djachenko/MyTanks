package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Level;

public class SpawnTankTask implements Task
{
	private long countdown;
	private Level level;
	private int playerId;

	public SpawnTankTask(Level level, double delay, int playerId)
	{
		this.countdown = Math.round(delay * (1000 / TaskPerformer.PERIOD));
		this.level = level;
		this.playerId = playerId;

		System.out.println("spawntask");
	}

	@Override
	public void execute(int iteration)
	{
		countdown--;

		System.out.println(countdown);

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
