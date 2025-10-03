package swingy.Model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class VillainTemplate
{
	private final String									_name;
	private final int										_min_level;
	private final int										_max_level;
	private final Map<StatisticTemplate.Type, Statistic>	_base_statistics;
	private final Map<StatisticTemplate.Type, Statistic>	_growth_statistics;

	public VillainTemplate(String name, int min_level, int max_level, Map<StatisticTemplate.Type, Statistic> base_statistics, Map<StatisticTemplate.Type, Statistic> growth_statistics)
	{
		this._name = name;
		this._min_level = min_level;
		this._max_level = max_level;
		this._base_statistics = new EnumMap<>(base_statistics);
		this._growth_statistics = new EnumMap<>(growth_statistics);
	}

	public String 									GetName() {return this._name;}
	public int										GetMinLevel() {return this._min_level;}
	public int										GetMaxLevel() {return this._max_level;}
	public Map<StatisticTemplate.Type, Statistic>	GetBaseStatistics() {return Collections.unmodifiableMap(this._base_statistics);}
	public Map<StatisticTemplate.Type, Statistic>	GetGrowthStatistics() {return Collections.unmodifiableMap(this._growth_statistics);}
}
