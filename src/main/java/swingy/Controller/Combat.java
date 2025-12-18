package swingy.Controller;

import java.util.ArrayList;
import java.util.List;

import swingy.Model.CombatResult;
import swingy.Model.CombatTurnResult;
import swingy.Model.Entity;
import swingy.Model.GameStats;
import swingy.Model.Hero;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;
import swingy.View.Gui.SwingView;

public class Combat
{
	private Hero 	_hero;
	private Villain _villain;
	private boolean	_is_boss;

	private final double TP_THRESHOLD = 100;
	private final double BASE_GAIN = 4;
	private final double EXPONENT = 0.75;
	private final double MIN_HIT_CHANCE = 0.1;
	private final double LUCK_EVASION_CHANCE = 0.005;
	private final double LUCK_HIT_CHANCE = 0.005;
	private final double LUCK_CRITICAL_CHANCE = 0.005;
	private final double DEFENSE_SCALING = 0.2;
	private final double DAMAGE_SPREAD = 0.2;
	private final double LUCK_DAMAGE_SPREAD = 0.005;
	private final double DEFENSE_STANCE_MULTIPLIER = 1.7;
	private final double DEFENSE_PARRY_CHANCE = 0.003;
	private final double LUCK_PARRY_CHANCE = 0.001;
	private final double PARRY_DAMAGE_MULTIPLIER = 0.8;
	private final int SIMULATE_NEXT_TURNS = 5;
	private final double LUCK_FLEE_CHANCE = 0.003;
	private final double MAX_PARRY_CHANCE = 0.7;

	private double	_hero_tp;
	private double	_villain_tp;
	private boolean	_hero_defense_stance;

