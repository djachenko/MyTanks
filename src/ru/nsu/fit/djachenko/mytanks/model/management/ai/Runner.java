package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;

import static ru.nsu.fit.djachenko.mytanks.model.management.ai.SearchTankStrategy.*;

public class Runner extends AI
{
	private Field.State fieldState;
	
	private Tank.State tankState;

	private boolean alive = false;

	boolean flag = false;
	Direction runDirection = null;

	MessageToModelFactory factory = MessageToModelFactory.getInstance();

	public Runner(Game game)
	{
		super(game);
	}

	@Override
	protected void notifyLevelStarted(Field.State fieldState)
	{
		super.notifyLevelStarted(fieldState);

		this.fieldState = fieldState;
	}

	@Override
	protected synchronized void notifyTankSpawned(Tank.State tankState)
	{
		super.notifyTankSpawned(tankState);

		this.tankState = tankState;

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

	private AimCellStrategy aimStrategy = new AimCellStrategy();
	private AimCellStrategy.Result aimCallback = AimCellStrategy.getCallback();

	@Override
	public synchronized void execute(int iteration)
	{
		if (!alive)
		{
			return;
		}

		fieldState.update();

		aimStrategy.run(19, 5, tankState, fieldState, aimCallback);//19 5

		move(aimCallback.getNextMove());

		if (1 == 1)
		{
			return;
		}

		switch (currentState)
		{
			case IDLE:
				if (bulletScanStrategy.run(tankState.getX(), tankState.getY(), fieldState, bulletScanCallback))
				{
					currentState = State.BULLETFOUND;
				}
				else
				{
					searchTankStrategy.run(tankState.getX(), tankState.getY(), tankState.getDirection(), fieldState, searchTankCallback);
					new RecognizeTankStrategy().run(searchTankCallback, new RecognizeTankStrategy().getCallback());
				}

				break;
			case BULLETFOUND:
				BulletScanStrategy.Result compare = bulletScanStrategy.getCallback();

				boolean newResult = bulletScanStrategy.run(tankState.getX(), tankState.getY(), fieldState, compare);

				if (!newResult || compare.getDistance() > bulletScanCallback.getDistance())
				{
					currentState = State.IDLE;
				}
				else if (compare.getDistance() < bulletScanCallback.getDistance())
				{
					currentState = State.RUNNING;

					escapeStrategy.run(tankState.getX(), tankState.getY(), tankState.getDirection(), fieldState, bulletScanCallback, escapeCallback);
					move(escapeCallback.getNextMove());
				}

				break;
			case RUNNING:
				boolean result = bulletScanStrategy.run(tankState.getX(), tankState.getY(), fieldState, bulletScanCallback);

				if (result)
				{
					escapeStrategy.run(tankState.getX(), tankState.getY(), tankState.getDirection(), fieldState, bulletScanCallback, escapeCallback);

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
	}
}
