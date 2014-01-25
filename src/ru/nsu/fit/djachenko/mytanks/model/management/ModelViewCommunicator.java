package ru.nsu.fit.djachenko.mytanks.model.management;

import ru.nsu.fit.djachenko.mytanks.communication.MessageAcceptor;
import ru.nsu.fit.djachenko.mytanks.communication.MessageDonor;
import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;

import java.util.LinkedList;
import java.util.Queue;

public class ModelViewCommunicator implements Runnable, MessageAcceptor, MessageDonor<MessageToView>
{
	private final Queue<MessageToView> queueToView = new LinkedList<>();
	private final Queue<MessageToModel> queueToModel = new LinkedList<>();

	private final Client client;

	ModelViewCommunicator(Client client)
	{
		this.client = client;
	}

	@Override
	public void run()
	{
		while (true)
		{
			synchronized (queueToModel)
			{
				while (queueToModel.isEmpty())
				{
					try
					{
						queueToModel.wait();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}

				queueToModel.remove().handle(client);
			}
		}
	}

	@Override
	public synchronized void accept(MessageToView message)
	{
		queueToView.add(message);
	}

	@Override
	public void accept(MessageToModel message)
	{
		synchronized (queueToModel)
		{
			queueToModel.add(message);
			queueToModel.notify();
		}
	}

	@Override
	public synchronized MessageToView get()
	{
		while (queueToView.isEmpty())
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		return queueToView.remove();
	}

	@Override
	public synchronized MessageToView tryGet()
	{
		if (!queueToView.isEmpty())
		{
			return queueToView.remove();
		}
		else
		{
			return null;
		}
	}
}
