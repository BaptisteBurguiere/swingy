package swingy.Model;

public class CombatTurnResult
{
	public Entity attacker;
	public Entity defender;
	public boolean missed;
	public boolean critical;
	public boolean parried;
	public double damage;

	public CombatTurnResult(Entity attacker, Entity defender)
	{
		this.attacker = attacker;
		this.defender = defender;
		this.missed = false;
		this.critical = false;
		this.parried = false;
		this.damage = 0;
	}
}
