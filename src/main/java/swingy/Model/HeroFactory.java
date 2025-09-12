package swingy.Model;

import java.util.EnumMap;
import java.util.Map;

public final class HeroFactory
{
	private HeroFactory() {}

	public static Hero NewHero(Hero.Class Class, String name)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);
		Map<Item.Type, Item> items = new EnumMap<>(Item.Type.class);
		double health = 0;
		double attack = 0;
		double defense = 0;
		double speed = 0;
		double evasion = 0;
		double accuracy = 0;
		double crit_chance = 0;
		double crit_damage = 0;
		double luck = 0;

		Item weapon = null;
		Item armor = null;
		Item helmet = null;

		Map<StatisticTemplate.Type, Statistic> weapon_stat = new EnumMap<>(StatisticTemplate.Type.class);
		Map<StatisticTemplate.Type, Statistic> armor_stat = new EnumMap<>(StatisticTemplate.Type.class);
		Map<StatisticTemplate.Type, Statistic> helmet_stat = new EnumMap<>(StatisticTemplate.Type.class);

		switch (Class)
		{
			case KNIGHT:
				health = 100;
				attack = 10;
				defense = 10;
				speed = 10;
				evasion = 0.1;
				accuracy = 0.9;
				crit_chance = 0.1;
				crit_damage = 1.5;
				luck = 5;
				weapon_stat.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 8));
				weapon = new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Steel Longsword", "", weapon_stat);
				armor_stat.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 8));
				armor = new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Tempered Chainmail", "", armor_stat);
				helmet_stat.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 20));
				helmet = new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Iron Visor Helm", "", helmet_stat);
				break;

			case GUARDIAN:
				health = 140;
				attack = 6;
				defense = 14;
				speed = 7;
				evasion = 0.08;
				accuracy = 0.85;
				crit_chance = 0.05;
				crit_damage = 1.3;
				luck = 4;
				weapon_stat.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 4));
				weapon = new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Bulwark Mace", "", weapon_stat);
				armor_stat.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 12));
				armor = new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Fortress Plate", "", armor_stat);
				helmet_stat.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 30));
				helmet = new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Aegis Greathelm", "", helmet_stat);
				break;

			case JUGGERNAUT:
				health = 130;
				attack = 14;
				defense = 9;
				speed = 8;
				evasion = 0.06;
				accuracy = 0.85;
				crit_chance = 0.1;
				crit_damage = 1.6;
				luck = 4;
				weapon_stat.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 12));
				weapon = new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Titan Maul", "", weapon_stat);
				armor_stat.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 8));
				armor = new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Warborn Plate", "", armor_stat);
				helmet_stat.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 25));
				helmet = new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Juggernaut Visage", "", helmet_stat);
				break;

			case ASSASSIN:
				health = 80;
				attack = 10;
				defense = 6;
				speed = 15;
				evasion = 0.25;
				accuracy = 0.95;
				crit_chance = 0.3;
				crit_damage = 1.7;
				luck = 6;
				weapon_stat.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 10));
				weapon = new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Shadowfang Dagger", "", weapon_stat);
				armor_stat.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 4));
				armor = new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Silken Wraps", "", armor_stat);
				helmet_stat.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 10));
				helmet = new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Veilhood", "", helmet_stat);
				break;

			case BERSERKER:
				health = 90;
				attack = 15;
				defense = 6;
				speed = 12;
				evasion = 0.1;
				accuracy = 0.88;
				crit_chance = 0.35;
				crit_damage = 1.8;
				luck = 6;
				weapon_stat.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 14));
				weapon = new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Bloodreaver Axe", "", weapon_stat);
				armor_stat.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 5));
				armor = new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Bonehide Vest", "", armor_stat);
				helmet_stat.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 12));
				helmet = new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Skullband Circlet", "", helmet_stat);
				break;

			case GAMBLER:
				health = 85;
				attack = 9;
				defense = 7;
				speed = 11;
				evasion = 0.22;
				accuracy = 0.75;
				crit_chance = 0.25;
				crit_damage = 2;
				luck = 10;
				weapon_stat.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 9));
				weapon = new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Coinflip Rapier", "", weapon_stat);
				armor_stat.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 6));
				armor = new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Lucky Waistcoat", "", armor_stat);
				helmet_stat.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 15));
				helmet = new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Stacked Deck Hat", "", helmet_stat);
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

		items.put(Item.Type.WEAPON, weapon);
		items.put(Item.Type.ARMOR, armor);
		items.put(Item.Type.HELMET, helmet);

		Hero new_hero = new Hero(Class, name, 1, stats, 0, health, items);

		return new_hero;
	}
}
