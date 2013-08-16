package ru.nsu.fit.djachenko.mytanks;

public class UnexpectedSituation extends Exception
{
	public UnexpectedSituation()
	{}

	public UnexpectedSituation(String message)
	{
		super(message);
	}
}
