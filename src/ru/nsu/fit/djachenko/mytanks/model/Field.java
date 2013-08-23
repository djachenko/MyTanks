package ru.nsu.fit.djachenko.mytanks.model;

import ru.nsu.fit.djachenko.mytanks.model.cells.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Field
{
	private Cell[][] table = null;

	public Field()
	{}

	public Field(int width, int height)//empty field
	{
		table = new Cell[height][width];

		for (int y = 0; y < table.length; y++)
		{
			for (int x = 0; x < table[y].length; x++)
			{
				table[y][x] = new GroundCell();
			}
		}
	}

	public Field(String configFile) throws IOException, MapFormatException
	{
		init(configFile);
	}

	public void init(String configPath) throws IOException, MapFormatException
	{
		init(new File(configPath));
	}

	public void init(File configFile) throws MapFormatException, IOException
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(configFile)))
		{
			String fieldParametersLine = reader.readLine();

			if (fieldParametersLine == null)
			{
				throw new MapFormatException("Empty map file");
			}

			String[] fieldParameters = fieldParametersLine.split(" ");

			if (fieldParameters.length < 2)
			{
				throw new MapFormatException("Wrong field parameters format");
			}

			int tableWidth = Integer.parseInt(fieldParameters[0]);
			int tableHeight = Integer.parseInt(fieldParameters[1]);

			table = new Cell[tableHeight][tableWidth];

			for (int j = 0; j < table.length; j++)
			{
				String rowString = reader.readLine();

				if (rowString == null || rowString.length() != table[j].length)
				{
					throw new MapFormatException("Wrong map size");
				}

				for (int i = 0; i < table[j].length; i++)
				{
					switch (rowString.charAt(i))
					{
						case ' ':
						case '.':
							table[j][i] = new GroundCell();

							break;
						case 'x':
							table[j][i] = new WallCell(i, j);

							break;
						default:
							throw new MapFormatException("Wrong map representation format");
					}
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			throw new MapFormatException("Unparsable parameters");
		}
	}

	public void draw(Tank tank) throws MapFormatException
	{
		int x = tank.getX();
		int y = tank.getY();
		Direction direction = tank.getDirection();

		int dx = direction.getDx();
		int dy = direction.getDy();


		for (int k = -1; k <= 1; k++)//y
		{
			for (int j = -1; j <= 1; j++)//x
			{
				if (!((dx == 0 && k == dy && j != 0) || (dy == 0 && k != 0 && j == dx)))
				{
					/*if (ableToReplace(x + j, y + k))
					{
						replace(x + j, y + k, new TankCell(this, tank, x + j, y + k));
					}
					 */
					if (table[y + k][x + j].type == Cell.Type.GROUND)
					{
						table[y + k][x + j] = new TankCell(this, tank, x + j, y + k);
					}
					else
					{
						throw new MapFormatException("Tank overlaps " + table[y + k][x + j].type.name().toLowerCase() + " at (" + (x + j) + "; " + (y + k) + ')');
					}
				}
			}

			System.out.println();
		}
	}

	void erase(Tank tank)
	{
		int x = tank.getX();
		int y = tank.getY();
		Direction direction = tank.getDirection();

		int dx = direction.getDx();
		int dy = direction.getDy();

		for (int j = -1; j <= 1; j++)//x
		{
			for (int k = -1; k <= 1; k++)//y
			{
				if (!((dx == 0 && k == dy && j != 0) || (dy == 0 && k != 0 && j == dx)))
				{
					table[y + k][x + j] = new GroundCell();
				}
			}
		}
	}

	public void draw(Bullet bullet)
	{
		int x = bullet.getX();
		int y = bullet.getY();

		replace(x, y, new BulletCell(this, bullet, x, y));
	}

	public void erase(Bullet bullet)
	{
		replace(bullet.getX(), bullet.getY(), new GroundCell());
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
		replace(x, y, new GroundCell());

		print();
	}

	public void move(int fromX, int fromY, int toX, int toY)
	{
		table[fromY][fromX].move(toX, toY);
		table[toY][toX] = table[fromY][fromX];
		table[fromY][fromX] = new GroundCell();
	}

	public boolean ableToReplace(int x, int y)
	{
		return x >= 0 && x < width() && y >= 0 && y < height() && table[y][x].ableToReplace();
	}

	public void replace(int x, int y, Cell cell)
	{
		table[y][x] = cell;
		/*else
		{
			throw forbidden()
		} */
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

		System.out.println(temp);
	}
}
