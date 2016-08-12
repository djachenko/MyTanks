package ru.nsu.fit.djachenko.mytanks.model.management.ai;

import ru.nsu.fit.djachenko.mytanks.model.cells.Field;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;
import ru.nsu.fit.djachenko.mytanks.model.management.ai.imperatives.Imperative;

public interface Strategy
{
	Imperative run(Tank.State tankState, Field.State fieldState, AI parent);
	int getPriority();
}
