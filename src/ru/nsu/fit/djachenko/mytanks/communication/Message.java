package ru.nsu.fit.djachenko.mytanks.communication;

public interface Message
{
	void handle(MessageAcceptor acceptor);
}
