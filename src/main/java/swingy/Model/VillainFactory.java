package swingy.Model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class VillainFactory
{
	private VillainFactory() {}

	private final static Random rand = new Random();

	private static List<VillainTemplate>	_villain_templates = null;
	private static List<VillainTemplate>	_boss_templates = null;	

	private static void Init()
	{
		if (_villain_templates != null)
			return;

		_villain_templates = new ArrayList<VillainTemplate>();

		Map<StatisticTemplate.Type, Statistic> base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		Map<StatisticTemplate.Type, Statistic> growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 60));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 8));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 4));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 5));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.03));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.75));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.03));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.15));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 1));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 14));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 2.3));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 1));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.45));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.003));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.012));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.004));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.012));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.22));

		_villain_templates.add(new VillainTemplate("Brute", 1, 20, 1, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 70));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 6));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 8));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 4));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.02));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.8));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.02));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.10));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 1));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 13));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 1.3));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 2.4));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.35));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.001));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.01));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.003));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.012));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.17));

		_villain_templates.add(new VillainTemplate("Defender", 10, 30, 1, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 55));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 8));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 4));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 13));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.1));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.88));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.10));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.25));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 3));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 8));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 1.5));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 0.8));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 1.5));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.005));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.014));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.010));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.030));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.24));

		_villain_templates.add(new VillainTemplate("Skirmisher", 5, 25, 1, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 50));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 5));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 5));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 6));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.05));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.88));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.08));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.3));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 3));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 10));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 1.7));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 0.8));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.8));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.004));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.015));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.01));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.04));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.4));

		_villain_templates.add(new VillainTemplate("Hexcaster", 15, 35, 1, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 120));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 12));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 8));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 3));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.01));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.65));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.04));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.2));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 1));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 25));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 4.8));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 2.4));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.3));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.003));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.008));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.006));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.03));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.15));

		_villain_templates.add(new VillainTemplate("Juggernaut", 25, 45, 1, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 45));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 7));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 4));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 9));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.15));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.82));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.12));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.25));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 6));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 8));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 1.4));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 0.9));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 1.4));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.01));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.015));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.012));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.035));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.7));

		_villain_templates.add(new VillainTemplate("Trickster", 1, 50, 1, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 80));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 10));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 8));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 7));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.06));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.9));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.07));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.25));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 4));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 16));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 2.9));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 2.4));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.8));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.003));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.02));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.008));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.035));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.4));

		_villain_templates.add(new VillainTemplate("Champion", 15, 50, 1.3, base_stats, growth_stats));





		_boss_templates = new ArrayList<VillainTemplate>();

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 200));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 10));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 15));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 3));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.01));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.8));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.03));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.2));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 2));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 35));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 3));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 4));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.2));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.002));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.01));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.002));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.02));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.2));

		_boss_templates.add(new VillainTemplate("Dreadknight Varok", 1, 45, 3.6, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 110));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 17));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 3));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 8));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.15));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.85));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.06));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.4));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 2));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 20));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 4));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 1));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 1.2));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.01));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.01));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.006));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.03));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.3));

		_boss_templates.add(new VillainTemplate("Seraphina the Fallen", 1, 45, 3.6, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 110));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 23));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 1));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 7));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.05));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.9));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.1));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.5));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 3));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 18));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 5));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 1));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.5));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.006));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.012));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.01));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.04));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.3));

		_boss_templates.add(new VillainTemplate("The Maw of Cinders", 1, 45, 3.6, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 130));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 13));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 5));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 8));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.08));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.88));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.07));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.35));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 8));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 22));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 2.5));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 1.5));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.7));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.01));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.01));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.007));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.02));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.7));

		_boss_templates.add(new VillainTemplate("Oracle Nihra", 1, 45, 3.6, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 170));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 12));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 9));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 5));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.03));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.82));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.04));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.25));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 2));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 30));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 3));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 2.5));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.4));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.004));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.008));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.003));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.015));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.2));

		_boss_templates.add(new VillainTemplate("Vorax, Devourer of Hope", 1, 45, 3.6, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 120));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 9));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 4));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 9));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.07));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.9));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.05));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.25));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 5));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 20));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 2));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 1));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 1));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.01));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.015));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.005));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.02));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.4));

		_boss_templates.add(new VillainTemplate("The Marionette King", 1, 45, 3.6, base_stats, growth_stats));

		base_stats = new EnumMap<>(StatisticTemplate.Type.class);
		growth_stats = new EnumMap<>(StatisticTemplate.Type.class);

		base_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 350));
		base_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 30));
		base_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 18));
		base_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 8));
		base_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.06));
		base_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.92));
		base_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.08));
		base_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.5));
		base_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 6));

		growth_stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 50));
		growth_stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 5));
		growth_stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 3));
		growth_stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0.8));
		growth_stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.008));
		growth_stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.012));
		growth_stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.008));
		growth_stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0.03));
		growth_stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0.5));

		_boss_templates.add(new VillainTemplate("Eclipsion, The World-Ender", 45, 50, 4.6, base_stats, growth_stats));
	}

	private static List<VillainTemplate> GetPool(int level)
	{
		List<VillainTemplate> pool = new ArrayList<>();

		for (VillainTemplate template : _villain_templates)
		{
			if (template.GetMinLevel() <= level && level <= template.GetMaxLevel())
				pool.add(template);	
		}

		return pool;
	}

	private static List<VillainTemplate> GetBossesPool(int level)
	{
		List<VillainTemplate> pool = new ArrayList<>();

		for (VillainTemplate template : _boss_templates)
		{
			if (template.GetMinLevel() <= level && level <= template.GetMaxLevel())
				pool.add(template);
		}

		return pool;
	}

    private static final double BASE_MEAN_OFFSET = 0.2;    // small early bias
    private static final double MIDGAME_PER_LVL = 0.20;   // per level after threshold
    private static final int MIDGAME_THRESHOLD = 5;       // begin bias after this hero level
    private static final double ENEMY_COUNT_SCALE = 0.15; // per extra enemy in room
    private static final double BASE_STDDEV = 1.1;
    private static final double STDDEV_PER_LEVEL = 0.04;
    private static final int MAX_OFFSET_CAP = 8;          // max (villainLevel - hero_level)
    private static final int MIN_LEVEL = 1;

	private static int GenerateVillainLevel(int hero_level, int villain_count)
	{
        if (hero_level <= 0) hero_level = 1;
        if (villain_count <= 0) villain_count = 1;

        // mean offset increases only after MIDGAME_THRESHOLD
        double lvlAfter = Math.max(0, hero_level - MIDGAME_THRESHOLD);
        double meanOffset = BASE_MEAN_OFFSET + lvlAfter * MIDGAME_PER_LVL;

        // add small bonus based on how many enemies are in this room
        // we treat 1 enemy as baseline (no penalty), extras add offset
        double enemiesBonus = (villain_count > 1) ? (villain_count - 1) * ENEMY_COUNT_SCALE : 0.0;
        meanOffset += enemiesBonus;

        // cap the offset so villains can't become absurdly higher than the hero
        if (meanOffset > MAX_OFFSET_CAP) meanOffset = MAX_OFFSET_CAP;

        double mean = hero_level + meanOffset;

        // stddev grows slowly with level to allow more spread late-game
        double stddev = BASE_STDDEV + hero_level * STDDEV_PER_LEVEL;

        double gauss = rand.nextGaussian() * stddev;
        int villainLevel = (int) Math.round(mean + gauss);

        // Enforce reasonable bounds
        // villain should never be less than MIN_LEVEL
        if (villainLevel < MIN_LEVEL) villainLevel = MIN_LEVEL;

        return villainLevel;
	}

	public static Villain GenerateVillain(Hero hero, int villain_count)
	{
		Init();

		int level = GenerateVillainLevel(hero.GetLevel(), villain_count);
		List<VillainTemplate> pool = GetPool(level);

		VillainTemplate template = pool.get(rand.nextInt(pool.size()));

		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);
		Map<StatisticTemplate.Type, Statistic> base_stats = template.GetBaseStatistics();
		Map<StatisticTemplate.Type, Statistic> growth_stats = template.GetGrowthStatistics();

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : base_stats.entrySet()) {
			StatisticTemplate.Type type = entry.getKey();
			double value = entry.getValue().GetValue() + growth_stats.get(type).GetValue() * level;

			stats.put(type, new Statistic(type, value));
		}

		return new Villain(template.GetName(), level, template.GetExpMultiplier(), stats);
	}

	public static int GenerateBossLevel(int hero_level)
	{
        // Base boss offset plus mild scaling by hero level:
        // example: hero 6 -> +3.1, hero 25 -> +5.5
        double baseBossOffset = 3.0;
        double bossScaling = 0.10; // 10% of hero level
        double offset = baseBossOffset + hero_level * bossScaling;

        // optional cap for bosses if you want to limit crazy values:
        if (offset > MAX_OFFSET_CAP) offset = MAX_OFFSET_CAP;

        int bossLevel = (int)Math.round(hero_level + offset);
        return Math.max(MIN_LEVEL, bossLevel);
	}

	public static Villain GenerateBoss(Hero hero)
	{
		int level = GenerateBossLevel(hero.GetLevel());
		List<VillainTemplate> pool = GetBossesPool(level);

		VillainTemplate template = pool.get(rand.nextInt(pool.size()));

		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);
		Map<StatisticTemplate.Type, Statistic> base_stats = template.GetBaseStatistics();
		Map<StatisticTemplate.Type, Statistic> growth_stats = template.GetGrowthStatistics();

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : base_stats.entrySet()) {
			StatisticTemplate.Type type = entry.getKey();
			double value = entry.getValue().GetValue() + growth_stats.get(type).GetValue() * level;

			stats.put(type, new Statistic(type, value));
		}

		return new Villain(template.GetName(), level, template.GetExpMultiplier(), stats);
	}
}
