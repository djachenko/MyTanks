package ru.nsu.fit.djachenko.mytanks.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Field
{
	private Cell[][] table = null;
	private Tank tank;

	public Field()
	{
	}

	public Field(String configFile) throws IOException, MapFormatException
	{
		init(configFile);
	}

	public void init(String configFile) throws MapFormatException, IOException
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(configFile)))
		{
			String[] fieldParameters = reader.readLine().split(" ");

			int tableWidth = Integer.parseInt(fieldParameters[0]);
			int tableHeight = Integer.parseInt(fieldParameters[1]);

			table = new Cell[tableHeight][tableWidth];

			for (int j = 0; j < table.length; j++)
			{
				Cell[] row = table[j];

				String rowString = reader.readLine();

				if (rowString.length() != row.length)
				{
					throw new MapFormatException(rowString.length() + " " + row.length);
				}

				for (int i = 0; i < row.length; i++)
				{
					switch (rowString.charAt(i))
					{
						case ' ':
						case '.':
							row[i] = new FloorCell(this, i, j);

							break;
						case 'x':
							row[i] = new WallCell(this, i, j);

							break;
						default:
							throw new MapFormatException("Wrong cell representaion");
					}
				}
			}

			int tankCount = Integer.parseInt(reader.readLine());

			for (int i = 0; i < tankCount; i++)
			{
				String[] tankParams = reader.readLine().split(" ");

				if (tankParams.length != 3)
				{
					throw new MapFormatException("params");
				}

				int x = Integer.parseInt(tankParams[0]);
				int y = Integer.parseInt(tankParams[1]);
				MoveDirection direction = MoveDirection.valueOf(tankParams[2].toUpperCase());

				int dx = direction.getDx();
				int dy = direction.getDy();

				for (int j = -1; j <= 1; j++)//x
				{
					for (int k = -1; k <= 1; k++)//y
					{
						if (!((dx == 0 && k == dy && j != 0) || (dy == 0 && k != 0 && j != dx)))
						{
							if (table[y + k][x + j].type == Cell.Type.FLOOR)
							{
								table[y + k][x + j] = new TankCell(this, x + j, y + k);
							}
							else
							{
								throw new MapFormatException("Wrong tank");
							}
						}
					}
				}

				tank = new Tank(this, x, y, true, direction);
			}
		}
		catch (NumberFormatException e)
		{
			throw new MapFormatException("num");
		}
		catch (IllegalArgumentException e)
		{
			throw new MapFormatException("Illegal");
		}
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
		return table[y][x];
	}

	public boolean ableToMove(int x, int y, MoveDirection dir)
	{
		return table[ y + dir.getDy() ][ x + dir.getDx() ].ableToMove(dir);
	}

	public void move(int x, int y, MoveDirection dir)
	{
		table[ y + dir.getDy() ][ x + dir.getDx() ].move(dir);
		table[ y + dir.getDy() ][ x + dir.getDx() ] = table[y][x];
	}

	public boolean ableToReplace(int x, int y)
	{
		return table[y][x].ableToReplace();
	}

	public void replace(int sourceX, int sourceY, int destX, int destY)
	{
		table[destY][destX] = table[sourceY][sourceX];
		table[sourceY][sourceX] = null;
	}

	public void swap(int x1, int y1, int x2, int y2)
	{
		Cell temp = table[y1][x1];
		table[y1][x1] = table[y2][x2];
		table[y2][x2] = temp;
	}

	public void print()
	{
		StringBuilder temp = new StringBuilder();

		for (int j = 0; j < height(); j++)
		{
			for (int i = 0; i < width(); i++)
			{
				temp.append(at(i, j).type.representation);
			}

			temp.append('\n');
		}

		System.out.print(temp);
	}

	public static void main(String[] args)
	{
		try
		{
			new Field("map.tnk").print();
		}
		catch (IOException | MapFormatException e)
		{
			e.printStackTrace();
		}
	}
}
