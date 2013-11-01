package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.communication.MessageChannel;
import ru.nsu.fit.djachenko.mytanks.communication.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.model.LevelHolder;
import ru.nsu.fit.djachenko.mytanks.communication.StartLevelMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseLevelView extends JPanel
{
	ChooseLevelView(MessageChannel<MessageToModel> channel, LevelHolder levelHolder)
	{
		initUI(channel, levelHolder);
	}

	private void initUI(final MessageChannel<MessageToModel> channel, final LevelHolder levelHolder)
	{
		int count = levelHolder.countLevels();

		for (int i = 0; i < count; i++)
		{
			JButton levelButton = new StartMenuViewButton("Level " + (i + 1));

			final int finalI = i;

			levelButton.addActionListener(new ActionListener()
			{
				private int id = finalI;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					channel.set(new StartLevelMessage(id));
				}
			});

			add(Box.createRigidArea(new Dimension(0, 5)));
			add(levelButton);
			add(Box.createRigidArea(new Dimension(0, 15)));
		}
	}
}
