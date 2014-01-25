package ru.nsu.fit.djachenko.mytanks.model.entries.activities;

import ru.nsu.fit.djachenko.mytanks.model.entries.Level;

import java.util.Random;

public class SpawnTankTask implements Task
{
	private long countdown;
	private final Level level;
	private final int playerId;

	private static final double MAXDELAY = 15;

	private final static Random random = new Random(System.currentTimeMillis());

	public SpawnTankTask(Level level, int playerId)
	{
		double delayFactor = random.nextDouble();
		this.countdown = Math.round(MAXDELAY * delayFactor * (1000 / TaskPerformer.PERIOD));

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
