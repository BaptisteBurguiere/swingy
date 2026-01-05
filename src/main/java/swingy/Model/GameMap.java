package swingy.Model;

import java.util.Arrays;
import java.util.Random;

public class GameMap
{
	public enum Element
	{
		EMPTY,
		HERO,
		WALL,
		VILLAIN,
		CHEST
	}

	public enum Direction
	{
		LEFT,
		RIGHT,
		UP,
		DOWN
	}

	public enum MoveResult
	{
		MOVE,
		FIGHT,
		EXIT,
		CHEST
	}

	private static final int MAX_MAP_SIZE = 20;

	private static final Random rand = new Random();

	private int			_size;
	private Element[][] _grid;
	private Villain[][] _villains_grid;
	private int 		_hero_x;
	private int			_hero_y;
	private int			_previous_hero_x;
	private int			_previous_hero_y;
	private Villain		_boss;

	public int 			GetSize() {return this._size;}
	public Element[][]	GetGrid() {return this._grid;}
	public int			GetHeroX() {return this._hero_x;}
	public int			GetHeroY() {return this._hero_y;}

	public GameMap(Hero hero, boolean endless)
	{
		this._size = Math.min((hero.GetLevel() - 1) * 5 + 10 - (hero.GetLevel() % 2), MAX_MAP_SIZE);
		this._grid = new Element[this._size][this._size];
		this._villains_grid = new Villain[this._size][this._size];
		this._hero_x = this._size / 2;
		this._hero_y = this._size / 2;

        for (int i = 0; i < this._size; i++)
            Arrays.fill(this._grid[i], Element.EMPTY);

        // Place hero
        this._grid[this._hero_y][this._hero_x] = Element.HERO;

        // Add villains
        placeVillains(hero, endless);
		this._boss = VillainFactory.GenerateBoss(hero, endless);
	}

    private void placeVillains(Hero hero, boolean endless) {
        int villainCount = (this._size * this._size) / 20; // adjust density
        int placed = 0;

        while (placed < villainCount) {
            int x = rand.nextInt(this._size);
            int y = rand.nextInt(this._size);

            if (this._grid[x][y] == Element.EMPTY && this._grid[x][y] != Element.HERO) {
                this._grid[x][y] = Element.VILLAIN;
				this._villains_grid[x][y] = VillainFactory.GenerateVillain(hero, villainCount, endless);
                placed++;
            }
        }
    }

	public MoveResult MoveHero(Direction direction)
	{
		int new_hero_x = this._hero_x;
		int new_hero_y = this._hero_y;

		switch (direction) {
			case LEFT:
				new_hero_x--;
				break;

			case RIGHT:
				new_hero_x++;
				break;

			case UP:
				new_hero_y--;
				break;

			case DOWN:
				new_hero_y++;
		}

		if (	new_hero_x < 0 || new_hero_x >= this._size
			||	new_hero_y < 0 || new_hero_y >= this._size)
			return MoveResult.EXIT;

		if (this._grid[new_hero_y][new_hero_x] == Element.WALL)
			return MoveResult.MOVE;

		if (this._grid[new_hero_y][new_hero_x] == Element.VILLAIN)
		{
			this._grid[this._hero_y][this._hero_x] = Element.EMPTY;
			this._previous_hero_x = this._hero_x;
			this._previous_hero_y = this._hero_y;
			this._hero_x = new_hero_x;
			this._hero_y = new_hero_y;
			this._grid[new_hero_y][new_hero_x] = Element.HERO;

			return MoveResult.FIGHT;
		}
		if (this._grid[new_hero_y][new_hero_x] == Element.CHEST)
		{
			this._grid[this._hero_y][this._hero_x] = Element.EMPTY;
			this._previous_hero_x = this._hero_x;
			this._previous_hero_y = this._hero_y;
			this._hero_x = new_hero_x;
			this._hero_y = new_hero_y;
			this._grid[new_hero_y][new_hero_x] = Element.HERO;

			return MoveResult.CHEST;
		}

		this._grid[this._hero_y][this._hero_x] = Element.EMPTY;
		this._previous_hero_x = this._hero_x;
		this._previous_hero_y = this._hero_y;
		this._hero_x = new_hero_x;
		this._hero_y = new_hero_y;
		this._grid[new_hero_y][new_hero_x] = Element.HERO;

		return MoveResult.MOVE;
	}

	public void GoToLastPosition()
	{
		this._grid[this._hero_y][this._hero_x] = Element.VILLAIN;
		this._hero_x = this._previous_hero_x;
		this._hero_y = this._previous_hero_y;
		this._grid[this._hero_y][this._hero_x] = Element.HERO;
	}

	public void SpawnChest()
	{
		this._grid[this._size / 2][this._size / 2] = Element.CHEST;
	}

	public boolean IsRoomEmpty()
	{
		for (int i = 0; i < this._size; i++)
		{
			for (int j = 0; j < this._size; j++)
			{
				if (this._grid[i][j] == Element.VILLAIN)
					return false;
			}
		}

		return true;
	}

	public Villain GetCurrentVillain()
	{
		return this._villains_grid[this._hero_y][this._hero_x];
	}

	public Villain GetBoss()
	{
		return this._boss;
	}

	public boolean CanExit(boolean endless)
	{
		if (endless && !this.IsRoomEmpty())
			return false;

		int villainCount = (this._size * this._size) / 20;
		int current = 0;
		for (int i = 0; i < this._size; i++)
		{
			for (int j = 0; j < this._size; j++)
			{
				if (this._grid[i][j] == Element.VILLAIN)
				{
					current++;
					if (current > villainCount / 2)
						return false;
				}
			}
		}
		
		return true;
	}
}
