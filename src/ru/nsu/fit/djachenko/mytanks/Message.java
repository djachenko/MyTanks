package ru.nsu.fit.djachenko.mytanks;

public abstract class Message
{
	public enum Type
	{
		MOVEMESSAGE,
		NUMBERMESSAGE,
		SHOOTMESSAGE,
		CHANGEMENUMESSAGE
	}

	public final Type type;

	Message(Type type)
	{
		this.type = type;
	}
}
