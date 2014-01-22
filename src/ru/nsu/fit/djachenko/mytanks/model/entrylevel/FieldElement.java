package ru.nsu.fit.djachenko.mytanks.model.entrylevel;

import ru.nsu.fit.djachenko.mytanks.model.entrylevel.celllevel.Field;

public interface FieldElement
{
	void draw(Field field);
	void erase(Field field);
}
