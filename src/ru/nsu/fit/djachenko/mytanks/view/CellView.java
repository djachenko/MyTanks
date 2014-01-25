package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;

import javax.swing.*;
import java.awt.*;

public class CellView extends JLabel
{
	public static final int SIZE = 25;//edge size in pixels (cellls are square)
	public static final int GRIDSIZE = 25;

	public enum Type
	{
		GROUND(new Color(255, 244, 244)),
		WALL(new Color(1, 1, 1)),
		TANK(new Color(107, 107, 107)),
		BULLET(new Color(184, 184, 184));

		private final Color color;

		private Type(Color color)
		{
			this.color = color;
		}

		Color getColor()
		{
			return color;
		}
	}

	CellView(Type type, int x, int y)
	{
		initUI(type, x, y);
	}

	private void initUI(Type type, int x, int y)
	{
		setBounds(GRIDSIZE * x, GRIDSIZE * y, SIZE, SIZE);
		setBorder(BorderFactory.createLineBorder(type.getColor().darker(), 1));
		setBackground(type.getColor());
		setOpaque(true);
	}

	void move(Direction direction)
	{
		int dx = direction.getDx();
		int dy = direction.getDy();

		for (int i = 0; i < SIZE; i++)
		{
			setLocation(getX() + dx, getY() + dy);
		}
	}
}
