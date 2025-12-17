package swingy.Model;

import java.util.EnumMap;
import java.util.Map;

public final class HeroFactory
{
	private HeroFactory() {}

	public static Hero NewHero(Hero.Class Class, String name)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);
		double health = 0;
		double attack = 0;
		double defense = 0;
		double speed = 0;
		double evasion = 0;
		double accuracy = 0;
		double crit_chance = 0;
		double crit_damage = 0;
		double luck = 0;

		switch (Class)
		{
			case KNIGHT:
				health = 110;
				attack = 11;
				defense = 11;
				speed = 10;
				evasion = 0.1;
				accuracy = 0.9;
				crit_chance = 0.1;
				crit_damage = 1.5;
				luck = 5;
				break;

			case GUARDIAN:
				health = 180;
				attack = 9;
				defense = 18;
				speed = 7;
				evasion = 0.08;
				accuracy = 0.85;
				crit_chance = 0.05;
				crit_damage = 1.3;
				luck = 4;
				break;

			case JUGGERNAUT:
				health = 130;
				attack = 19;
				defense = 9;
				speed = 8;
				evasion = 0.06;
				accuracy = 0.85;
				crit_chance = 0.1;
				crit_damage = 1.6;
				luck = 4;
				break;

			case ASSASSIN:
				health = 80;
				attack = 10;
				defense = 6;
				speed = 15;
				evasion = 0.2;
				accuracy = 0.95;
				crit_chance = 0.2;
				crit_damage = 1.7;
				luck = 6;
				break;

			case BERSERKER:
				health = 90;
				attack = 15;
				defense = 6;
				speed = 12;
				evasion = 0.1;
				accuracy = 0.88;
				crit_chance = 0.25;
				crit_damage = 1.8;
				luck = 6;
				break;

			case GAMBLER:
				health = 95;
				attack = 12;
				defense = 8;
				speed = 11;
				evasion = 0.17;
				accuracy = 0.8;
				crit_chance = 0.15;
				crit_damage = 2;
				luck = 10;
				break;
		
			default:
				break;
		}

		stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, health));
		stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, attack));
		stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, defense));
		stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, speed));
		stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, evasion));
		stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, accuracy));
		stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, crit_chance));
		stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, crit_damage));
		stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, luck));

		Hero new_hero = new Hero(Class, name, 1, stats, 0, health, new EnumMap<>(Item.Type.class));

		return new_hero;
	}
}
