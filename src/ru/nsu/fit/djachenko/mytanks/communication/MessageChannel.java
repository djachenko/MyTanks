package ru.nsu.fit.djachenko.mytanks.communication;

import java.util.LinkedList;
import java.util.Queue;

public class MessageChannel<T extends Message>
{
	public class GetPoint
	{
		private Queue<T> queue = new LinkedList<>();

		public synchronized T get()
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

		public synchronized T tryGet()
		{
			if (!queue.isEmpty())
			{
				return queue.remove();
			}
			else
			{
				return null;
			}
		}

		synchronized void add(T message)
		{
			queue.add(message);

			notifyAll();
		}
	}

	public class SetPoint
	{
		public void set(T message)
		{
			synchronized (queue)
			{
				for (GetPoint point : queue)
				{
					point.add(message);
				}
			}
		}
	}

	private final Queue<GetPoint> queue = new LinkedList<>();

	public GetPoint getGetPoint()
	{
		GetPoint getPoint = new GetPoint();

		synchronized (queue)
		{
			queue.add(getPoint);
		}

		return getPoint;
	}

	public SetPoint getSetPoint()
	{
		return new SetPoint();
	}
}
