package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.Imperative;

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
	}

	private BulletScanStrategy bulletScanStrategy = new BulletScanStrategy();

	@Override
	public synchronized void execute(int iteration)
	{
		if (!alive)
		{
			return;
		}

		fieldState.update();

		Imperative imperative = bulletScanStrategy.run(tankState, fieldState, this);
		System.out.println(imperative);

		System.out.println(imperative.getClass().getName());

		imperative.handle(this);
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
