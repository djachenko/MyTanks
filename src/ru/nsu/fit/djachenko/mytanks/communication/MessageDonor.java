package ru.nsu.fit.djachenko.mytanks.communication;

public interface MessageDonor<T extends Message>
{
	public T get();
	public T tryGet();
}
