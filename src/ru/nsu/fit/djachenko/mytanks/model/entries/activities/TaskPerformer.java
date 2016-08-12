package ru.nsu.fit.djachenko.mytanks.model.entries.activities;

import ru.nsu.fit.djachenko.mytanks.Constants;

import java.util.*;

public class TaskPerformer
{
	private final LinkedList<Task> tasks = new LinkedList<>();

	public TaskPerformer()
	{
		this(Constants.TASKPERFORMERPERIOD);
	}

	public TaskPerformer(int period)
	{
		new Timer(true).scheduleAtFixedRate(new TimerTask()
		{
			int iteration = 0;

			@Override
			public void run()
			{
				synchronized (tasks)
				{
					int count = tasks.size();

					for (; count > 0; count--)
					{
						Task task = tasks.remove();

						task.execute(iteration);

						if (task.hasToBeRepeated())
						{
							tasks.add(task);
						}
					}

					iteration++;
				}
			}
		}, 0, period);
	}

	public void enqueue(Task task)
	{
		synchronized (tasks)
		{
			tasks.add(task);
		}
	}
}
