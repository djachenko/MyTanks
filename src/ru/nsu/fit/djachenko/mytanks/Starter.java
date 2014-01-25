package ru.nsu.fit.djachenko.mytanks;

import ru.nsu.fit.djachenko.mytanks.model.management.Client;
import ru.nsu.fit.djachenko.mytanks.view.AppWindow;

public class Starter
{
 	public static void main(String[] args) throws InterruptedException
    {
	    Client client = new Client();
	    new Thread(client).start();

	    AppWindow appWindow = new AppWindow(client);
	    appWindow.setVisible(true);
	}
}
