package ru.nsu.fit.djachenko.mytanks.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelHolder
{
	private static final String CONFIG = "resources/config";

	private static String[] levels;

	public LevelHolder()
	{
		if (levels == null)
		{
			try(BufferedReader reader = new BufferedReader(new FileReader(CONFIG)))
			{
				String string = reader.readLine();

				int count = 0;

				if (string != null)
				{
					count = Integer.parseInt(string);
				}

				levels = new String[count];

				for (int i = 0; i < count; i++)
				{
					levels[i] = reader.readLine();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public String getLevel(int index)
	{
		if (levels.length > index)
		{
			return levels[index];
		}
		else
		{
			return null;
		}
	}

	public int countLevels()
	{
		return levels.length;
	}
}
