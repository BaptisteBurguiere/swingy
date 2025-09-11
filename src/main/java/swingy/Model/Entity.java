package swingy.Model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public abstract class Entity
{
	protected String									_name;
	protected int										_level;
	protected Map<StatisticTemplate.Type, Statistic>	_statistics;

	public Entity(String name, int level, Map<StatisticTemplate.Type, Statistic> statistics)
	{
		this._name = name;
		this._level = level;
		this._statistics = new EnumMap<>(statistics);
	}

	public String 									GetName() {return this._name;}
	public int										GetLevel() {return this._level;}
	public Map<StatisticTemplate.Type, Statistic>	GetStatistics() {return Collections.unmodifiableMap(this._statistics);}

	public void	TakeDamage(double damage) {};
	public void	DealDamage(Entity entity, double damage) {};
}
