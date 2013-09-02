package ru.nsu.fit.djachenko.mytanks.view;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.EventManager;
import ru.nsu.fit.djachenko.mytanks.model.UnexpectedSituationException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter
{
	private EventManager manager;

	Controller(EventManager manager)
	{
		this.manager = manager;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		try
		{
			switch (key)
			{
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					manager.add(Direction.RIGHT);
					break;

				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					manager.add(Direction.UP);
					break;

				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					manager.add(Direction.LEFT);
					break;

				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					manager.add(Direction.DOWN);
					break;

				case KeyEvent.VK_SPACE:
					manager.shoot();
					break;

				default:
					break;
			}
		}
		catch (UnexpectedSituationException e1)
		{
			e1.printStackTrace();
		}

		//manager.print();
	}
}
