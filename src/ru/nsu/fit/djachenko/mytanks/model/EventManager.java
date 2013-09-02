package ru.nsu.fit.djachenko.mytanks.model;

import java.util.LinkedList;
import java.util.Queue;

public class EventManager
{
	public static class EntryPoint
	{
		private Queue<Direction> queue = new LinkedList<>();

		public Direction get()
		{
			return queue.remove();
		}

		public void add(Direction direction)
		{
			queue.add(direction);
		}

		public boolean isEmpty()
		{
			return queue.isEmpty();
		}
	}

	private Queue<EntryPoint> points = new LinkedList<>();

	public EntryPoint subscribe()
	{
		EntryPoint point = new EntryPoint();

		points.add(point);

		return point;
	}

	public void add(Direction direction)
	{
		for (EntryPoint point : points)
		{
			point.add(direction);
		}
	}
}
