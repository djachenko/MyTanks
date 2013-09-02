package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameWindow extends JFrame
{
	public GameWindow(EventManager manager)
	{
		new Timer(100, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				iteration();
			}
		}).start();

		initUI(manager);
	}

	public void initUI(EventManager manager)
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

		FieldView fieldView = new FieldView(level);

		add(fieldView);
		fieldView.add(new TankView(level.getTank()));

		pack();

		setTitle("MyTanks");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addKeyListener(new Controller(manager));

		setVisible(true);
	}

	void iteration()
	{}
}
