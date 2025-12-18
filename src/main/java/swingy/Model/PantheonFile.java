package swingy.Model;

import java.util.ArrayList;
import java.util.List;

public class PantheonFile
{
	public List<Hero> heroes;
	public List<GameStats> stats;

	public PantheonFile()
	{
		this.heroes = new ArrayList<>();
		this.stats = new ArrayList<>();
	}
}
