package swingy.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameMap
{
	public enum Element
	{
		EMPTY,
		HERO,
		WALL,
		VILLAIN
	}

	private static final Random rand = new Random();

	private int			_size;
	private Element[][] _grid;
	private int 		_hero_x;
	private int			_hero_y;

	public int 			GetSize() {return this._size;}
	public Element[][]	GetGrid() {return this._grid;}
	public int			GetHeroX() {return this._hero_x;}
	public int			GetHeroY() {return this._hero_y;}


	public GameMap(int hero_level)
	{
		this._size = (hero_level - 1) * 5 + 10 - (hero_level % 2);
		this._grid = new Element[this._size][this._size];
		this._hero_x = this._size / 2;
		this._hero_y = this._size / 2;

        for (int i = 0; i < this._size; i++)
            Arrays.fill(this._grid[i], Element.EMPTY);

        // Carve maze
        //carveMaze(this._size/2, this._size/2);

        // Place hero
        this._grid[this._hero_y][this._hero_x] = Element.HERO;

        // Add villains
        placeVillains(hero_level);
	}

	private void carveMaze(int x, int y) {
        this._grid[x][y] = Element.EMPTY;

        // Directions (up, down, left, right)
        int[][] dirs = { {2,0}, {-2,0}, {0,2}, {0,-2} };
        Collections.shuffle(Arrays.asList(dirs), rand);

        for (int[] d : dirs) {
            int nx = x + d[0], ny = y + d[1];
            if (nx > 0 && ny > 0 && nx < this._size-1 && ny < this._size-1 && this._grid[nx][ny] == Element.WALL) {
                // Carve path between
                this._grid[x + d[0]/2][y + d[1]/2] = Element.EMPTY;
                carveMaze(nx, ny);
            }
        }
    }

    private void placeVillains(int heroLevel) {
        int villainCount = (this._size * this._size) / 20; // adjust density
        int placed = 0;

        while (placed < villainCount) {
            int x = rand.nextInt(this._size);
            int y = rand.nextInt(this._size);

            if (this._grid[x][y] == Element.EMPTY && this._grid[x][y] != Element.HERO) {
                this._grid[x][y] = Element.VILLAIN;
                placed++;
            }
        }
    }
}
