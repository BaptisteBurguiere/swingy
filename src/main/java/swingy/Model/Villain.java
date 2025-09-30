package swingy.Model;

import java.util.Map;

public class Villain extends Entity
{
	private double	_current_health;

	public Villain(String name, int level, Map<StatisticTemplate.Type, Statistic> statistics)
	{
		super(name, level, statistics);
		this._current_health = this.GetStatistic(StatisticTemplate.Type.HEALTH).GetValue();
	}

	public double GetCurrentHealth() {return this._current_health;}

	public void TakeDamage(double damage)
	{
		this._current_health -= damage;
		if (this._current_health < 0)
			this._current_health = 0;
	}
}