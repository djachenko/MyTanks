package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.Constants;

import javax.swing.*;
import java.awt.*;

class StartMenuViewButton extends JButton
{
	private static final int WIDTH = Constants.STARTMENUVIEWBUTTONWIDTH;
	private static final int HEIGHT = Constants.STARTMENUVIEWBUTTONHEIGHT;

	StartMenuViewButton(String text)
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
