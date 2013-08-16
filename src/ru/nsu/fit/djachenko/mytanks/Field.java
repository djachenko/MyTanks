package ru.nsu.fit.djachenko.mytanks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Field
{
	private Cell[][] field = null;

	Field()
	{
	}

	public void init(String configFile) throws IOException
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(configFile)))
		{
			int width = Integer.parseInt(reader.readLine());
			int height = Integer.parseInt(reader.readLine());

			field = new Cell[height][width];

			for (int y = 0; y < width; y++)
			{
				for (int x = 0; x < width; x++)
				{
					switch (reader.read())
					{
						case 'x':
							field[y][x] = null;

							break;
						case '.':
							field[y][x] = null;

							break;
						case 'f':
							field[y][x] = null;

							break;
						case 'e':
							field[y][x] = null;

							break;
						default:
							break;
					}
				}
			}
		}
	}

	public int height()
	{
		return field.length;
	}

	public int width()
	{
		if (height() > 0)
		{
			return field[0].length;
		}
		else
		{
			return 0;
		}
	}

	public Cell at(int x, int y)
	{
		return field[y][x];
	}

	public boolean ableToMove(int x, int y, MoveDirection dir)
	{
		return field[ y + dir.getDy() ][ x + dir.getDx() ].ableToMove(dir);
	}

	public void move(int x, int y, MoveDirection dir)
	{
		field[ y + dir.getDy() ][ x + dir.getDx() ].move(dir);
		field[ y + dir.getDy() ][ x + dir.getDx() ] = field[y][x];
	}

	public boolean ableToReplace(int x, int y)
	{
		return field[y][x].ableToReplace();
	}

	public void replace(int sourceX, int sourceY, int destX, int destY)
	{
		field[destY][destX] = field[sourceY][sourceX];
		field[sourceY][sourceX] = null;
	}

	public void swap(int x1, int y1, int x2, int y2)
	{
		Cell temp = field[y1][x1];
		field[y1][x1] = field[y2][x2];
		field[y2][x2] = temp;
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
}
