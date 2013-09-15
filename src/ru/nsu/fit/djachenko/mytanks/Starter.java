package ru.nsu.fit.djachenko.mytanks;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.MapFormatException;
import ru.nsu.fit.djachenko.mytanks.model.Tank;
import ru.nsu.fit.djachenko.mytanks.view.GameWindow;

import java.io.IOException;

public class Starter
{
	public static void main(String[] args)
	{
		Level level = null;

		try
		{
			level = new Level("resources/test/Tank/TankTestMap.tnk");
			level.setTank(new Tank(level, 4, 13, Direction.DOWN));
		}
		catch (IOException | MapFormatException e)
		{
			e.printStackTrace();
		}

		new GameWindow(level).setVisible(true);
	}
}
