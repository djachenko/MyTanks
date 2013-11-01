package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

import javax.swing.*;
import java.awt.*;

public class CellView extends JLabel
{
	public static final int SIZE = 25;//edge size in pixels (cells are square)
	public static final int GRIDSIZE = 25;

	public enum Type
	{
		GROUND(new Color(255, 244, 244), 1),
		WALL(new Color(1, 1, 1), 1),
		TANK(new Color(107, 107, 107), 1),
		BULLET(new Color(184, 184, 184), 1);

		public final Color color;
		public final int order;

		private Type(Color color, int order)
		{
			this.color = color;
			this.order = order;
		}
	}

	private final Type type;

	public CellView(Type type, int x, int y)
	{
		this.type = type;

		initUI(x, y);
	}

	void initUI(int x, int y)
	{
		setBounds(GRIDSIZE * x, GRIDSIZE * y, SIZE, SIZE);
		setBorder(BorderFactory.createLineBorder(type.color.darker(), 1));
		setBackground(type.color);
		setOpaque(true);
	}

	void move(Direction direction)
	{
		int dx = direction.dx;
		int dy = direction.dy;

		for (int i = 0; i < SIZE; i++)
		{
			setLocation(getX() + dx, getY() + dy);
		}
	}
}
