package ru.nsu.fit.djachenko.mytanks.model;

public class UnexpectedSituationException extends Exception
{
	public UnexpectedSituationException()
	{}

	public UnexpectedSituationException(String message)
	{
		super(message);
	}
}
