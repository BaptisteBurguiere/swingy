package swingy.Model;

public class CombatTurnResult
{
	public Entity attacker;
	public Entity defender;
	public boolean missed;
	public boolean critical;
	public boolean parried;
	public double damage;
	public boolean defense_stance;
	public boolean hero_turn;
	public boolean try_flee;
	public boolean flee_successful;

	public CombatTurnResult(Entity attacker, Entity defender)
	{
		this.attacker = attacker;
		this.defender = defender;
		this.missed = false;
		this.critical = false;
		this.parried = false;
		this.damage = 0;
		this.defense_stance = false;
		this.hero_turn = false;
		this.try_flee = false;
		this.flee_successful = false;
	}
}