	public Combat(Hero hero, Villain villain, boolean is_boss)
	{
		this._hero = hero;
		this._villain = villain;
		this._is_boss = is_boss;
		this._hero_tp = 0;
		this._villain_tp = 0;
		this._hero_defense_stance = false;
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

	List<Entity> SimulateNextTurns(int nb_turns)
	{
		double hero_tp = this._hero_tp;
		double villain_tp = this._villain_tp;

		List<Entity> next_turns = new ArrayList<>();

		for (int i = 0; i < nb_turns; i++)
		{
			while (true)
			{
				hero_tp += GetTurnGain(this._hero);
				if (hero_tp >= TP_THRESHOLD)
				{
					hero_tp -= TP_THRESHOLD;
					next_turns.add(this._hero);
					break;
				}

				villain_tp += GetTurnGain(this._villain);
				if (villain_tp >= TP_THRESHOLD)
				{
					villain_tp -= TP_THRESHOLD;
					next_turns.add(this._villain);
					break;
				}
			}
		}

		return next_turns;
	}

	private boolean IsMissed(Entity attacker, Entity defender)
	{
		double attacker_luck = attacker.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();
		double defender_luck = defender.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();
		double accuracy = attacker.GetStatistic(StatisticTemplate.Type.ACCURACY).GetValue();
		double evasion = defender.GetStatistic(StatisticTemplate.Type.EVASION).GetValue();

		double hit_chance = Math.max(MIN_HIT_CHANCE, (accuracy + attacker_luck * LUCK_HIT_CHANCE) - (evasion + defender_luck * LUCK_EVASION_CHANCE));
		double hit_roll = Math.random();

		return hit_roll > hit_chance;
	}

	private boolean IsCritical(Entity attacker)
	{
		double crit_chance = attacker.GetStatistic(StatisticTemplate.Type.CRIT_CHANCE).GetValue();
		double luck = attacker.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();
		
		double crit_roll = Math.random();

		return crit_roll < crit_chance + luck * LUCK_CRITICAL_CHANCE;
	}

	private boolean IsParry(Entity defender)
	{
		double defense = defender.GetStatistic(StatisticTemplate.Type.DEFENSE).GetValue();
		double luck = defender.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();

		double parry_chance = Math.min(defense * DEFENSE_PARRY_CHANCE + luck * LUCK_PARRY_CHANCE, MAX_PARRY_CHANCE);

		double parry_roll = Math.random();

		return parry_roll < parry_chance;
	}

	private double CalculateDamage(Entity attacker, Entity defender, CombatTurnResult result, boolean defense_stance)
	{
    double attack = attacker.GetStatistic(StatisticTemplate.Type.ATTACK).GetValue();
    double defense = defender.GetStatistic(StatisticTemplate.Type.DEFENSE).GetValue();
    double luck = attacker.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();

    if (defense_stance)
        defense *= DEFENSE_STANCE_MULTIPLIER;

	double damage = attack - (defense * DEFENSE_SCALING);

    double spread_roll = Math.min(1., Math.random() * (1 + luck * LUCK_DAMAGE_SPREAD));
    damage *= 1. + (((DAMAGE_SPREAD * 2) * spread_roll) - DAMAGE_SPREAD);

    if (IsCritical(attacker))
    {
        result.critical = true;
        double crit_damage = attacker.GetStatistic(StatisticTemplate.Type.CRIT_DAMAGE).GetValue();
        damage *= crit_damage;
    }

    return Math.max(damage, 1);
	}

	private double CalculateParryDamage(Entity attacker, Entity defender)
	{
		double attack = attacker.GetStatistic(StatisticTemplate.Type.ATTACK).GetValue();
		double defense = attacker.GetStatistic(StatisticTemplate.Type.DEFENSE).GetValue();
		double luck = defender.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();

		double damage = attack - (defense * DEFENSE_SCALING);
		double spread_roll = Math.min(1., Math.random() * (1 + luck * LUCK_DAMAGE_SPREAD));
		damage *= 1. + (((DAMAGE_SPREAD * 2) * spread_roll) - DAMAGE_SPREAD);
		damage *= PARRY_DAMAGE_MULTIPLIER;
		damage = Math.max(damage, 1);

		return damage;
	}

	private boolean IsFlee()
	{
		double hero_speed = this._hero.GetStatistic(StatisticTemplate.Type.SPEED).GetValue();
		double hero_evasion = this._hero.GetStatistic(StatisticTemplate.Type.EVASION).GetValue();
		double hero_luck = this._hero.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();
		double villain_speed = this._villain.GetStatistic(StatisticTemplate.Type.SPEED).GetValue();
		double villain_evasion = this._villain.GetStatistic(StatisticTemplate.Type.EVASION).GetValue();

		double hero_agility = hero_speed * (1.0 + hero_evasion);
		double villain_agility = villain_speed * ( 1.0 + villain_evasion);

		double flee_chance = (hero_agility / (hero_agility + villain_agility)) + hero_luck * LUCK_FLEE_CHANCE;

		double flee_roll = Math.random();

		return flee_chance > flee_roll;
	}

	private CombatTurnResult HeroTurn() throws Exception
	{
		Game game_controller = Game.GetInstance();

		this._hero_defense_stance = false;
		CombatTurnResult result = new CombatTurnResult(this._hero, this._villain);
		result.hero_turn = true;

		List<Entity> next_turns = SimulateNextTurns(SIMULATE_NEXT_TURNS);

		switch (game_controller.DisplayHeroCombatChoice(_hero, _villain, next_turns, _is_boss)) {
			case FLEE:
				result.try_flee = true;
				if (IsFlee())
					result.flee_successful = true;
				break;

			case ATTACK:
				if (IsMissed(this._hero, this._villain))
				{
					result.missed = true;
					return result;
				}

				double damage = CalculateDamage(this._hero, this._villain, result, false);
				this._villain.TakeDamage(damage);
				result.damage = damage;
				break;

			case DEFEND:
				this._hero_defense_stance = true;
				result.defense_stance = true;
				break;
		
			default:
				break;
		}

		return result;
	}

	private CombatTurnResult VillainTurn()
	{
		CombatTurnResult result = new CombatTurnResult(this._villain, this._hero);
		result.hero_turn = false;

		if (IsMissed(this._villain, this._hero))
		{
			result.missed = true;
			return result;
		}

		if (this._hero_defense_stance && IsParry(this._hero))
		{
			result.parried = true;
			double damage = CalculateParryDamage(_villain, _hero);
			this._villain.TakeDamage(damage);
			result.damage = damage;
		}
		else
		{
			double damage = CalculateDamage(this._villain, this._hero, result, this._hero_defense_stance);
			this._hero.TakeDamage(damage);
			result.damage = damage;
		}

		return result;
	}

	public CombatResult Start() throws Exception
	{
		Game game_controller = Game.GetInstance();
		GameStats stats = game_controller.GetStats();
		CombatResult result = new CombatResult();

		while (true)
		{
			CombatTurnResult turn_result;
			if (IsHeroTurn())
			{
				turn_result = HeroTurn();
				
				if (turn_result.missed)
					stats.missed++;

				if (turn_result.critical)
					stats.crit++;

				if (turn_result.try_flee && turn_result.flee_successful)
					stats.flee_success++;

				if (turn_result.try_flee && !turn_result.flee_successful)
					stats.flee_failed++;

				stats.damage_dealt += (int)turn_result.damage;
			}
			else
			{
				turn_result = VillainTurn();

				if (turn_result.missed)
					stats.dodged++;

				if (turn_result.parried)
				{
					stats.damage_dealt += (int)turn_result.damage;
					stats.parried++;
				}
				else
					stats.damage_received += (int)turn_result.damage;
			}

			List<Entity> next_turns = SimulateNextTurns(SIMULATE_NEXT_TURNS);
			game_controller.DisplayCombatTurnResult(turn_result, next_turns);

			if (this._hero.GetCurrentHealth() <= 0)
			{
				result.hero_win = false;
				break;
			}
			if (this._villain.GetCurrentHealth() <= 0)
			{
				result.hero_win = true;
				if (this._is_boss)
					stats.boss_slained++;
				else
					stats.villain_slained++;
				break;
			}
			if (turn_result.flee_successful)
			{
				result.flee = true;
				break;
			}

			game_controller.GetUserInput();
		}

		return result;
	}
}
