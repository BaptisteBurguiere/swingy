package swingy.Controller;

import java.util.EnumMap;
import java.util.Map;

import swingy.Model.CombatTurnResult;
import swingy.Model.Hero;
import swingy.Model.HeroFactory;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;
import swingy.View.View;
import swingy.View.Console.ConsoleView;
import swingy.Model.Entity;
import swingy.Model.CombatResult;

public class Game
{
	private static Game	_instance = null;

	private Hero		_hero;
	private View		_view;

	private Game(Hero.Class Class, String name)
	{
		this._view = new ConsoleView();
		this._hero = HeroFactory.NewHero(Class, name);
	}

	public static Game GetInstance()
	{
		if (_instance == null)
			_instance = new Game(Hero.Class.KNIGHT, "Hero");

		return _instance;
	}

	public static Game GetInstance(Hero.Class Class, String name)
	{
		if (_instance == null)
			_instance = new Game(Class, name);

		return _instance;
	}

	public void Start()
	{
		this._view.DisplayHero(this._hero);

		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);
		stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 100));
		stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 10));
		stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 10));
		stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 10));
		stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0.1));
		stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0.9));
		stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0.1));
		stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 1.5));
		stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 5));

		Villain villain = new Villain("Godrick Soldier", 1, stats);

		Combat combat = new Combat(_hero, villain);
		CombatResult result = combat.Start();

		if (result.hero_win)
			this._view.DisplayVillainDied(villain);
		else
			this._view.DisplayYouDied();

		this._view.DisplayHero(_hero);
	}

	public void DisplayCombatTurnResult(CombatTurnResult result)
	{
		this._view.DisplayCombatTurnResult(result);
	}
}
