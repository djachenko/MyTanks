package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ViewTaskPerformer
{
	private final LinkedList<ViewTask> tasks = new LinkedList<>();
	private static final int DELAY = Constants.VIEWTASKPERFORMERDELAY;

	public ViewTaskPerformer()
	{
		new Timer(DELAY, new ActionListener()
		{
			private int iteration = 0;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				synchronized (tasks)
				{
					int count = tasks.size();

					for (; count > 0; count--)
					{
						ViewTask task = tasks.remove();

						task.execute(iteration);

						if (task.hasToBeRepeated())
						{
							tasks.add(task);
						}
					}
				}

				iteration++;
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
