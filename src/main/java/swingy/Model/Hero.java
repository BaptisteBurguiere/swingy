package swingy.Model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

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
		this._items = new EnumMap<>(items);
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
	public static String		GetClassStr(Class Class)
	{
		switch (Class)
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

		switch (type)
		{
			case ESSENCE:
				for (Map.Entry<StatisticTemplate.Type, Statistic> entry : item.GetStatistics().entrySet())
				{
					StatisticTemplate.Type stat_type = entry.getKey();
					Statistic stat = this._statistics.get(stat_type);
					stat.Increase(entry.getValue().GetValue());

					if (stat_type == StatisticTemplate.Type.HEALTH)
						this._current_health += entry.getValue().GetValue();
				}
				break;

			case HEALING:
				Map<StatisticTemplate.Type, Statistic> stats = item.GetStatistics();
				double healing = stats.get(StatisticTemplate.Type.HEALTH).GetValue() / 100.;
				double health = this._statistics.get(StatisticTemplate.Type.HEALTH).GetValue();
				this._current_health += health * healing;

				if (this._current_health > health)
					this._current_health = health;
				break;
		
			default:
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
				break;
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

			switch (this._class) {
				case KNIGHT:
					this._statistics.get(StatisticTemplate.Type.HEALTH).Increase(12);
					this._statistics.get(StatisticTemplate.Type.ATTACK).Increase(3);
					this._statistics.get(StatisticTemplate.Type.DEFENSE).Increase(3);
					this._statistics.get(StatisticTemplate.Type.SPEED).Increase(1);
					break;
	
				case GUARDIAN:
					this._statistics.get(StatisticTemplate.Type.HEALTH).Increase(20);
					this._statistics.get(StatisticTemplate.Type.DEFENSE).Increase(6);
					this._statistics.get(StatisticTemplate.Type.ATTACK).Increase(1);
					break;
	
				case JUGGERNAUT:
					this._statistics.get(StatisticTemplate.Type.HEALTH).Increase(18);
					this._statistics.get(StatisticTemplate.Type.ATTACK).Increase(5);
					this._statistics.get(StatisticTemplate.Type.DEFENSE).Increase(2);
					break;
	
				case ASSASSIN:
					this._statistics.get(StatisticTemplate.Type.HEALTH).Increase(8);
					this._statistics.get(StatisticTemplate.Type.SPEED).Increase(3);
					this._statistics.get(StatisticTemplate.Type.CRIT_CHANCE).Increase(0.01);
					this._statistics.get(StatisticTemplate.Type.EVASION).Increase(0.005);
					break;
	
				case BERSERKER:
					this._statistics.get(StatisticTemplate.Type.HEALTH).Increase(10);
					this._statistics.get(StatisticTemplate.Type.ATTACK).Increase(6);
					this._statistics.get(StatisticTemplate.Type.CRIT_CHANCE).Increase(0.02);
					break;
	
				case GAMBLER:
					this._statistics.get(StatisticTemplate.Type.LUCK).Increase(2);
					for (int i = 0; i < 2; i++)
					{
						Random rand = new Random();
						int roll = rand.nextInt(StatisticTemplate.GetNbStats());

						switch (roll) {
							case 0:
								this._statistics.get(StatisticTemplate.Type.HEALTH).Increase(15);
								break;

							case 1:
								this._statistics.get(StatisticTemplate.Type.ATTACK).Increase(5);
								break;

							case 2:
								this._statistics.get(StatisticTemplate.Type.DEFENSE).Increase(3);
								break;

							case 3:
								this._statistics.get(StatisticTemplate.Type.SPEED).Increase(1);
								break;

							case 4:
								this._statistics.get(StatisticTemplate.Type.EVASION).Increase(0.005);
								break;

							case 5:
								this._statistics.get(StatisticTemplate.Type.ACCURACY).Increase(0.005);
								break;

							case 6:
								this._statistics.get(StatisticTemplate.Type.CRIT_CHANCE).Increase(0.01);
								break;

							case 7:
								this._statistics.get(StatisticTemplate.Type.CRIT_DAMAGE).Increase(0.05);
								break;

							case 8:
								this._statistics.get(StatisticTemplate.Type.LUCK).Increase(2);
								break;
						
							default:
								break;
						}
					}
					break;
			}

			this._current_health = this._statistics.get(StatisticTemplate.Type.HEALTH).GetValue();
		}
	}
}
