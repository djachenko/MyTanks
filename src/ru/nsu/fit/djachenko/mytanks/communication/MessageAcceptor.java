package ru.nsu.fit.djachenko.mytanks.communication;

import ru.nsu.fit.djachenko.mytanks.communication.messagestomodel.MessageToModel;
import ru.nsu.fit.djachenko.mytanks.communication.messagestoview.MessageToView;

public interface MessageAcceptor
{
	void accept(MessageToView message);
	void accept(MessageToModel message);
}
