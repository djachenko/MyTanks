package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.MapFormatException;
import ru.nsu.fit.djachenko.mytanks.model.Tank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameWindow extends JFrame
{
	public GameWindow()
	{
		new Timer(100, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				iteration();
			}
		}).start();

		initUI();
	}

	public void initUI()
	{
		Level level = null;

		try
		{
			level = new Level("resources/test/Tank/TankTestMap.tnk");
			level.setTank(new Tank(level, 4, 13, Direction.DOWN));
		}
		catch (IOException | MapFormatException e)
		{
			e.printStackTrace();
		}

		add(new LevelView(level));

		pack();

		setTitle("MyTanks");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addKeyListener(new Controller(level));

		setVisible(true);
	}

	void iteration()
	{}

	public static void main(String[] args)
	{
		new GameWindow();
	}
}
