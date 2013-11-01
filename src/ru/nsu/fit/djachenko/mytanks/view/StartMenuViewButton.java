package ru.nsu.fit.djachenko.mytanks.view;

import javax.swing.*;
import java.awt.*;

public class StartMenuViewButton extends JButton
{
	public static final int WIDTH = 150;
	public static final int HEIGHT = 40;

	public StartMenuViewButton(String text)
	{
		super(text);

		initUI();
	}

	private void initUI()
	{
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));

		setAlignmentX(CENTER_ALIGNMENT);
	}
}
