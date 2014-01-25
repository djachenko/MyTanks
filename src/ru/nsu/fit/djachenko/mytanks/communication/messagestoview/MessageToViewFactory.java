package ru.nsu.fit.djachenko.mytanks.communication.messagestoview;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.cells.Field;

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

	public MessageToView getDrawBulletMessage(int x, int y, Direction direction, int bulletID)
	{
		return new DrawBulletMessage(x, y, direction, bulletID);
	}

	public MessageToView getDrawTankMessage(int x, int y, Direction direction, int tankId, int playerId)
	{
		return new DrawTankMessage(x, y, direction, tankId, playerId);
	}

	public MessageToView getLevelStartedMessage(Field.State state)
	{
		return new LevelStartedMessage(state);
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
