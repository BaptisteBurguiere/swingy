package swingy.Model;

public class CombatResult
{
	public boolean	flee;
	public boolean	hero_win;
	public int		exp;

	public CombatResult()
	{
		this.flee = false;
		this.hero_win = false;
		this.exp = 0;
	}
}
