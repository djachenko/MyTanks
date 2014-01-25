package ru.nsu.fit.djachenko.mytanks.model.cells;

import ru.nsu.fit.djachenko.mytanks.model.Direction;
import ru.nsu.fit.djachenko.mytanks.model.entries.FieldElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Field
{
	private Cell[][] table = null;
	private final CellFactory cellFactory = CellFactory.getInstance();

	public static class State
	{
		private final Field field;

		private Cell.Type[][] table;

		State(Field field)
		{
			this.field = field;

			this.table = new Cell.Type[field.height()][field.width()];

			update();
		}

		public Cell.Type at(int x, int y)
		{
			return table[y][x];
		}

		public int height()
		{
			return table.length;
		}

		public int width()
		{
			if (height() > 0)
			{
				return table[0].length;
			}
			else
			{
				return 0;
			}
		}

		public void update()
		{
			System.out.println("up");

			for (int y = 0; y < table.length; y++)
			{
				for (int x = 0; x < table[y].length; x++)
				{
					table[y][x] = field.at(x, y).getType();
				}
			}
		}
	}

	private final State state;

	protected Field(String configFile) throws IOException
	{
		init(configFile);

		state = new State(this);
	}

	private void init(String configPath) throws IOException
	{
		init(new File(configPath));
	}

	private void init(File configFile) throws IOException
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(configFile)))
		{
			String fieldParametersLine = reader.readLine();

			if (fieldParametersLine == null)
			{
				//throw new MapFormatException("Empty map file");
			}

			String[] fieldParameters = fieldParametersLine.split(" ");

			if (fieldParameters.length < 2)
			{
				//throw new MapFormatException("Wrong field parameters format");
			}

			int tableWidth = Integer.parseInt(fieldParameters[0]);
			int tableHeight = Integer.parseInt(fieldParameters[1]);

			table = new Cell[tableHeight][tableWidth];

			for (int j = 0; j < table.length; j++)
			{
				String rowString = reader.readLine();

				if (rowString == null || rowString.length() != table[j].length)
				{
					//throw new MapFormatException("Wrong map size");
				}

				for (int i = 0; i < table[j].length; i++)
				{
					switch (rowString.charAt(i))
					{
						case ' ':
						case '.':
							table[j][i] = cellFactory.getGroundCell();

							break;
						case 'x':
							table[j][i] = cellFactory.getWallCell();

							break;
						default:
							//throw new MapFormatException("Wrong map representation format");
					}
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			//throw new MapFormatException("Unparsable parameters");
		}
	}

	protected void draw(FieldElement element)
	{
		element.draw(this);
	}

	protected void erase(FieldElement element)
	{
		element.erase(this);
	}

	public int height()
	{
		return table.length;
	}

	public int width()
	{
		if (height() > 0)
		{
			return table[0].length;
		}
		else
		{
			return 0;
		}
	}

	public Cell at(int x, int y)
	{
		if (x >= 0 && x < width() && y >= 0 && y < height())
		{
			return table[y][x];
		}
		else
		{
			return null;//throw
		}
	}

	public boolean ableToMove(int x, int y, Direction dir, int depth)
	{
		return x >= 0 && x < width() && y >= 0 && y < height() && table[y][x].ableToMove(dir, depth);
	}

	public void move(int x, int y, Direction dir, int depth)
	{
		table[y][x].move(dir, depth);
		replace(x, y, cellFactory.getGroundCell());
	}

	public void move(int fromX, int fromY, int toX, int toY)//TODO: make private or remove
	{
		table[fromY][fromX].move(toX, toY);
		table[toY][toX] = table[fromY][fromX];
		table[fromY][fromX] = cellFactory.getGroundCell();
	}

	public boolean ableToReplace(int x, int y)
	{
		return x >= 0 && x < width() && y >= 0 && y < height() && table[y][x].ableToReplace();
	}

	public void replace(int x, int y, Cell cell)//TODO: make private or remove
	{
		table[y][x] = cell;
		/*else
		{
			throw forbidden()
		} */
	}

	public void print()
	{
		StringBuilder temp = new StringBuilder();

		for (int j = 0; j < height(); j++)
		{
			for (int i = 0; i < width(); i++)
			{
					temp.append(at(i, j).getType().representation);
			}

			temp.append('\n');
		}

		System.out.println(temp);
	}

	protected List<SpawnPoint> scanForSpawnPoints()
	{
		List<SpawnPoint> list = new LinkedList<>();

		for (int y = 1; y < height() - 1; y++)
		{
			for (int x = 1; x < width() - 1; x++)
			{
				boolean ok = true;

				for (int i = -1; i <= 1; i++)
				{
					for (int j = -1; j <= 1; j++)
					{
						if (table[y + j][x + i].getClass() != GroundCell.class)
						{
							ok = false;
						}
					}
				}

				if (ok)
				{
					list.add(new SpawnPoint(x, y));
				}
			}
		}

		return list;
	}

	protected boolean check(SpawnPoint point)
	{
		int x = point.getX();
		int y = point.getY();

		for (int i = -1; i <= 1; i++)
		{
			for (int j = -1; j <= 1; j++)
			{
				if (table[y + j][x + i].getClass() != GroundCell.class)
				{
					return false;
				}
			}
		}

		return true;
	}

	public State getState()
	{
		return state;
	}
}
