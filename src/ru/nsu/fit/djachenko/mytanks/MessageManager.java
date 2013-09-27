package ru.nsu.fit.djachenko.mytanks;

import java.util.LinkedList;
import java.util.Queue;

public class MessageManager
{
	public static class AccessPoint
	{
		private Queue<Message> queue = new LinkedList<>();
		private MessageManager manager;

		AccessPoint(MessageManager manager)
		{
			this.manager = manager;
		}

		public synchronized Message get()
		{
			while (queue.isEmpty())
			{
				try
				{
					wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

			return queue.remove();
		}

		public void set(Message message)
		{
			manager.set(message);
		}

		synchronized void add(Message message)
		{
			queue.add(message);

			notifyAll();
		}
	}

	private final Queue<AccessPoint> queue = new LinkedList<>();

	public AccessPoint getAccessPoint()
	{
		AccessPoint accessPoint = new AccessPoint(this);

		synchronized (queue)
		{
			queue.add(accessPoint);
		}

		return accessPoint;
	}

	synchronized void set(Message event)
	{
		for (AccessPoint point : queue)
		{
			point.add(event);
		}
	}
}
