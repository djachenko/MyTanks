package ru.nsu.fit.djachenko.mytanks.view.activities;

import ru.nsu.fit.djachenko.mytanks.model.activities.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ViewTaskPerformer
{
	private final LinkedList<Task> tasks = new LinkedList<>();
	public static final int DELAY = 100;

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
					Task task = tasks.remove();

					task.execute();

					if (task.hasToBeRepeated())
					{
						tasks.add(task);
					}
				}
			}
		});
	}

	public void enqueue(Task task)
	{
		synchronized (tasks)
		{
			tasks.add(task);
		}
	}
}
