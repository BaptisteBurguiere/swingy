package swingy.Controller;

import java.util.EnumMap;
import java.util.Map;

import swingy.Model.CombatTurnResult;
import swingy.Model.GameMap;
import swingy.Model.Hero;
import swingy.Model.HeroFactory;
import swingy.Model.ItemFactory;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;
import swingy.View.View;
import swingy.View.Console.ConsoleView;
import swingy.Model.CombatResult;
import swingy.Model.Item;
import swingy.Model.VillainFactory;

public class Game
{
	private static Game	_instance = null;

	private GameMap		_map;
	private Hero		_hero;
	private View		_view;

	private final double XP_BASE = 4;
	private final double XP_GROWTH = 0.6;
	private final double XP_DIFF_SCALE = 10;
	private final double XP_MIN_MULT = 0.2;
	private final double XP_MAX_MULT = 2;
	private final double XP_SPREAD = 0.1;

	private Game(Hero.Class Class, String name)
	{
		this._view = new ConsoleView();
		this._hero = HeroFactory.NewHero(Class, name);
		this._map = new GameMap(this._hero.GetLevel());
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
		this._view.DisplayMainView(this._map, this._hero);
		// this._view.DisplayHero(this._hero);
		// this._view.DisplayEquipment(this._hero);

		// Villain villain = VillainFactory.GenerateVillain(this._hero);

		// Combat combat = new Combat(_hero, villain);
		// CombatResult result = combat.Start();

		// if (result.hero_win)
		// {
		// 	this._view.DisplayVillainDied(villain);

		// 	int xp_gained = CalculateXpGained(villain);
		// 	this._hero.GainExperience(xp_gained);
		// 	this._view.DisplayXpGained(xp_gained);
			
		// 	if (this._hero.GetExperience() >= this._hero.GetExperienceNeeded())
		// 	{
		// 		this._hero.LevelUp();
		// 		this._view.DisplayLevelUp(_hero);
		// 	}

		// 	Item drop = ItemFactory.GenerateItem(_hero);
		// 	if (drop != null)
		// 	{
		// 		this._view.DisplayItem(drop);
		// 	}
		// }
		// else
		// 	this._view.DisplayYouDied();

		// this._view.DisplayHero(_hero);
	}

	public void DisplayCombatTurnResult(CombatTurnResult result)
	{
		this._view.DisplayCombatTurnResult(result);
	}

	public int CalculateXpGained(Villain villain)
	{
		double k = XP_BASE + XP_GROWTH * (double)villain.GetLevel();

		double mult = 1. + ((double)villain.GetLevel() - (double)this._hero.GetLevel()) / XP_DIFF_SCALE;
		mult = Math.max(XP_MIN_MULT, Math.min(XP_MAX_MULT, mult));

		double villain_xp_needed = villain.GetExperienceNeeded();

		double xp_gained = villain_xp_needed / k * mult;

		double spread_roll = Math.min(1., Math.random());
		xp_gained *= 1. + (((XP_SPREAD * 2) * spread_roll) - XP_SPREAD);

		return (int) xp_gained;
	}
}
