package swingy.Controller;

import swingy.Model.CombatTurnResult;
import swingy.Model.Entity;
import swingy.Model.Hero;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;

public class Combat
{
	private Hero 	_hero;
	private Villain _villain;

	private final double TP_THRESHOLD = 100;
	private final double BASE_GAIN = 4;
	private final double EXPONENT = 0.75;
	private final double MIN_HIT_CHANCE = 0.1;
	private final double LUCK_EVASION_CHANCE = 0.005;
	private final double LUCK_HIT_CHANCE = 0.005;
	private final double LUCK_CRITICAL_CHANCE = 0.008;
	private final double DEFENSE_SCALING = 0.5;

	private double _hero_tp;
	private double _villain_tp;

	public Combat(Hero hero, Villain villain)
	{
		this._hero = hero;
		this._villain = villain;
		this._hero_tp = 0;
		this._villain_tp = 0;
	}

	private double GetTurnGain(Entity entity)
	{
		return BASE_GAIN + Math.pow(entity.GetStatistic(StatisticTemplate.Type.SPEED).GetValue(), EXPONENT);
	}

	private boolean IsHeroTurn()
	{
		while (true)
		{
			this._hero_tp += GetTurnGain(this._hero);
			if (this._hero_tp >= TP_THRESHOLD)
			{
				this._hero_tp -= TP_THRESHOLD;
				return true; 
			}

			this._villain_tp += GetTurnGain(this._villain);
			if (this._villain_tp >= TP_THRESHOLD)
			{
				this._villain_tp -= TP_THRESHOLD;
				return false; 
			}
		}
	}

	private boolean IsMissed(Entity attacker, Entity defender)
	{
		double attacker_luck = attacker.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();
		double defender_luck = defender.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();
		double accuracy = attacker.GetStatistic(StatisticTemplate.Type.ACCURACY).GetValue();
		double evasion = defender.GetStatistic(StatisticTemplate.Type.EVASION).GetValue();

		double hit_chance = Math.max(MIN_HIT_CHANCE, (accuracy + attacker_luck * LUCK_HIT_CHANCE) - (evasion + defender_luck * LUCK_EVASION_CHANCE));
		double hit_roll = Math.random();

		return hit_roll < hit_chance;
	}

	private boolean IsCritical(Entity attacker)
	{
		double crit_chance = attacker.GetStatistic(StatisticTemplate.Type.CRIT_CHANCE).GetValue();
		double luck = attacker.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();
		
		double crit_roll = Math.random();

		return crit_roll < crit_chance + luck * LUCK_CRITICAL_CHANCE;
	}

	private double CalculateDamage(Entity attacker, Entity defender, CombatTurnResult result)
	{
		double attack = attacker.GetStatistic(StatisticTemplate.Type.ATTACK).GetValue();
		double defense = defender.GetStatistic(StatisticTemplate.Type.DEFENSE).GetValue();

		double damage = attack - (defense * DEFENSE_SCALING);
		damage = Math.max(damage, 1);

		if (IsCritical(attacker))
		{
			result.critical = true;
			double crit_damage = attacker.GetStatistic(StatisticTemplate.Type.CRIT_DAMAGE).GetValue();
			damage *= crit_damage;
		}

		return damage;
	}

	private CombatTurnResult HeroTurn()
	{
		CombatTurnResult result = new CombatTurnResult(this._hero, this._villain);
		if (IsMissed(this._hero, this._villain))
		{
			result.missed = true;
			return result;
		}

		double damage = CalculateDamage(this._hero, this._villain, result);
		this._villain.TakeDamage(damage);

		return result;
	}

	private CombatTurnResult VillainTurn()
	{
		CombatTurnResult result = new CombatTurnResult(this._villain, this._hero);
		if (IsMissed(this._villain, this._hero))
		{
			result.missed = true;
			return result;
		}

		double damage = CalculateDamage(this._villain, this._hero, result);
		this._hero.TakeDamage(damage);
		result.damage = damage;

		return result;
	}

	public void Start()
	{
		while (true)
		{
			CombatTurnResult result;
			if (IsHeroTurn())
				result = HeroTurn();
			else
				result = VillainTurn();
		}
	}
}
