package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.Level;
import ru.nsu.fit.djachenko.mytanks.model.UnexpectedSituationException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter
{
	private Level level;

	Controller(Level level)
	{
		this.level = level;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				level.moveTank(Direction.RIGHT);
				break;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				level.moveTank(Direction.UP);
				break;

			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				level.moveTank(Direction.LEFT);
				break;

			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				level.moveTank(Direction.DOWN);
				break;

			case KeyEvent.VK_SPACE:
				level.shoot();
				break;

			default:
				break;
		}

		//level.print();
	}
}
