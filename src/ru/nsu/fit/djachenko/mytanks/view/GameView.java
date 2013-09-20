package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Level;

import javax.swing.*;

public class GameView extends JFrame
{
	public GameView(Level level)
	{
		initUI(level);
	}

	public void initUI(Level level)
	{
		FieldView fieldView = new FieldView(level);

		add(fieldView);
		TankView tankView = new TankView(level.getTank());
		fieldView.add(tankView);
		fieldView.setComponentZOrder(tankView, 0);

		pack();

		setTitle("MyTanks");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addKeyListener(new Controller(level));
	}

	void iteration()
	{

	}
}
