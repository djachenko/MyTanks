package ru.nsu.fit.djachenko.mytanks.model.entries;

import ru.nsu.fit.djachenko.mytanks.model.cellls.Field;

public interface FieldElement
{
	void draw(Field field);
	void erase(Field field);
}
