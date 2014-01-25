package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.management.LevelHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ChooseLevelView extends JPanel
{
	ChooseLevelView(MessageAcceptor channel, LevelHolder levelHolder)
	{
		initUI(channel, levelHolder);
	}

	private void initUI(final MessageAcceptor channel, final LevelHolder levelHolder)
	{
		int count = levelHolder.countLevels();

		for (int i = 0; i < count; i++)
		{
			JButton levelButton = new StartMenuViewButton("Level " + (i + 1));

			final int finalI = i;

			levelButton.addActionListener(new ActionListener()
			{
				private final int id = finalI;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					MessageToModelFactory.getInstance().getStartLevelMessage(id).handle(channel);
				}
			});

			add(Box.createRigidArea(new Dimension(0, 5)));
			add(levelButton);
			add(Box.createRigidArea(new Dimension(0, 15)));
		}
	}
}
