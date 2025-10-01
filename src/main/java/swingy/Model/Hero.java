package swingy.Model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class Hero extends Entity
{
	public enum Class
	{
		KNIGHT,		// balanced
		GUARDIAN,	// health defense
		JUGGERNAUT,	// health attack
		ASSASSIN,	// speed evasion crit
		BERSERKER,	// attack crit chance
		GAMBLER		// evasion luck crit dmg
	}

	private Class					_class;
	private int						_experience;
	private double					_current_health;
	private Map<Item.Type, Item>	_items;

	public Hero(Class Class, String name, int level, Map<StatisticTemplate.Type, Statistic> statistics, int experience, double current_health, Map<Item.Type, Item> items)
	{
		super(name, level, statistics);
		this._class = Class;
		this._experience = experience;
		this._current_health = current_health;
		this._items = new EnumMap<>(Item.Type.class);

		for (Map.Entry<Item.Type, Item> entry : items.entrySet())
			this.EquipItem(entry.getValue());
	}

	public Class				GetClass() {return this._class;}
	public String				GetClassStr()
	{
		switch (this._class)
		{
			case KNIGHT:
				return "Knight";
			case GUARDIAN:
				return "Guardian";
			case JUGGERNAUT:
				return "Juggernaut";
			case ASSASSIN:
				return "Assassin";
			case BERSERKER:
				return "Berserker";
			case GAMBLER:
				return "Gambler";
			default:
				return "Undefined";
		}
	}
	public int					GetExperience() {return this._experience;}
	public double				GetCurrentHealth() {return this._current_health;}
	public Map<Item.Type, Item>	GetItems() {return Collections.unmodifiableMap(this._items);}
	public Item					GetItem(Item.Type type) {return this._items.get(type);}

	public void TakeDamage(double damage)
	{
		this._current_health -= damage;
		if (this._current_health < 0)
			this._current_health = 0;
	}

	public void EquipItem(Item item)
	{
		Item.Type type = item.GetType();
		Item current_item = this._items.get(type);

		if (current_item != null)
		{
			for (Map.Entry<StatisticTemplate.Type, Statistic> entry : current_item.GetStatistics().entrySet())
			{
				StatisticTemplate.Type stat_type = entry.getKey();
				Statistic stat = this._statistics.get(stat_type);
				stat.Decrease(entry.getValue().GetValue());

				if (stat_type == StatisticTemplate.Type.HEALTH && this._current_health > stat.GetValue())
					this._current_health = stat.GetValue();
			}
		}

		this._items.put(type, item);

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : item.GetStatistics().entrySet())
		{
			StatisticTemplate.Type stat_type = entry.getKey();
			Statistic stat = this._statistics.get(stat_type);
			stat.Increase(entry.getValue().GetValue());

			if (stat_type == StatisticTemplate.Type.HEALTH)
				this._current_health += entry.getValue().GetValue();
		}
	}

	public void GainExperience(int xp)
	{
		this._experience += xp;
	}

	public void LevelUp()
	{
		while (this._experience >= this.GetExperienceNeeded())
		{
			this._experience -= this.GetExperienceNeeded();
			this._level++;
		}
	}
}
