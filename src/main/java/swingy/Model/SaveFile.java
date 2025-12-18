package swingy.Model;

public class SaveFile
{
	public static final int NB_SAVES = 8;

	public Hero[] heroes;
	public GameStats[] stats;

	public SaveFile()
	{
		this.heroes = new Hero[NB_SAVES];
		this.stats = new GameStats[NB_SAVES];
	}
}


