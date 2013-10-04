package ru.nsu.fit.djachenko.mytanks.view.activities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ViewTaskPerformer
{
	private final LinkedList<ViewTask> tasks = new LinkedList<>();
	public static final int DELAY = 10;

	public ViewTaskPerformer()
	{
		new Timer(DELAY, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int count = tasks.size();

				for (; count > 0; count--)
				{
					ViewTask task = tasks.remove();

					task.execute(0);

					if (task.hasToBeRepeated())
					{
						tasks.add(task);
					}
				}
			}
		}).start();
	}

	public void enqueue(ViewTask task)
	{
		synchronized (tasks)
		{
			tasks.add(task);
		}
	}
}
