package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToView;
import ru.nsu.fit.djachenko.mytanks.model.Game;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.view.activities.HandleMessageTask;
import ru.nsu.fit.djachenko.mytanks.view.activities.ViewTaskPerformer;

import javax.swing.*;

public class GameView extends JFrame
{
	private FieldView fieldView;
	private ViewTaskPerformer performer = new ViewTaskPerformer();

	public GameView(Game game, MessageChannel<MessageToView> hereChannel, MessageChannel<MessageToModel> thereChannel)
	{
		initUI(game, thereChannel);

		performer.enqueue(new HandleMessageTask(hereChannel.getGetPoint(), this));
	}

	public void initUI(Game game, MessageChannel<MessageToModel> thereChannel)
	{
		Level level = game.getCurrentLevel();

		fieldView = new FieldView(level, performer);

		add(fieldView);
		TankView tankView = new TankView(level.getActiveTank());
		fieldView.add(tankView);
		fieldView.setComponentZOrder(tankView, 0);

		pack();

		setTitle("MyTanks");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addKeyListener(new Controller(thereChannel));
	}

	public void add(BulletView bulletView)
	{
		if (fieldView != null)
		{
			fieldView.add(bulletView);
		}
	}

	public void add(TankView tankView)
	{
		if (fieldView != null)
		{
			fieldView.add(tankView);
		}
	}
}
