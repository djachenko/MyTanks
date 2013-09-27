package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.MessageManager;
import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.model.Level;

import javax.swing.*;

public class GameView extends JFrame
{
	public GameView(Game game, MessageManager messageManager)
	{
		initUI(game, messageManager);
	}

	public void initUI(Game game, MessageManager messageManager)
	{
		Level level = game.getCurrentLevel();

		FieldView fieldView = new FieldView(level);

		add(fieldView);
		TankView tankView = new TankView(level.getTank());
		fieldView.add(tankView);
		fieldView.setComponentZOrder(tankView, 0);

		pack();

		setTitle("MyTanks");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addKeyListener(new Controller(messageManager));
	}
}
