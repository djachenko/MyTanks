package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;

public class Runner extends AI
{
	private Field.State state;

	private int x;
	private int y;

	private boolean alive = false;

	boolean flag = false;
	Direction runDirection = null;

	MessageToModelFactory factory = MessageToModelFactory.getInstance();

	public Runner(Game game)
	{
		super(game);
	}

	@Override
	protected void notifyLevelStarted(Field.State state)
	{
		super.notifyLevelStarted(state);

		this.state = state;
	}

	@Override
	protected synchronized void notifyTankSpawned(int x, int y, Direction direction)
	{
		super.notifyTankSpawned(x, y, direction);

		this.x = x;
		this.y = y;
		this.alive = true;
		this.runDirection = null;
		this.flag = false;
	}

	@Override
	protected synchronized void notifyTankHit()
	{
		super.notifyTankHit();

		this.alive = false;
		this.currentState = State.IDLE;
	}


	static enum State
	{
		IDLE,
		BULLETFOUND,
		RUNNING
	}

	private State currentState = State.IDLE;

	private BulletScanStrategy strategy = new BulletScanStrategy();
	private BulletScanStrategy.Result callback = strategy.getCallback();

	@Override
	public synchronized void execute(int iteration)
	{
		if (!alive)
		{
			return;
		}

		state.update();

		switch (currentState)
		{
			case IDLE:
				if (strategy.run(x, y, state, callback))
				{
					currentState = State.BULLETFOUND;
				}

				break;
			case BULLETFOUND:
				BulletScanStrategy.Result compare = strategy.getCallback();

				boolean newResult = strategy.run(x, y, state, compare);

				if (!newResult || compare.getDistance() > callback.getDistance())
				{
					currentState = State.IDLE;
				}
				else if (compare.getDistance() < callback.getDistance())
				{
					runDirection = compare.getDirectionToBullet().opposite();
					currentState = State.RUNNING;
				}

				break;
			case RUNNING:
				if (strategy.run(x, y, state, callback))
				{
					send(factory.getMoveTankMessage(getId(), runDirection));
				}
				else
				{
					currentState = State.IDLE;
				}

				break;
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return true;
	}
}
