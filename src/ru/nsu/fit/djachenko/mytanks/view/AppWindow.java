package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.communication.*;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.ChooseLevelMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.LevelStartedMessage;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;
import ru.nsu.fit.djachenko.mytanks.controller.ArrowsController;
import ru.nsu.fit.djachenko.mytanks.controller.WASDController;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.management.Client;
import ru.nsu.fit.djachenko.mytanks.model.management.LevelHolder;
import ru.nsu.fit.djachenko.mytanks.view.activities.HandleMessageTask;
import ru.nsu.fit.djachenko.mytanks.view.activities.ViewTaskPerformer;

import javax.swing.*;

public class AppWindow extends JFrame
{
	private final MessageChannel<MessageToView> channelToView;
	private final MessageChannel<MessageToModel> channelToClient;
	private StartMenuView startMenu;
	private ChooseLevelView chooseLevelMenu;
	private LevelView currentLevelView;
	private final ViewTaskPerformer performer;

	private JPanel currentPanel;

	{
		channelToView = new MessageChannel<>();
		performer = new ViewTaskPerformer();

		performer.enqueue(new HandleMessageTask(channelToView, this));
	}

   	public AppWindow(Client client)
    {
	    this.channelToClient = client.getChannelToClient();
	    client.setChannelToView(this.getChannelToView());

	    initUI();
	    setStartMenu();
    }

	private void initUI()
	{
		setTitle("MyTanks");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void setStartMenu()
	{
		if (startMenu == null)
		{
			startMenu = new StartMenuView(channelToClient);//controller?
		}

		if (currentPanel != null)
		{
			remove(currentPanel);
		}

		add(startMenu);
		currentPanel = startMenu;
		pack();
		setLocationRelativeTo(null);
	}

	private void setLevelChooseMenu()
	{
		if (chooseLevelMenu == null)
		{
			chooseLevelMenu = new ChooseLevelView(channelToClient, new LevelHolder());
		}

		if (currentPanel != null)
		{
			remove(currentPanel);
		}

		currentPanel = chooseLevelMenu;
		add(currentPanel);
		pack();
		setLocationRelativeTo(null);
	}

	private void startLevel(Field.State state, int wasdId, int arrowsId)//REFACTOR
	{
		currentLevelView = new LevelView(state, wasdId, arrowsId);
		currentLevelView.addKeyListener(new WASDController(channelToClient, wasdId));
		currentLevelView.addKeyListener(new ArrowsController(channelToClient, arrowsId));

		if (currentPanel != null)
		{
			remove(currentPanel);
		}

		currentPanel = currentLevelView;

		add(currentPanel);
		pack();
		setLocationRelativeTo(null);
		currentLevelView.requestFocus();
	}

	MessageChannel<MessageToView> getChannelToView()
	{
		return channelToView;
	}

	public void accept(MessageToView message)
	{
		if (currentLevelView != null)
		{
			message.handle(currentLevelView);
		}
	}

	public void accept(ChooseLevelMessage message)
	{
		setLevelChooseMenu();
	}

	public void accept(LevelStartedMessage message)
	{
		startLevel(message.getState(), message.getWasdId(), message.getArrowsId());
	}
}
