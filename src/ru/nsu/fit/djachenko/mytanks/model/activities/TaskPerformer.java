package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Level;

import java.util.*;

public class TaskPerformer
{
	private final LinkedList<Task> tasks = new LinkedList<>();

	public TaskPerformer()
	{
		new Timer(true).scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				synchronized (tasks)
				{
					int count = tasks.size();

					for (; count > 0; count--)
					{
						Task task = tasks.remove();

						task.execute();

						if (task.hasToBeRepeated())
						{
							tasks.add(task);
						}
					}
				}
			}
		}, 0, 100);
	}

	public void enqueue(Task task)
	{
		synchronized (tasks)
		{
			tasks.add(task);
		}
	}
}
