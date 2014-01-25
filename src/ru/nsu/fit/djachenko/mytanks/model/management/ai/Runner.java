package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cellls.Field;
import ru.nsu.fit.djachenko.mytanks.model.management.Game;

public class Runner extends AI
{
	private Field.State state;
	private int stateWidth;
	private int stateHeight;

	private int x;
	private int y;

	private boolean alive = false;

	int[][] table;

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
		this.stateHeight = state.height();
		this.stateWidth = state.width();

		table = new int[state.height()][state.width()];
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
	}

	@Override
	public synchronized void execute(int iteration)
	{
		if (!alive)
		{
			return;
		}

		state.update();

		/*if (direction.isHorisontal())
		{
			int dx = direction.getDx();

			int count = 0;
			boolean flag = false;

			for (int i = x; i >= 0 && i < stateWidth; i += dx)
			{
				for (int j = y - count >= 0 ? y - count : 0; j < stateHeight && j < y + count; j++)
				{
					if (state.at(x + i, y + j) == Cell.Type.TANK)
				}
			}
		}*/

		if (!flag)
		{
			BulletScanStrategy strategy = new BulletScanStrategy();

			strategy.action(-1, x, y, null, state);

			flag = strategy.getResult();
			runDirection = strategy.getRunDirection();
		}

		if (flag)
		{
			send(factory.getMoveTankMessage(getId(), runDirection));
		}
	}

	@Override
	public boolean hasToBeRepeated()
	{
		return true;
	}
}
