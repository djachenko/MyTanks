package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;

import static ru.nsu.fit.djachenko.mytanks.model.management.ai.SearchTankStrategy.*;

public class Runner extends AI
{
	private Field.State state;

	private int x;
	private int y;
	private Direction direction;

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
		this.direction = direction;
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

	private BulletScanStrategy bulletScanStrategy = new BulletScanStrategy();
	private BulletScanStrategy.Result bulletScanCallback = bulletScanStrategy.getCallback();

	private BuildEscapeRouteStrategy escapeStrategy = new BuildEscapeRouteStrategy();
	private BuildEscapeRouteStrategy.Result escapeCallback = escapeStrategy.getCallback();

	private SearchTankStrategy searchTankStrategy = new SearchTankStrategy();
	private Result searchTankCallback = searchTankStrategy.getCallback();

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
				if (bulletScanStrategy.run(x, y, state, bulletScanCallback))
				{
					currentState = State.BULLETFOUND;
				}
				else
				{
					searchTankStrategy.run(x, y, state, searchTankCallback);
				}

				break;
			case BULLETFOUND:
				BulletScanStrategy.Result compare = bulletScanStrategy.getCallback();

				boolean newResult = bulletScanStrategy.run(x, y, state, compare);

				if (!newResult || compare.getDistance() > bulletScanCallback.getDistance())
				{
					currentState = State.IDLE;
				}
				else if (compare.getDistance() < bulletScanCallback.getDistance())
				{
					currentState = State.RUNNING;

					escapeStrategy.run(x, y, direction, state, bulletScanCallback, escapeCallback);
					move(escapeCallback.getNextMove());
				}

				break;
			case RUNNING:
				boolean result = bulletScanStrategy.run(x, y, state, bulletScanCallback);

				if (result)
				{
					escapeStrategy.run(x, y, direction, state, bulletScanCallback, escapeCallback);

					move(escapeCallback.getNextMove());
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

	private void move(Direction moveDirection)
	{
		send(factory.getMoveTankMessage(getId(), moveDirection));

		if (moveDirection == direction)
		{
			int newX = x + moveDirection.getDx();
			int newY = y + moveDirection.getDy();

			if (newX > 0 && newX < state.width() - 1 && newY > 0 && newY < state.height() - 1)
			{
				x = newX;
				y = newY;
			}
		}
		else
		{
			direction = moveDirection;
		}
	}
}
