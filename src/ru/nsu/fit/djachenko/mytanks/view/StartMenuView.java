package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.Constants;
import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModelFactory;
import ru.nsu.fit.djachenko.mytanks.model.management.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StartMenuView extends JPanel
{
	StartMenuView(MessageAcceptor channel)
	{
		initUI(channel);
	}

	private void initUI(final MessageAcceptor channel)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton startSingleGameButton = new StartMenuViewButton("Start game vs computer");
		JButton startSharedGameButton = new StartMenuViewButton("Start shared game");
		JButton connectButton = new StartMenuViewButton("Connect to server");
		JButton showRecordsButton = new StartMenuViewButton("Show records");
		JButton settingsButton = new StartMenuViewButton("Settings");
		JButton exitButton = new StartMenuViewButton("Exit");

		startSingleGameButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MessageToModelFactory.getInstance().getStartGameMessage(GameMode.SINGLE).handle(channel);
			}
		});
		startSharedGameButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MessageToModelFactory.getInstance().getStartGameMessage(GameMode.SHARED).handle(channel);
			}
		});
		connectButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("server");
			}
		});
		showRecordsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("records!");
			}
		});
		settingsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Settings!");
			}
		});
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		add(Box.createRigidArea(new Dimension(0, 5)));
		add(startSingleGameButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(startSharedGameButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(connectButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(showRecordsButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(settingsButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(exitButton);
		add(Box.createRigidArea(new Dimension(0, 5)));

		int width = Constants.STARTMENUVIEWBUTTONWIDTH + 10;
		int height = 5 * (Constants.STARTMENUVIEWBUTTONHEIGHT + 5);

		setPreferredSize(new Dimension(width, height));
	}
}
