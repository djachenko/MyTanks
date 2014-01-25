package ru.nsu.fit.djachenko.mytanks.model.entries.activities;

public interface Task
{
	public void execute(int iteration);
	public boolean hasToBeRepeated();
}
