package ru.nsu.fit.djachenko.mytanks.model.activities;

import ru.nsu.fit.djachenko.mytanks.model.Level;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

public class TaskPerformer
{
	private final PriorityQueue<Task> tasks = new PriorityQueue<>(1, new Comparator<Task>()
	{
		@Override
		public int compare(Task o1, Task o2)
		{
			return o1.type.priority - o2.type.priority;
		}
	});

	public TaskPerformer(final Level level)
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

						level.print();
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
