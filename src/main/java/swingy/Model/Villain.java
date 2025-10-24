package swingy.Model;

import java.util.Map;

public class Villain extends Entity
{
	private double	_current_health;
	private double	_exp_multiplier;

	public Villain(String name, int level, double exp_multiplier, Map<StatisticTemplate.Type, Statistic> statistics)
	{
		super(name, level, statistics);
		this._current_health = this.GetStatistic(StatisticTemplate.Type.HEALTH).GetValue();
		this._exp_multiplier = exp_multiplier;
	}

	public double GetCurrentHealth() { return this._current_health; }
	public double GetExpMultiplier() { return this._exp_multiplier; }

	public void TakeDamage(double damage)
	{
		this._current_health -= damage;
		if (this._current_health < 0)
			this._current_health = 0;
	}
}