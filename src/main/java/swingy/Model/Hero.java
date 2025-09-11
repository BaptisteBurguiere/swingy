package swingy.Model;

import java.util.EnumMap;
import java.util.Map;

public class Hero extends Entity
{
	public enum Class
	{
		KNIGHT,

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

	public void EquipItem(Item item)
	{
		Item.Type type = item.GetType();
		Item current_item = this._items.get(type);

		if (current_item != null)
		{
			for (Map.Entry<StatisticTemplate.Type, Double> entry : current_item.GetStatistics().entrySet())
			{
				StatisticTemplate.Type stat_type = entry.getKey();
				Statistic stat = this._statistics.get(stat_type);
				stat.Decrease(entry.getValue());

				if (stat_type == StatisticTemplate.Type.HEALTH && this._current_health > stat.GetValue())
					this._current_health = stat.GetValue();
			}
		}

		this._items.put(type, item);

		for (Map.Entry<StatisticTemplate.Type, Double> entry : item.GetStatistics().entrySet())
		{
			StatisticTemplate.Type stat_type = entry.getKey();
			Statistic stat = this._statistics.get(stat_type);
			stat.Increase(entry.getValue());

			if (stat_type == StatisticTemplate.Type.HEALTH)
				this._current_health += entry.getValue();
		}
	}
}
