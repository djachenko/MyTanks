package ru.nsu.fit.djachenko.mytanks;

import ru.nsu.fit.djachenko.mytanks.model.management.Client;
import ru.nsu.fit.djachenko.mytanks.model.management.ModelViewCommunicator;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;

class Starter
{
 	public static void main(String[] args) throws InterruptedException
    {
	    Client client = new Client();

	    ModelViewCommunicator communicator = client.getCommunicator();

	    new Thread(communicator).start();

	    AppWindow appWindow = new AppWindow(communicator);
	    appWindow.setVisible(true);
	}
}
