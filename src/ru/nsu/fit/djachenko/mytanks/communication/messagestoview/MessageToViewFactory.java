package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.entries.Bullet;
import ru.nsu.fit.djachenko.mytanks.model.entries.Level;
import ru.nsu.fit.djachenko.mytanks.model.entries.Tank;

import java.util.HashMap;
import java.util.Map;

public class MessageToViewFactory
{
	private static MessageToViewFactory instance = new MessageToViewFactory();

	private static MessageToView chooseLevelMessage = new ChooseLevelMessage();
	private Map<Integer, MessageToView> bulletMovedMessages = new HashMap<>();

	private MessageToViewFactory()
	{}

	public static MessageToViewFactory getInstance()
	{
		return instance;
	}

	public MessageToView getBulletMovedMessage(int bulletId)
	{
		if (!bulletMovedMessages.containsKey(bulletId))
		{
			bulletMovedMessages.put(bulletId, new BulletMovedMessage(bulletId));
		}

		return bulletMovedMessages.get(bulletId);
	}

	public MessageToView getBulletRemovedMessage(int bulletId)
	{
		return new BulletRemovedMessage(bulletId);
	}

	public MessageToView getChooseLevelMessage()
	{
		return chooseLevelMessage;
	}

	public MessageToView getDrawBulletMessage(Bullet bullet)
	{
		return new DrawBulletMessage(bullet);
	}

	public MessageToView getDrawTankMessage(Tank tank, int playerId)
	{
		return new DrawTankMessage(tank, playerId);
	}

	public MessageToView getLevelStartedMessage(Level level)
	{
		return new LevelStartedMessage(level);
	}

	public MessageToView getTankMovedMessage(int tankId, Direction direction)
	{
		return new TankMovedMessage(tankId, direction);
	}

	public MessageToView getTankRemovedMessage(int tankId)
	{
		return new TankRemovedMessage(tankId);
	}
}
