package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;

public class LevelStartedMessage extends MessageToView
{
	private Level level;
	private int wasdId;
	private int arrowsId;

	public LevelStartedMessage(Level level, int[] ids)
	{
		this.level = level;
		this.wasdId = ids[0];
		this.arrowsId = ids[1];

		System.out.println("lsm ctor");
	}

	@Override
	public void handle(AppWindow appWindow)
	{
		System.out.println("lasm h");
		appWindow.accept(this);
	}

	public Level getLevel()
	{
		return level;
	}

	public int getWasdId()
	{
		return wasdId;
	}

	public int getArrowsId()
	{
		return arrowsId;
	}
}
